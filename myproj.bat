@echo OFF
SETLOCAL
SET /p name= "Enter project path: "
echo Path specified is: %name%
cd %name%
mvn clean compile test