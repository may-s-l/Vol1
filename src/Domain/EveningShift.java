package Domain;

import java.sql.Time;
import java.time.LocalDate;

public class EveningShift extends Shift {

    private static Time Ds_time = new Time(14,0,00);
    private static Time De_time=new Time(22,0,00);

    public EveningShift(LocalDate date){
        super(Ds_time,De_time,date);
    };

    public EveningShift(Time start_time,Time end_time,LocalDate date){
        super(start_time,end_time,date);
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

    public String toString() {
        return "Evening Shift "+super.toString();
    }

}
