# Overview
Creating a datasource based on external configuration and profiles.

# Findbugs
Findbugs runs as part of the build and will fail on certain conditions.

Findbugs can be run independently with the following commands:
 - `mvn findbugs:findbugs` - see report in target
 - `mvn findbugs:gui` - displays a GUI showing any problems found.

Findbugs analyses the compiled .class files so ensure a build has taken place before running.
