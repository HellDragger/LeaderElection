# Leader Election Algorithm 3.1(Asyncronous start and termination of Directed Ring Network)

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
    or
   ```bash
   javac Processor.java Ring.java
   ```

3. **Execute the Program**: After successful compilation, run the program using the following command:

    ```bash
    java Ring
    ```

4. **Input Number of Processors**: Follow the prompt to enter the number of processors participating in the ring network. Input the desired number and press Enter.

5. **Observing Execution**: The program will execute the ring election algorithm, displaying the status of processors at each round and electing a leader. Follow the on-screen prompts and observe the output to understand the election process.

6. **Program Termination**: Once a leader is elected, the program will display the leader's information and terminate.

## Additional Notes

- The program utilizes random generation for assigning unique IDs to processors, setting wake-up rounds, and simulating the network topology.
- The ring election algorithm ensures the eventual election of a leader even in the presence of failures or message loss within the network.
- Being an asynchronous network, determining the number of rounds required for termination is challenging. Therefore, in the program, we utilize a boolean flag to terminate the program after the election of the leader node.

## Time Complexity
- The time complexity of this algorithm mainly depends on the number of rounds it takes for a leader to be elected.
- In the worst-case scenario, each processor needs to wake up and participate in the communication rounds until the leader is elected.
- Assuming each processor has a distinct start round `ri`, the maximum number of rounds required for a leader to be elected is bounded by the maximum value of `ri`.
- Thus, the time complexity can be expressed as O(max(ri)), where `ri` represents the start rounds of all processors.

## Message Complexity
- In this algorithm, each processor communicates with its neighbor to exchange information about the maximum ID seen so far.
- Since it's a ring-based algorithm, each processor sends messages to its adjacent processors.
- In each round, every processor sends and receives one message, except for the final leader.
- Therefore, the total number of messages exchanged is proportional to the number of processors (n).
- Thus, the message complexity is O(n).

## Measure of Correctness
- The algorithm guarantees correctness by ensuring that each processor updates its maximum ID seen so far based on the messages received from its neighbors.
- It also ensures termination by having a condition to break the loop and declare a leader once a processor identifies itself as having the maximum ID seen.
- The termination condition `flag` ensures that the loop terminates when a leader is elected.
- Additionally, the algorithm prints the elected leader's information once the termination condition is met.
- Hence, the correctness is ensured by the termination condition and the consistency in updating and comparing the maximum IDs among processors.
