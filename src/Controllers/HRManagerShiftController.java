package Controllers;

import Domain.*;
import Data.*;

import java.time.DayOfWeek;
import java.time.LocalDate;


public class HRManagerShiftController {

    private Temp_DataBase Temp_Database;

    public HRManagerShiftController(Temp_DataBase Temp_DataBase) {
        this.Temp_Database=Temp_DataBase;
    }



    public Week createWeekforassignment(String date) {
        LocalDate Ldate = LocalDate.parse(date);
        if (Ldate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            Week week = new Week(Ldate);
            Day day;
            for (int i = 0; i < 7; i++) {
                day = week.getDayOfWeek(Ldate.plusDays(i));
                if (!day.isIsdayofrest()) {
                    for (Job j : Temp_Database.getEmployeejobs_temp_database()) {
                        if (!(j instanceof ManagementJob)) {
                            day.getShiftsInDay()[0].addJobToShift(j);
                            day.getShiftsInDay()[1].addJobToShift(j);
                        }
                    }
                }
            }
            return week;

        }
        return null;
    }
//
//
//    public boolean isItTheTIMEtoAssignmenttToShifts(){
//        LocalDate today= LocalDate.now();
//        DayOfWeek week_day=today.getDayOfWeek();
//        if(week_day==THURSDAY|week_day==FRIDAY){
//            return true;
//        }
//        return false;
//    }

    }
