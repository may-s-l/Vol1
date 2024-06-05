import Domain.Day;
import Domain.MorningShift;
import Domain.MyPair;
import Domain.Week;
import Controllers.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //2007-12-03
        MasterController MC =new MasterController();
        HRManagerEmployeeController EC=MC.getHR_Employee();
        EC.createBranch("M SUPERLEE","HJUHU87");
        EC.createJob("SHIFT MENGER");
        EC.createJob("driver");
        EC.createEmployee("GHG","123456","12345678","M SUPERLEE",14,"2024-05-06",123,"Full","hOURLY","driver");

        HRManagerShiftController SC= MC.getHR_Shift();
        MyPair<Week,List<List<Object>>>SCHEDULE= SC.MakeScheduleforNextWeek(1,"2024-06-09");
        System.out.println(SCHEDULE);
        //System.out.println(W.weekInTableToShow());






    }
}