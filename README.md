# **SENG201 Project**

A turn-based guild management game built for SENG201. The player
recruits adventurers, equips them with items, and sends them on expeditions
for gold and loot over a player-chosen number of rounds.

# **Requirements**

Java 21 (any vendor, OpenJDK works)

Internet access on the first build (Gradle downloads JavaFX 21 and the
build plugins; subsequent builds are offline)

Target platform: Linux Mint (the JAR has also been verified to run on
macOS and Windows with the same Java requirement)



# **Quick start: run the prebuilt JAR**

A self-contained ("fat") JAR is produced in build/libs/:

java -jar build/libs/**PUT JAR NAME HERE**.jar

JavaFX is bundled inside the JAR, so no separate JavaFX install is required.


# **Building from source**

The project uses Gradle with the bundled
Gradle Wrapper,
so no system-wide Gradle install is needed.

**Linux / macOS:**
./gradlew shadowJar

**Windows:**
gradlew.bat shadowJar

The runnable fat JAR will appear at
build/libs/**PUT JAR NAME HERE**.jar. 
The default build task also
produces it as a side-effect.
To run directly without packaging:
bash./gradlew run



# **Importing into IntelliJ IDEA**

File → Open and select the project root directory (the folder
containing build.gradle).
IntelliJ will detect the Gradle build automatically. Click Trust
Project and Load Gradle Project when prompted.
Wait for the initial Gradle sync to finish (dependencies cache into
~/.gradle/caches/).
Set the project SDK to a Java 21 JDK: File → Project Structure →
Project → SDK.
To run, right-click seng201.team0.App and choose Run 'App.main()'.
Alternatively use the Gradle tool window: adventurer-resource-collector
→ Tasks → application → run.


# **Cloning into IntelliJ from Eng-Git**

File → New → Project from Version Control.

Paste the Eng-Git clone URL 

(https://eng-git.canterbury.ac.nz/seng201-2026/team-66.git).

Pick a target directory and click Clone.
After clone completes, follow steps 2–5 of the "Importing into IntelliJ
IDEA" section above.