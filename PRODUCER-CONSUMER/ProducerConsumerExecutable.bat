@echo off
title Producer Consumer Simulation
echo Starting Producer-Consumer Simulation...
echo ----------------------------------------

:: Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in your system PATH.
    pause
    exit
)

:: Run the Jar file
:: Ensure the jar file is named exactly "ProducerConsumer.jar"
java -jar ProducerConsumer.jar

echo.
echo ----------------------------------------
echo Execution Finished.
pause