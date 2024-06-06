package Domain;

import Domain.Enums.ShiftType;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class MorningShift extends Shift{


    private static LocalTime Ds_time = LocalTime.parse("06:00:00");
    private static LocalTime De_time= LocalTime.parse("14:00:00");


    public MorningShift(LocalDate date){
        super(Ds_time,De_time,date, ShiftType.MORNING);
    };

    public MorningShift(LocalTime start_time,LocalTime end_time,LocalDate date){
        super(start_time,end_time,date,ShiftType.MORNING);
    }

    public static LocalTime getDs_time() {
        return Ds_time;
    }

    public static void setDs_time(LocalTime ds_time) {
        Ds_time = ds_time;
    }

    public static LocalTime getDe_time() {
        return De_time;
    }

    public static void setDe_time(LocalTime de_time) {
        De_time = de_time;
    }


    @Override
    public String toString() {
        return "Morning Shift "+super.toString();
    }

}
