package Domain;

import Domain.EveningShift;
import Domain.MorningShift;
import Domain.Shift;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day {

    boolean isdayofrest;
    LocalDate Date;
    DayOfWeek dayOfWeek;
    Shift[] shiftsInDay;
    public Day(boolean isdayofrest, LocalDate date) {
        this.isdayofrest = isdayofrest;
        Date = date;
        dayOfWeek=date.getDayOfWeek();
        if (!isdayofrest){
            shiftsInDay= new Shift[2];
            shiftsInDay[0]=new MorningShift(date);
            shiftsInDay[1]=new EveningShift(date);
        }
        else {
            shiftsInDay=null;
        }
    }

    public boolean isIsdayofrest() {
        return isdayofrest;
    }

    public void setIsdayofrest(boolean isdayofrest) {
        this.isdayofrest = isdayofrest;
        if (isdayofrest && shiftsInDay!=null){
            shiftsInDay=null;
        }
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Shift[] getShiftsInDay() {
        return shiftsInDay;
    }

    public void setShiftsInDay(Shift[] shiftsInDay) {
        this.shiftsInDay = shiftsInDay;
    }

    @Override
    public String toString() {
        if(this.isdayofrest){
            return "Date: "+ this.Date+"\n"+
                    "Day of Week : "+this.dayOfWeek+"\n---Day off---\n";
        }
        return  "Date: "+ this.Date+"\n"+
                "Day of Week : "+this.dayOfWeek+"\n"
                +this.shiftsInDay[0].toString()+"\n"+
                this.shiftsInDay[1].toString()+"\n";
    }
}
