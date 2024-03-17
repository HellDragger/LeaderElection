import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class RingInterface {
    static int[] rounds; // Static array to store rounds for each subring
    static int roundIndex = 0;
    static int messageCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of processors from the user
        System.out.print("Enter the number of processors: ");
        int numProcessors = scanner.nextInt();

        // Get the user's choice for interface nodes (none, random, all)
        System.out.println("Enter the number of interface nodes (0 - none, 1 - random, 2 - all): ");
        int interfaceChoice = scanner.nextInt();

        // Create a HashSet to store unique processor IDs for the main ring
        HashSet<Integer> uniqueMainRingIDs = new HashSet<>();

        // Create the array of processors for the main ring
        Processor[] mainRingProcessors = new Processor[numProcessors];
        rounds = new int[numProcessors * numProcessors];

        // Generate random IDs, create processors, and set status to "Asleep"
        for (int i = 0; i < numProcessors; i++) {
            int randomID;
            do {
                randomID = new Random().nextInt(numProcessors * (numProcessors + 1)) + 1;
            } while (uniqueMainRingIDs.contains(randomID));
            uniqueMainRingIDs.add(randomID);

            mainRingProcessors[i] = new Processor(randomID);
            mainRingProcessors[i].setStatus("Asleep");

            if (interfaceChoice == 0) {
                mainRingProcessors[i].setInterface(false);
            } else if (interfaceChoice == 2) {
                mainRingProcessors[i].setInterface(true);
            } else if (interfaceChoice == 1) {
                boolean isInterface;
                isInterface = new Random().nextBoolean();
                mainRingProcessors[i].setInterface(isInterface);
            } else {
                System.out.println("Invalid choice: Select from provided options");
                return;
            }
        }

        // Print main ring information
        System.out.println("\nMain Ring:");
        for (Processor processor : mainRingProcessors) {
            System.out.println("ownID: " + processor.getOwnID());
        }

        // Process interface nodes and create subrings
        
        for (int i = 0; i < numProcessors; i++) {
            if (mainRingProcessors[i].getInterface()) {
                // Create a subring
                ArrayList<Processor> subringProcessors = createSubring(uniqueMainRingIDs, numProcessors);
                // messageCount += subringProcessors.size(); // Count messages for subring creation
                conductLeaderElection(subringProcessors, mainRingProcessors[i]);
                // Print subring information
                // System.out.println("\nSubring for Interface Processor " + mainRingProcessors[i].getOwnID());
                // printSubring(subringProcessors);

            }
        }
        if (interfaceChoice != 0) {
            System.out.println("\nMain Ring (after subring leader election):");
            for (Processor processor : mainRingProcessors) {
                System.out.println("ownID: " + processor.getOwnID());
            }
        }

        // set nextID, maxID, InID
        int inID = 0;
        for (Processor processor : mainRingProcessors) {
            processor.setMaxID(processor.getOwnID());
            processor.setInID(inID);
        }

        int first = mainRingProcessors[0].getOwnID();
        for (int i = 0; i < mainRingProcessors.length; i++) {
            if (i == mainRingProcessors.length - 1) {
                // If it's the last processor, set its next ID to the own ID of the first
                // processor
                mainRingProcessors[i].setNextID(first);
            } else {
                // For other processors, set next ID to the own ID of the next processor
                mainRingProcessors[i].setNextID(mainRingProcessors[i + 1].getOwnID());
            }
        }
        // perform leader elcetion in same way as in subring
        // Initialize round and set status to "Awake" at round 1
        int maxElement = rounds[0];
        for (int element : rounds) {
            if (element > maxElement) {
                maxElement = element;
            }
        }

        int round = 1;
        for (Processor processor : mainRingProcessors) {
            processor.setStatus("Awake");
        }

        int maximumID = 0;
        maximumID = mainRingProcessors[0].getOwnID();
        while (true) {
            boolean leaderFound = false;
            for (int i = 0; i < mainRingProcessors.length; i++) {
                for (Processor processor : mainRingProcessors) {
                    int nextID = processor.getNextID();
                    int max = processor.getMaxID();
                    // Update inID of next processor with maxID and increment message count
                    for (Processor processor2 : mainRingProcessors) {
                        if (processor2.getOwnID() == nextID) {
                            processor2.setInID(max);
                        }
                    }
                    
                    for (Processor processor2 : mainRingProcessors) {
                        maximumID = Math.max(processor2.getOwnID(), maximumID);
                    }
                    for (Processor processor2 : mainRingProcessors) {
                        processor2.setMaxID(maximumID);
                    }
                    if(!leaderFound){
                        if(processor.getOwnID() == maximumID){
                            processor.setStatus("Leader");
                            leaderFound = true;
                            break;
                        }
                    }
                }
            }

            if (leaderFound) {
                int leader = 0;
                for (Processor processor : mainRingProcessors) {
                    if (processor.getStatus().equals("Leader")) {
                        leader = processor.getOwnID();
                    }
                }
                for (Processor processor : mainRingProcessors) {
                    processor.setLeaderID(leader);
                }
                round += 1;
                // System.out.println("Main ring ID changed to:
                // "+mainRingProcessors.getOwnID());
                break; // Exit the election process
            } else {
                round++; // Proceed to the next round
            }
        }
        
        // Print main ring processor details after leader
        for (Processor processor : mainRingProcessors) {
            System.out.println("ID: " + processor.getOwnID() +
                    ", nextID: " + processor.getNextID() +
                    ", maxID: " + processor.getMaxID() +
                    ", Status: " + processor.getStatus() +
                    ", Leader ID: " + processor.getLeaderID());
        }
        round = numProcessors + 2;
        messageCount += round * numProcessors;
        // Print total messages sent
        System.out.println("\nTotal Messages Sent: " + messageCount);
        System.out.println("\nTotal Rounds Required: " + (round + maxElement));
        for (Processor processor : mainRingProcessors) {
            if(processor.getStatus().equals("Leader") && processor.getOwnID() == processor.getMaxID()){
                System.out.println("\nLeader Processor in "+numProcessors+" processors\nID: " + processor.getOwnID() +
                ", nextID: " + processor.getNextID() +
                ", maxID: " + processor.getMaxID() +
                ", Status: " + processor.getStatus());
            }
        }
        System.out.println("\nMaximum in main ring:- "+maximumID);
        for (Processor processor : mainRingProcessors) {
            if(processor.getStatus().equals("Leader")){
                System.out.println("\nElected leader ID: "+processor.getOwnID());
            }
        }

        scanner.close();
    }

    private static ArrayList<Processor> createSubring(HashSet<Integer> uniqueMainRingIDs, int numProcessors) {
        int numProcessorsInSubring = new Random().nextInt(numProcessors) + 1; // 1 to numProcessors
        ArrayList<Processor> subringProcessors = new ArrayList<>();

        // Create a HashMap to store unique IDs for the subring
        HashMap<Integer, Boolean> uniqueSubringIDs = new HashMap<>();

        // Generate random IDs for subring processors, ensuring uniqueness within the
        // subring
        for (int i = 0; i < numProcessorsInSubring; i++) {
            int randomID;
            do {
                randomID = new Random().nextInt(numProcessors * (numProcessors + 1));
            } while (uniqueSubringIDs.containsKey(randomID));
            uniqueSubringIDs.put(randomID, true);

            Processor processor = new Processor(randomID);
            subringProcessors.add(processor);
        }

        // Set maxID, InID, and status for each processor in the subring
        int maxID = 0;
        for (Processor processor : subringProcessors) {
            maxID = Math.max(maxID, processor.getOwnID()); // Find the maximum ID
            processor.setMaxID(maxID); // Set maxID for each processor
            processor.setInID(0); // Initialize InID with 0
            processor.setStatus("Asleep"); // Set status to "Asleep"
        }

        // Set nextID for each processor in the subring
        for (int i = 0; i < numProcessorsInSubring; i++) {
            int nextID;
            if (i == numProcessorsInSubring - 1) {
                nextID = subringProcessors.get(0).getOwnID();
            } else {
                nextID = subringProcessors.get(i + 1).getOwnID();
            }
            subringProcessors.get(i).setNextID(nextID);
        }
        int round = numProcessorsInSubring + 2;
        if (roundIndex < rounds.length) {
            rounds[roundIndex++] = round; // Append round to the array
        } else {
            System.out.println("Warning: Rounds array is full. Cannot store rounds for additional subrings.");
        }
        messageCount += (round * numProcessorsInSubring);

        return subringProcessors;
    }

    private static void printSubring(ArrayList<Processor> subringProcessors) {
        for (Processor processor : subringProcessors) {
            System.out.println("ID: " + processor.getOwnID() +
                    ", nextID: " + processor.getNextID() +
                    ", maxID: " + processor.getMaxID() +
                    ", inID: " + processor.getInID() +
                    ", Status: " + processor.getStatus());
        }
    }

    private static void conductLeaderElection(ArrayList<Processor> subringProcessors, Processor mainRingProcessors) {

        // Initialize round and set status to "Awake" at round 1
        int round = 1;
        for (Processor processor : subringProcessors) {
            processor.setStatus("Awake");
        }

        while (true) {
            boolean leaderFound = false;
            for (int i = 0; i < subringProcessors.size(); i++) {
                for (Processor processor : subringProcessors) {
                    int nextID = processor.getNextID();
                    int max = processor.getMaxID();
                    // Update inID of next processor with maxID and increment message count
                    for (Processor processor2 : subringProcessors) {
                        if (processor2.getOwnID() == nextID) {
                            processor2.setInID(max);
                        }
                    }
                    // Check for leader condition (maxID < inID or maxID=inID=ownID)
                    if (processor.getMaxID() < processor.getInID()) {
                        processor.setMaxID(processor.getInID()); // Update maxID with higher value
                    } else if (processor.getMaxID() == processor.getInID()
                            && processor.getMaxID() == processor.getOwnID()) {
                        processor.setStatus("Leader"); // Found the leader
                        leaderFound = true;
                        break;
                    }
                }
            }

            if (leaderFound) {
                int leader = 0;
                for (Processor processor : subringProcessors) {
                    if (processor.getStatus().equals("Leader")) {
                        leader = processor.getOwnID();
                    }
                    processor.setLeaderID(leader);
                }
                round += 1;
                mainRingProcessors.setOwnID(leader);
                break; // Exit the election process
            } else {
                round++; // Proceed to the next round
            }
        }
    }
}
