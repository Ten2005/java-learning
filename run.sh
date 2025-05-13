#!/bin/bash

JAVAFX_SDK_PATH="./javafx-sdk-21.0.7/lib"

echo "Compiling NumberGuessingFX.java..."
javac --module-path "$JAVAFX_SDK_PATH" --add-modules javafx.controls NumberGuessingFX.java

if [ $? -ne 0 ]; then
  echo "Compilation failed."
  exit 1
fi

echo "Running NumberGuessingFX..."
java --module-path "$JAVAFX_SDK_PATH" --add-modules javafx.controls NumberGuessingFX

echo "Application finished." 