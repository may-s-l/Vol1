package Domain;

import Domain.Enums.ShiftType;

import java.sql.Time;
import java.time.LocalDate;

public class MorningShift extends Shift{


    private static Time Ds_time = new Time(6,00,00);
    private static Time De_time=new Time(14,00,00);


    public MorningShift(LocalDate date){
        super(Ds_time,De_time,date, ShiftType.MORNING);
    };

    public MorningShift(Time start_time,Time end_time,LocalDate date){
        super(start_time,end_time,date,ShiftType.MORNING);
    }

    public static Time getDs_time() {
        return Ds_time;
    }

    public static void setDs_time(Time ds_time) {
        Ds_time = ds_time;
    }

    public static Time getDe_time() {
        return De_time;
    }

    public static void setDe_time(Time de_time) {
        De_time = de_time;
    }


    @Override
    public String toString() {
        return "Morning Shift "+super.toString();
    }

}
