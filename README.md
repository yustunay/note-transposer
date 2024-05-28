# Note Transposer Application

## Overview

This Java application transposes musical notes based on input parameters. It reads a JSON file containing a collection of musical notes, adjusts each note by a specified number of semitones, and outputs the transposed notes to a JSON file. The project uses Gradle for building and managing dependencies.

## Prerequisites

Before you build and run the application, ensure you have the following installed:
- Java JDK 17 (or higher)
- Gradle 7.0 (or higher)

## Building the Project

To build the project and generate an executable `.jar` file, run the following command from the root of the project:

```bash
./gradlew clean build
```
This command compiles the Java code, runs any tests, and creates a JAR file in the build/libs directory.

## Running the Application
To run the application, use the following command:
```bash
java -jar task.jar in/a.json [semitones] out/b.json
```

Replace [semitones] with the number of semitones you want to transpose the notes, e.g., 5 for 5 semitones. Make sure the input/a.json file exists with the proper format.

This will read the notes from in/a.json, transpose them up by 5 semitones, and write the result to out/b.json.

## Input File Format
The input JSON file should contain an array of note arrays, where each sub-array contains two integers representing the octave and note number respectively. Example:

```bash
[
    [2, 1], [2, 6], ...
]
```

## Output File
The output file will be a JSON file with the transposed notes in the same format as the input.

## Running Tests

This project uses JUnit 5 for unit testing. You can run the tests to verify the functionality of the application and ensure that all components behave as expected under various scenarios.

## How to Run Tests

To execute all tests in the project, use the following command in the root directory of the project:

```bash
./gradlew test
```

This command will run all tests defined in the src/test/java directory.

## Viewing Test Results
After running the tests, you can find a detailed HTML report in the following directory:
```bash
build/reports/tests/test/index.html
```
Open this file in a web browser to view a detailed report of all test cases, including information on which tests passed, failed, or were skipped.