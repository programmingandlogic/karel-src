#!/usr/bin/env bash

ABSOLUTE_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "ABS_PATH: ${ABSOLUTE_PATH}"

echo "Compiling Karel.jar"
javawrapper /usr/lib/jvm/java-6-jdk javac -d . ../acm/*/*.java ../stanford/karel/*.java
echo DONE
