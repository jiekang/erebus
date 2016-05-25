# erebus
Configurable Java Code Creator

## What it does

erebus generates java code that can be run as a jvm process. This is intended for any kind of testing that needs random java applications to take up cpu cycles, memory, perform input/output, perform threading, or other non-specific process tasks.

## Requirements

Maven
Java 8

## How to use erebus

In erebus directory:
```
$ make install
$ ./bin/erebus <path to config file>
```

A sample config file is included in the samples folder.

This will generate java code located in the output directory along with scripts to easily compile and run the code

## How to use generated code

In erebus directory:

```
$ cd output
$ ./build.sh
$ ./run.sh
```
