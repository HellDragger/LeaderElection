# Ring Election Algorithm 3.1

## Overview

This repository contains Java files implementing a ring election algorithm. The algorithm simulates the election of a leader among a group of processors arranged in a ring network.

## Files

- `Processor.java`: Defines the `Processor` class representing individual processors in the ring network.
- `Ring.java`: Contains the main implementation of the ring election algorithm, including processor initialization, election logic, and output display.
  
## Instructions for Execution

1. **Compile Java Files**: Make sure you have Java Development Kit (JDK) installed on your system. Compile both `Processor.java` and `Ring.java` using the following commands:

    ```bash
    javac Processor.java
    javac Ring.java
    ```

2. **Execute the Program**: After successful compilation, run the program using the following command:

    ```bash
    java Ring
    ```

3. **Input Number of Processors**: Follow the prompt to enter the number of processors participating in the ring network. Input the desired number and press Enter.

4. **Observing Execution**: The program will execute the ring election algorithm, displaying the status of processors at each round and electing a leader. Follow the on-screen prompts and observe the output to understand the election process.

5. **Program Termination**: Once a leader is elected, the program will display the leader's information and terminate.

## Additional Notes

- The program utilizes random generation for assigning unique IDs to processors, setting wake-up rounds, and simulating the network topology.
- The ring election algorithm ensures the eventual election of a leader even in the presence of failures or message loss within the network.
