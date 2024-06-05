package Domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

public class Week {
    private int weekNUM;
    private LocalDate start_date;
    private LocalDate end_date;
    private MyMap<LocalDate,Day> DayInWEEK;


    public Week(LocalDate start_date) {
        this.start_date = start_date;
        this.DayInWEEK = new MyMap<LocalDate, Day>();
        LocalDate date = start_date;
        Day d = null;
        for (int i = 0; i < 7; i++) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY) {
                d = new Day(false, date);
                this.DayInWEEK.put(date, d);
                if (date.getDayOfWeek() == DayOfWeek.TUESDAY) {
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    this.weekNUM = date.get(weekFields.weekOfWeekBasedYear());
                }
            } else {
                d = new Day(true, date);
                this.DayInWEEK.put(date, d);
                this.end_date = date;
            }
            date = date.plusDays(1);
        }
    }

    public int getWeekNUM() {
        return weekNUM;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }


    public Day getDayOfWeek(LocalDate date){
        return this.DayInWEEK.get(date);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Week number: ").append(weekNUM)
                .append(" ").append(start_date).append(" - ").append(end_date).append("\n");

        for (LocalDate date : DayInWEEK.getKeys()) {
            sb.append(DayInWEEK.get(date)).append("\n");
        }

        return sb.toString();
    }


    public String weekInTableToShow() {
        StringBuilder sb = new StringBuilder();
        int jobWidth = 20;
        int shiftWidth = 15;
        int daydateWidth=33;

        sb.append("Week number: ").append(weekNUM).append("\n");
        sb.append("Start date: ").append(start_date).append("\n");
        sb.append("End date: ").append(end_date).append("\n\n");

        sb.append(String.format("| %-" + jobWidth + "s |", "Job"));
        for (LocalDate date : DayInWEEK.getKeys()) {
            Day day = DayInWEEK.get(date);
            sb.append(String.format(" %-" + daydateWidth + "s |", day.getDayOfWeek() + " " + day.getDate(), ""));
        }
        sb.append("\n");


        sb.append(String.format("| %-" + jobWidth + "s |", ""));
        for (int i = 0; i < DayInWEEK.size(); i++) {
            sb.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", "Morning", "Evening"));
        }
        sb.append("\n");

        sb.append(String.join("", Collections.nCopies(jobWidth + shiftWidth * 2 * DayInWEEK.size() + DayInWEEK.size() + 1, "-"))).append("\n");

        Set<Job> jobsToFill = Shift.getNumberofWorkersPerPositionDifult().getKeys(); // Assuming all days have the same jobs to fill

        for (Job job : jobsToFill) {
            sb.append(String.format("| %-" + jobWidth + "s |", job.getJobName()));
            for (LocalDate date : DayInWEEK.getKeys()) {
                Day day = DayInWEEK.get(date);

                String morningEmployee = "";
                String eveningEmployee = "";

                if (!day.isIsdayofrest()) {
                    Set<Employee> morningShiftEmployees = day.getShiftsInDay()[0].getEmployeeinshiftSet();
                    Set<Employee> eveningShiftEmployees = day.getShiftsInDay()[1].getEmployeeinshiftSet();

                    morningEmployee = morningShiftEmployees.stream().map(Employee::getName).collect(Collectors.joining(", "));
                    eveningEmployee = eveningShiftEmployees.stream().map(Employee::getName).collect(Collectors.joining(", "));
                } else {
                    morningEmployee = "Day off";
                    eveningEmployee = "Day off";
                }

                sb.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", morningEmployee, eveningEmployee));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}

