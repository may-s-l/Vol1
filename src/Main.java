import Domain.Day;
import Domain.MorningShift;
import Domain.Week;
import Controllers.*;
import java.sql.Time;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //2007-12-03
        HRManagerEmployeeController EC =new HRManagerEmployeeController();
        HRManagerShiftController SC=new HRManagerShiftController(EC.getTemp_Database());
        EC.createJob("SHIFT MENGER");
        Week W = SC.createWeekforassignment("2024-06-02");
        System.out.println(W);






    }
}