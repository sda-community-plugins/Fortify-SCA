// --------------------------------------------------------------------------------
// Update Fortify Security Rules
// --------------------------------------------------------------------------------

import com.serena.air.StepFailedException
import com.serena.air.StepPropertiesHelper
import com.serena.air.TextAreaParser
import com.urbancode.air.AirPluginTool
import com.serena.air.plugin.sca.FortifyUtils

//
// Create some variables that we can use throughout the plugin step.
// These are mainly for checking what operating system we are running on.
//
final def PLUGIN_HOME = System.getenv()['PLUGIN_HOME']
final String lineSep = System.getProperty('line.separator')
final String osName = System.getProperty('os.name').toLowerCase(Locale.US)
final String pathSep = System.getProperty('path.separator')
final boolean windows = (osName =~ /windows/)
final boolean vms = (osName =~ /vms/)
final boolean os9 = (osName =~ /mac/ && !osName.endsWith('x'))
final boolean unix = (pathSep == ':' && !vms && !os9)

//
// Initialise the plugin tool and retrieve all the properties that were sent to the step.
//
final def  apTool = new AirPluginTool(this.args[0], this.args[1])
final def  props  = new StepPropertiesHelper(apTool.getStepProperties(), true)

//
// Set a variable for each of the plugin steps's inputs.
// We can check whether a required input is supplied (the helper will fire an exception if not) and
// if it is of the required type.
//
File workDir = new File('.').canonicalFile
String updateOptions = props.optional('updateOptions')
String fortifyPath = props.optional("fortifyPath")
Boolean debugMode = props.optionalBoolean("debugMode", false)

def updateOptionsList = updateOptions.split('[ \n\r]')?.findAll{ it && it.trim().length() > 0}
def fUtils = new FortifyUtils()

println "----------------------------------------"
println "-- STEP INPUTS"
println "----------------------------------------"

//
// Print out each of the property values.
//
println "Working directory: ${workDir.canonicalPath}"
println "Update Options: " + ((updateOptionsList.isEmpty()) ? "none defined" : updateOptionsList.toListString())
println "Fortify Path: " + (fortifyPath ? fortifyPath : "not defined, using system path")
println "Debug Output: ${debugMode}"
if (debugMode) { props.setDebugLoggingMode() }

println "----------------------------------------"
println "-- STEP EXECUTION"
println "----------------------------------------"

String scaExe
int exitCode = -1;

//
// The main body of the plugin step - wrap it in a try/catch statement for handling any exceptions.
//
try {

    //
    // Validation
    //

    if (workDir.isFile()) {
        throw new StepFailedException("Working directory ${workDir} is a file!")
    }

    if (fortifyPath) {
        scaExe = fortifyPath + File.separatorChar + "fortifyupdate" + (windows ? ".cmd" : "")
    } else {
        scaExe = fUtils.findExecutableOnPath("fortifyupdate" + (windows ? ".cmd" : ""))
    }
    if (!new File(scaExe).exists()) {
        throw new StepFailedException("Could not find executable: ${scaExe}")
    }

    // ensure work-dir exists
    workDir.mkdirs()

    //
    // Build Command Line
    //
    def commandLine = []
    commandLine.add(scaExe)

    if (updateOptionsList) {
        updateOptionsList.each() { option ->
            commandLine.add(option)
        }
    }

    // print out command info
    println("")
    println("Fortify command line: ${commandLine.join(' ')}")
    println("working directory: ${workDir.path}")
    println('===============================')
    println("command output: ")

    //
    // Launch Process
    //
    final def processBuilder = new ProcessBuilder(commandLine as String[]).directory(workDir)
    final def process = processBuilder.start()
    process.out.close() // close stdin
    process.waitForProcessOutput(System.out, System.err) // forward stdout and stderr
    process.waitFor()

    // print results
    println('===============================')
    println("command exit code: ${process.exitValue()}")
    println("")

    exitCode = process.exitValue();
} catch (StepFailedException e) {
    //
    // Catch any exceptions we find and print their details out.
    //
    println "ERROR: ${e.message}"
    // An exit with a non-zero value will be deemed a failure
    System.exit 1
}

//
// An exit with a zero value means the plugin step execution will be deemed successful.
//
System.exit(exitCode);
