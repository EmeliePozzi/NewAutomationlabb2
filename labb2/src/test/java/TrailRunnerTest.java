

import org.junit.jupiter.api.*;

import automation.labb.Person;
import automation.labb.Run;
import automation.labb.RunManager;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.time.LocalDate;



public class TrailRunnerTest {
    private Run run;
    private Run run1;
    private Run run2;
    private Run run3;
    private Run runWithoutDate;

    private RunManager runManager;
    private Person person;


    @BeforeEach
    public void setUp(){
        this.run = new Run(50.0, Duration.ofHours(1).plusMinutes(10).plusSeconds(1), LocalDate.of(2022, 3, 7));
        this.run1 = new Run(45.0, Duration.ofHours(1).plusMinutes(15).plusSeconds(5), LocalDate.of(2024, 1, 4));
        this.run2 = new Run(50.0, Duration.ofHours(0).plusMinutes(59).plusSeconds(10), LocalDate.of(2023, 2, 3));
        this.run3 = new Run(50.0, Duration.ofHours(0).plusMinutes(40).plusSeconds(15), LocalDate.of(2023, 2, 3));

        this.runWithoutDate = new Run(50.0, Duration.ofHours(1).plusMinutes(10).plusSeconds(1));

        this.runManager = new RunManager();

        this.runManager.saveRun(this.run);
        this.runManager.saveRun(this.run1);
        this.runManager.saveRun(this.run2);
        this.runManager.saveRun(this.run3);

        this.person = new Person(1.80, 70);
        
        Run.resetLastUsedId();

   }

    @Test
     public void shouldSaveDistance(){

        double distance = run.getDistanceInKm();
        
        assertEquals(50.0, distance);
    }

    @Test
    public void shouldSaveTime(){

       
        Duration expectedAnvandarVaraktighet = Duration.ofHours(1).plusMinutes(10).plusSeconds(1); 
        Duration time = run.getTime();

        assertEquals(expectedAnvandarVaraktighet, time);
    }

    @Test
    public void shouldSaveDate(){

        LocalDate expectedDate = LocalDate.of(2022, 3, 7);
        LocalDate actualDate = run.getDate();

        assertEquals(expectedDate, actualDate);

    }

    @Test
    public void shouldSetTodaysDateWhenInputIsNothing(){

        LocalDate expectedDate = LocalDate.now();
        LocalDate actualDate = runWithoutDate.getDate();

        assertEquals(expectedDate, actualDate);
    }
    
    @Test 
    public void shouldSetID(){

        assertEquals(1, run.getId());
        assertEquals(2, run1.getId());

    } 

    @Test
    public void ShouldHaveUniqueID(){

        int id1 = run.getId();
        int id2 = run1.getId();

        assertNotEquals(id1, id2);

    }

    @Test
    public void shouldCalculateAvgSpeed(){
        
    
        double expectedSpeed = 50.0/1.183;
        double actualSpeed = run.getAvgSpeed();

        assertEquals(expectedSpeed, actualSpeed, 0.6);
    }

    @Test
    public void shouldCalculateKilometerTime(){
        Duration expectedTimeInHours = Duration.ofHours(1).plusMinutes(10).plusSeconds(1); 
    
        double expectedTime = (double) expectedTimeInHours.toMinutes() / 50;
        double actualTime = run.getKilometerTime();

        assertEquals(expectedTime, actualTime, 0.1);
    }

    @Test
    public void enterLengthAndWeight(){
        double expectedLength = 1.80;
        double expectedWeight = 70.0;

        double actualLength = person.getLength();
        double actualWeight = person.getWeight();

        assertEquals(expectedLength, actualLength, 0.01);
        assertEquals(expectedWeight, actualWeight, 0.01);

    }

    @Test
    public void calculateBMI(){
        double expectedBMI = 70.0/(1.80*1.80);

        double actualBMI = person.getBMI(person.getLength(), person.getLength());
        
        assertEquals(expectedBMI, actualBMI, 0.01);
    }

    @Test
    public void shouldSaveRunsInCollection(){

        assertEquals(4, runManager.getRuns().size());
        assertEquals(run, runManager.getRuns().get(0));
    }
   
    @Test
    public void shouldCalculateTotalDistanceOfRuns(){    

        double expectedTotalDistance = 50.0 + 45.0 + 50.0 + 50.0;
        double actualTotalDistance = runManager.calculateTotalDistance();

        assertEquals(expectedTotalDistance, actualTotalDistance);
    }

    @Test
    public void shouldCalculateAvgDistance(){

        double expectedAvgDistance = (50.0 + 45.0 + 50.0 + 50.0) /4;
        double actualAvgDistance = runManager.calculateAvgDistance();

        assertEquals(expectedAvgDistance, actualAvgDistance);
    }

    @Test
public void shouldGetDetailsForRun() {
    
    String expectedDetails = 
        "Run ID: " + 1 + "\n" +
        "Distance: " + 50.0 + "km\n" +
        "Time: " + Duration.ofHours(1).plusMinutes(10).plusSeconds(1) + "\n" +
        "Date: " + LocalDate.of(2022, 3, 7) + "\n";

    String actualDetails = runManager.getDetailsForRun(1);

    assertEquals(expectedDetails, actualDetails);
}
    @Test
    public void shouldDeleteRunFromCollection(){
 
        int initialSize =runManager.getRuns().size();
            
        runManager.deleteRun(123);

        assertEquals(initialSize, runManager.getRuns().size());

        runManager.deleteRun(run1.getId());

        assertEquals(initialSize - 1, runManager.getRuns().size());

        runManager.deleteRun(run1.getId());

        assertEquals(3, runManager.getRuns().size());

    }
    @Test
public void shouldReturnNullForNonExistingRun() {
    RunManager runManager = new RunManager();

    String details = runManager.getDetailsForRun(999); 

    assertEquals("Run not found", details);
}

@Test
public void shouldReturnRunWhenIdMatches() {
    RunManager runManager = new RunManager();
    Run run1 = new Run(45.0, Duration.ofHours(1).plusMinutes(15).plusSeconds(5), LocalDate.of(2024, 1, 4));
    runManager.saveRun(run1);

    Run foundRun = runManager.getRunById(1); 
    assertNotNull(foundRun); 

    Run notFoundRun = runManager.getRunById(999); 
    assertNull(notFoundRun); 
}

}
