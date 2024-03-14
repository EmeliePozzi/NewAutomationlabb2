package automation.labb;
import java.util.ArrayList;
import java.util.List;

public class RunManager {
    private List<Run> runs;

    public RunManager(){
        this.runs = new ArrayList<>();
    }
    
    public List<Run> getRuns(){
        return runs;
    }

    public void saveRun(Run run){
        runs.add(run);
    }

    public Run getRunById(int id) {
        for (Run run : runs) {
            if (run.getId() == id) {
                return run;
            }
        }
        return null; 
    }
    public double calculateTotalDistance(){
        double totalDistance = 0.0;
        for (Run run : runs) {
            totalDistance += run.getDistanceInKm();
        }
        return totalDistance;
    }

    public double calculateAvgDistance(){
        double totalDistance = 0.0;
        for (Run run : runs) {
            totalDistance += run.getDistanceInKm();
        }
        return totalDistance / runs.size();

    }
    public String getDetailsForRun(int runId) {

        Run run = getRunById(runId);
        if (run != null) {
            String details = "Run ID: " + run.getId() + "\n" +
                             "Distance: " + run.getDistanceInKm() + "km\n" +
                             "Time: " + run.getTime() + "\n" +
                             "Date: " + run.getDate() + "\n";
            return details;
        } else {
            return "Run not found";

        }
        }

    public void deleteRun(int runId){
        for (Run run : runs) {
            if (run.getId() == runId) {
            runs.remove(run);
            break;
            }
        }
    }
}
