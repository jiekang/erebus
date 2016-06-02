#!/bin/bash

if [ "$#" -ne 2 ]; then
	echo "Usage: run-multiple.sh <number of iterations> <command>"
	exit
fi

num="$1"

for((n=0;n<$num;n++)) do $2 & done
