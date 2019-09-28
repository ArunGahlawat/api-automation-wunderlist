@echo OFF
SETLOCAL
SET /p name= "Enter project path: "
echo Path specified is: %name%
cd %name%
call mvn clean compile test -DlogLevl=DEBUG