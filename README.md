# Fortify Static Code Analyzer plugin

The _Fortify Static Code Analyzer_ plugin allows you to execute static application security 
testing as part of a Deployment Automation workflow.

This plugin is a work in progress but it is intended to provide the following steps:

* [x] **Update Fortify Rulepacks** - Update Fortify Security Content (Rulepacks) prior to a scan
* [x] **Fortify SCA Clean** - Clean up from a previous scan
* [x] **Fortify SCA Translate** - Convert source code to intermediary files to use in a scan
* [x] **Fortify SCA Scan** - Run a scan with Fortify Source Analyzer
* [x] **Fortify SSC Upload** - Upload the results of a scan to Software Security Center  
* [ ] **Generate Fortify Report** - Generate a Fortify Report from a results file
* [ ] **Install Fortify SCA** - Install the Fortify Static Code Analyzer tools on an endpoint

This plugin can be used with Fortify [Static Code Analyzer](https://www.microfocus.com/en-us/products/static-code-analysis-sast/overview) 
standalone or when integrated with [Software Security Center](https://www.microfocus.com/en-us/products/software-security-assurance-sdlc/overview). The plugin requires that Fortify Static Code Analyzer Tools have been previously installed on the endpoint where Deployment Automation executes a process. In future an additional step will be added to install the tools.
 
### Installing the plugin
 
Download the latest version from the _release_ directory and install into Deployment Automation from the 
**Administration\Automation\Plugins** page.

### Using the plugin

The plugin provides discrete steps for translating and executing a scan. If possible you should execute 
**Update Fortify Rulepacks** first so that you are scanning with the latest rules. Then execute **Clean**, **Translate**
and **Scan** in that order with the same *Build Id*. You can optionally upload the scan results to [Software Security Center](https://www.microfocus.com/en-us/products/software-security-assurance-sdlc/overview)
using the **Fortify SSC Upload** step. For this step you can create two Deployment Automation 
[System Properties](http://help.serena.com/doc_center/sra/ver6_3/sda_help/sra_adm_sys_properties.html)
called `ssc.serverUrl` that refers to your Software Security Center URL (e.g. "https://server-name:8080/ssc") and
 `ssc.authToken` that refers to an authentication token of type **AnalysisUploadToken** that has been created in Software Security Center.

### Building the plugin

To build the plugin you will need to clone the following repositories (at the same level as this repository):

 - [mavenBuildConfig](https://github.com/sda-community-plugins/mavenBuildConfig)
 - [plugins-build-parent](https://github.com/sda-community-plugins/plugins-build-parent)
 - [air-plugin-build-script](https://github.com/sda-community-plugins/air-plugin-build-script)
 
 and then compile using the following command
 ```
   mvn clean package
 ```  

This will create a _.zip_ file in the `target` directory when you can then install into Deployment Automation
from the **Administration\Automation\Plugins** page.

If you have any feedback or suggestions on this template then please contact me using the details below.

Kevin A. Lee

kevin.lee@microfocus.com

**Please note: this plugins is provided as a "community" plugin and is not supported by Micro Focus in any way**.
