# Workpapers-Needed

## Overview
`Workpapers-Needed` is a JavaFX application developed as a collaborative project. This repository contains the source code for the application, the project is built using JavaFX 24 and requires JDK 24 to run. This README provides instructions for setting up and running the project in Eclipse.

## Repository Structure
- **main**: The primary development branch where all stable features are merged.

## Prerequisites/Requirements (IDE MUST BE ECLIPSE)
Before setting up the project, ensure you have the following installed:
- **JDK 24**: The Java Development Kit (download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use an OpenJDK distribution like [Adoptium](https://adoptium.net/)).
- **JavaFX 24**: The JavaFX SDK (download from [Gluon](https://gluonhq.com/products/javafx/)).
- **JavaFX VM arguments** need to be set up as --module-path "DELETE-THIS-AND-REPLACE-WITH-FILEPATH-TO-WHERE-JAVAFX24-SDK-WAS-INSTALLED-\JAVAFXSDK24\Lib" --add-modules javafx.controls,javafx.fxml,javafx.media --enable-native-access=javafx.graphics
- **Libraries and module/classpaths** JavaFX24 usermade library needs to be sent up on the build path under CLASSPATH, user made library consists of all external JAR files from the JDK24 SDK download folder SPECIFICALLY the ones in the subfolder "Lib." in ECLIPSE IDE under help and "Install New Software" a JavaFX plugin for eclipse must be downloaded download.eclipse.org/efxclipse/updates-nightly/repository.

- ## Project Development Environment Set up Recap
- **1. Use Eclipse**
- **2. Download both JDK24 and JavaFX 24 from corresponding above sites**
- **3. Set Up VM arguments for the project as --module-path "DELETE-THIS-AND-REPLACE-WITH-FILEPATH-TO-WHERE-JAVAFX-SDK-24\Lib-was-installed" --add-modules javafx.controls,javafx.fxml,javafx.media --enable-native-access=javafx.graphics**
- **4. Download Eclipse JavaFX plugin from download.eclipse.org/efxclipse/updates-nightly/repository in eclipse using help > install new software**
- **5. Create User Library in eclipse, naming the library "JavaFX24", import all external jars located in the javafx-sdk-24 >>> lib folder into this library**
- **6. Double check class and module paths. JDK24 should be under module path (Eclipse is running JDK 24 essentially), the JavaFX24 user made library AND the plugin folder called "JavaFX SDK" should be both under classpath**

## Setup Instructions in Eclipse (BELOW IS SET UP INSTRUCTIONS FOR DEVS, NON DEVS CAN IGNORE)

### 1. Clone the Repository
1. Open Eclipse.
2. Go to **File > Import > Git > Projects from Git (with smart import)**.
3. Select **Clone URI** and click **Next**.
4. Enter the repository URI: `https://github.com/Khai-Huynh1/Workpapers-Needed.git`.
5. Authenticate with your GitHub credentials (use a Personal Access Token if required).
6. Select the branch you want to work on (e.g., `main` for development or a feature branch like `feature-1`).
7. Choose a local directory to clone the repository and click **Finish**.
8. Eclipse will import the project as a Java project.

### 2. Install JDK 24
1. If JDK 24 isn’t installed:
   - Download JDK 24 from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/).
   - Install it on your system.
2. In Eclipse, configure JDK 24:
   - Go to **Window > Preferences > Java > Installed JREs**.
   - Click **Add** > **Standard VM** > **Next**.
   - Browse to the JDK 24 installation directory (e.g., `C:\Program Files\Java\jdk-24` on Windows).
   - Click **Finish**, then check the box next to JDK 24 and click **Apply and Close**.
3. Set the project to use JDK 24:
   - Right-click the project (`Workpapers-Needed`) in the Package Explorer > **Properties**.
   - Go to **Java Build Path > Libraries**.
   - Select **JRE System Library**, click **Edit**, and choose **Workspace default JRE** (which should now be JDK 24).
   - Click **Finish**, then **Apply and Close**.

### 3. Download and Configure JavaFX 24
1. Download the JavaFX 24 SDK from [Gluon](https://gluonhq.com/products/javafx/).
   - Choose the appropriate version for your operating system (e.g., Windows, macOS, Linux).
   - Extract the ZIP file to a directory (e.g., `C:\javafx-sdk-24`).
2. Add JavaFX to the Build Path in Eclipse:
   - Right-click the project > **Properties**.
   - Go to **Java Build Path > Libraries**.
   - Click **Add External JARs**.
   - Navigate to the `lib` folder of the JavaFX SDK (e.g., `C:\javafx-sdk-24\lib`).
   - Select all JAR files (e.g., `javafx.base.jar`, `javafx.controls.jar`, `javafx.fxml.jar`, `javafx.graphics.jar`, etc.) and click **Open**.
   - Click **Apply**.

### 4. Configure VM Arguments for JavaFX
JavaFX requires specific VM arguments to run, as it’s no longer bundled with the JDK.
1. Right-click the project > **Run As > Run Configurations**.
2. In the Run Configurations dialog, select your main class under **Java Application** (or create a new configuration by clicking **New**).
3. Go to the **Arguments** tab.
4. In the **VM arguments** section, add the following (replace the path with your JavaFX SDK location):
