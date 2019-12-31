package com.serena.air.plugin.sca

import com.serena.air.StepFailedException

public class FortifyUtils {

    public static boolean isEmpty(String str) {
        return (str == null) || str.trim().isEmpty();
    }

    public static def toTrimmedList(def list, String delimiter) {
        return list.split(delimiter).findAll{ it?.trim() }.collect{ it.trim() }
    }

    public static String findExecutableOnPath(String name) {
        for (String dirname : System.getenv("PATH").split(File.pathSeparator)) {
            File file = new File(dirname, name);
            if (file.isFile() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        throw new StepFailedException("Could not find executable: ${name}");
    }

    // ----------------------------------------

    public static debug(String message) {
        println("[DEBUG} ${message}")
    }

    public static info(String message) {
        println("[INFO] ${message}")
    }

    public static error(String message) {
        println("[ERROR] ${message}")
    }
}
