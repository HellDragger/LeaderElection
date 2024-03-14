import java.util.Scanner;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class Ring {
    // Create an array of Processor objects
    public static Processor[] createProcessors(int n) {
        return new Processor[n];
    }

    // Assign unique IDs to each processor
    public static void assignUniqueIDs(Processor[] processors) {
        Set<Integer> idStorage = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < processors.length; i++) {
            int randomId;
            do {
                randomId = random.nextInt(3 * processors.length) + 1;
            } while (idStorage.contains(randomId));
            idStorage.add(randomId);
            processors[i] = new Processor(randomId);
        }
    }

    // Print unique IDs of processors
    public static void printUniqueIDs(Processor[] processors) {
        for (int i = 0; i < processors.length; i++) {
            System.out.println("Processor " + (i + 1) + " own ID: " + processors[i].getOwnID());
        }
    }

    // Assign next IDs to processors based on processor order
    public static void assignNextIDs(Processor[] processors) {
        for (int i = 0; i < processors.length; i++) {
            int nextIndex = (i + 1) % processors.length;
            processors[i].setNextID(processors[nextIndex].getOwnID());
        }
    }

    // Print next IDs of processors
    public static void printNextIDs(Processor[] processors) {
        for (int i = 0; i < processors.length; i++) {
            System.out.println("Processor " + (i + 1) + " nextID: " + processors[i].getNextID());
        }
    }

    // Set the round to wake up for every processor
    public static void setWakeUpRounds(Processor[] processors) {
        Random random = new Random();
        for (Processor processor : processors) {
            int wakeUpRound = random.nextInt(processors.length) + 1;
            processor.setWakeUpRound(wakeUpRound);
        }

    }

    // Print wake up rounds of processors
    public static void printWakeUpRounds(Processor[] processors) {
        for (int i = 0; i < processors.length; i++) {
            System.out.println("Processor " + (i + 1) + " wake round: " + processors[i].getWakeUpRound());
        }
    }

    // Initialize status of all processors as "Asleep"
    public static void initializeStatus(Processor[] processors) {
        for (Processor processor : processors) {
            processor.setStatus("Asleep");
        }
    }

    public static void printStatus(Processor[] processors) {
        for (int i = 0; i < processors.length; i++) {
            System.out.println("Processor " + (i + 1) + " is: " + processors[i].getStatus());
        }
    }

    public static void initializeMaxID(Processor[] processors) {
        for (Processor processor : processors) {
            processor.setMaxID(processor.ownID);
        }
    }

    public static void printMaxID(Processor[] processors) {
        for (int i = 0; i < processors.length; i++) {
            System.out.println("Processor " + (i + 1) + " max ID: " + processors[i].getMaxID());
        }
    }

    public static void printInID(Processor[] processors) {
        for (int i = 0; i < processors.length; i++) {
            System.out.println("Processor " + (i + 1) + " In ID: " + processors[i].getInID());
        }
    }

    public static void initializeInID(Processor[] processors) {
        for (Processor processor : processors) {
            processor.setInID(0);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of processors: ");
        int n = scanner.nextInt();
        Processor[] processors = createProcessors(n);
        assignUniqueIDs(processors);
        printUniqueIDs(processors);
        System.out.println("________________________");
        assignNextIDs(processors);
        printNextIDs(processors);
        System.out.println("________________________");
        setWakeUpRounds(processors);
        printWakeUpRounds(processors);
        System.out.println("________________________");
        initializeStatus(processors);
        initializeMaxID(processors);
        printMaxID(processors);
        System.out.println("________________________");
        initializeInID(processors);

        int round = 1;
        boolean flag = false;
        do{
            for (Processor processor : processors) {
                if (processor.getWakeUpRound() == round)
                {
                    processor.setStatus("Awake");
                }
                if (processor.getStatus().equals("Awake"))
                {
                    int next_id = processor.getNextID();
                    int ID = processor.getMaxID();
                    for (Processor processor2 : processors) {
                        if(processor2.ownID == next_id)
                        {
                            processor2.setInID(ID);

                        }
                    }
                    if (processor.maxID < processor.inID)
                    {
                        System.out.println("For Processor "+ processor.ownID +": " + processor.getMaxID() + " < "+ processor.getInID());
                        System.out.println("Max ID has been updated\n");
                        processor.setMaxID(processor.getInID());
                    }
                    else if (processor.maxID == processor.inID && processor.inID == processor.ownID)
                    {
                        processor.setStatus("Leader");
                        flag = true;
                        break;
                    }
                }

            }
            for (Processor processor : processors) {
                System.out.println("After Round "+ round + ":");
                System.out.println("Processor with ID "+ processor.getOwnID()+ " has data\nStatus: "+processor.getStatus()+"\nIn ID: "+processor.getInID()+"\nMax ID: "+processor.getMaxID());
                System.out.println("________________________");
            }
            round++;
        }while(!flag);
        System.out.println("Leader elected after " + round + " rounds");
        for (Processor processor : processors) {
            if (processor.getStatus().equals("Leader"))
            {
                System.out.println("Leader info:\nID: "+ processor.getOwnID() + "\nStatus: "+processor.getStatus());
                System.out.println("________________________");
            }
        }
        int leaderID = 0;

        // Find the leader processor
        for (Processor processor : processors) {
            if (processor.getStatus().equals("Leader")) {
                leaderID = processor.getOwnID();
                break; // Exit the loop once leader is found
            }
        }
        round = round +1;
        // Set leader ID for all processors
        if (leaderID != 0) {
            for (Processor processor : processors) {
                processor.setLeaderID(leaderID);
                
            }
        } else {
            System.out.println("No leader found. Leader ID not set.");
        }
        
        System.out.println("Process terminated after:"+ round+" rounds after informing all processors about elected leader");

        scanner.close();
    }
}