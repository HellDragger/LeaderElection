public class Processor {
    public int ownID;
    public int nextID;
    public int inID;
    public int maxID;
    public int wake_up_round;
    public String status;
    public int leaderID;
    public boolean isInterface;

    public Processor(int ownID) {
        this.ownID = ownID;
    }

    // Getters and Setters
    public int getOwnID() {
        return ownID;
    }

    public int getNextID() {
        return nextID;
    }

    public void setNextID(int nextID) {
        this.nextID = nextID;
    }

    public int getWakeUpRound() {
        return wake_up_round;
    }

    public void setWakeUpRound(int wake_up_round) {
        this.wake_up_round = wake_up_round;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMaxID(int ownID){
        this.maxID = ownID;
    }

    public int getMaxID(){
        return maxID;
    }

    public void setInID(int inID){
        this.inID = inID;
    }

    public int getInID(){
        return inID;
    }

    public void setLeaderID(int leaderID){
        this.leaderID = leaderID;
    }

    public int getLeaderID(){
        return leaderID;
    }

    public void setInterface(boolean isInterface){
        this.isInterface = isInterface;
    }

    public boolean getInterface()
    {
        return isInterface;
    }

    public void setOwnID(int ownID){
        this.ownID = ownID;
    }
}
