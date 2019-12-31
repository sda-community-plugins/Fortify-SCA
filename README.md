# Maven Build Plugin

The _Maven Build_ plugin is a build management plugin. It is run during the development and deployment process 
to execute [Maven](https://maven.apache.org/) builds. It interacts directly with the Maven command line.

This plugin provides the following steps:

* [x] **Maven Exec** - Execute a Maven Build command.

This plugin requires that a [Java JDK](https://openjdk.java.net/) and [Maven](https://maven.apache.org/) have been installed on the target server where the steps will be executed.

### Installing the plugin
 
Download the latest version from the _release_ directory and install into Deployment Automation from the 
**Administration\Automation\Plugins** page.

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

