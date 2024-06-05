import Domain.*;
import Domain.MyTripel;
import Controllers.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
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
        EC.createEmployee("may","123457","12345678","M SUPERLEE",14,"2024-05-06",123,"Full","hOURLY","driver");

        HRManagerShiftController SC= MC.getHR_Shift();
        MyTripel<Week,List<List<Object>>,MyMap<Integer, Employee>> SCHEDULE= SC.MakeScheduleforNextWeek(1,"2024-06-09");
        //System.out.println(W.weekInTableToShow());
        List<Integer> em= new ArrayList<Integer>();
        em.add(1);
        em.add(2);
        System.out.println(SC.addEmployeetoshift(SC.checkaddEmployeesToShiftsByDateANDJob(em,"driver","morning","2024-06-14",SCHEDULE.getFirst()),SCHEDULE));
        System.out.println(SCHEDULE.getFirst().weekInTableToShow());
        System.out.println(SC.ChangingdefaultvaluesinSpecificShiftNUMworkertoJob("2024-06-14","morning","driver",3,SCHEDULE.getFirst()));
//        System.out.println(SCHEDULE.getFirst().weekInTableToShow());
        System.out.println(SC.addEmployeetoshift(SC.checkaddEmployeesToShiftsByDateANDJob(em,"driver","morning","2024-06-14",SCHEDULE.getFirst()),SCHEDULE));
        System.out.println(SCHEDULE.getFirst().weekInTableToShow());







    }
}