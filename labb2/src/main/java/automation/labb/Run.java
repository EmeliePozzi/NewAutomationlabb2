package automation.labb;

import java.time.Duration;
import java.time.LocalDate;

public class Run {
  protected  double distanceInKm;
   protected Duration time;
   protected LocalDate date;
   protected static int lastUsedId = 0;
   protected int runId;

   public Run(double distanceInKm, Duration time, LocalDate date){
    this.distanceInKm = distanceInKm;
    this.time = time;
    this.date = date;
    this.runId = ++lastUsedId;
   }

   public Run(double distanceInKm, Duration time) {
        this.distanceInKm = distanceInKm;
        this.time = time; 
        this.date = LocalDate.now();
        this.runId = ++lastUsedId;
    }
   
    public Duration getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getId() {
        return runId;
    }

    public double getAvgSpeed() {
        double distance = getDistanceInKm();
        long timeInSeconds = getTime().getSeconds();
        double timeInHours = timeInSeconds / 3600.0;

        return distance / timeInHours;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }

    public double getKilometerTime() {
        double totalMinutes = time.toMinutes();
        return totalMinutes / distanceInKm;
    }
    
    public static void resetLastUsedId() {
        lastUsedId = 0;
    }
}