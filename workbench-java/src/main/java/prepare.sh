#!/bin/bash

if [ $# -ne 2 ]; then
    echo "Incorrect parameters"
    echo "Usage: ./$0 <base-name> <problem-count>"
    echo ""
    exit 1
fi

INT2CHAR=(ignore A B C D E F G H I J K L M N O P Q R S T U V W X Y Z)

SRC=Template
CONTEST="$1"
PROBLEM_CNT="$2"

for i in `seq "$PROBLEM_CNT"`; do
    TARGET="${CONTEST}_${INT2CHAR[i]}"
    TARGET_FILE="${TARGET}.java"

    if [ -f "$TARGET_FILE" ]; then
        echo "Error: File $TARGET_FILE already exists"
        exit 1
    fi

    echo "Creating $TARGET_FILE"
    cp "${SRC}.java" "$TARGET_FILE"
    sed -i "s/$SRC/$TARGET/" "$TARGET_FILE"

done
