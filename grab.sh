#!/bin/bash

if [ $# -ne 2 ]; then
    echo "Incorrect parameters"
    echo "Usage: ./$0 <base-url> <problems>"
    echo "Example: ./$0 http://codeforces.com/contest/670/problem/ \"A B C D E F\""
    echo "         (The problems are treated as URL suffixes.)"
    echo ""
    exit 1
fi

BASE_URL="$1"
URL_SUFFIXES="$2"

for PROBLEM in $URL_SUFFIXES; do
    URL="${BASE_URL}${PROBLEM}"

    echo "Downloading problem ${PROBLEM} from ${URL}"
    wget -O "${PROBLEM}.html" "${URL}" 2> /dev/null

    FORMULA_CNT=$(cat "${PROBLEM}.html" | grep -v "<meta name" | grep -e "[a-fA-F0-9]\{40\}" | wc -l)
    if [ $FORMULA_CNT -ne 0 ]; then
        echo " - Warning: ${FORMULA_CNT}+ formula image(s) detected"
    fi
    echo " - DONE"
done
