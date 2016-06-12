#!/bin/bash

if [ $# -ne 2 ]; then
    echo "Incorrect parameters"
    echo "Usage: ./$0 <base-name> <problem-count>"
    echo ""
    exit 1
fi

INT2CHAR=(ignore A B C D E F G H I J K L M N O P Q R S T U V W X Y Z)

CONTEST="$1"
PROBLEM_CNT="$2"

for i in `seq "$PROBLEM_CNT"`; do
    JAVA_CLASS="${CONTEST}_${INT2CHAR[i]}"
    JAVA_FILE="${JAVA_CLASS}.java"

    if [ -f "${JAVA_FILE}" ]; then
        echo "Error: File ${JAVA_FILE} already exists"
        exit 1
    fi

    echo "Creating ${JAVA_FILE}"
    cp "src/main/java/Template.java" "src/main/java/${JAVA_FILE}"
    sed -i "s/Template/${JAVA_CLASS}/" "src/main/java/${JAVA_FILE}"


    TEST_CLASS="${JAVA_CLASS}Test"
    TEST_FILE="${TEST_CLASS}.java"

    if [ -f "${TEST_FILE}" ]; then
        echo "Error: File ${TEST_FILE} already exists"
        exit 1
    fi

    echo "Creating ${TEST_FILE}"
    cp "src/test/java/TemplateTest.java" "src/test/java/${TEST_FILE}"
    sed -i "s/TemplateTest/${TEST_CLASS}/" "src/test/java/${TEST_FILE}"
    sed -i "s/Template\./${JAVA_CLASS}./g" "src/test/java/${TEST_FILE}"

done
