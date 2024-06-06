package Domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        int jobWidth = 15;
        int shiftWidth = 30;
        int daydateWidth = 64;
        int rowsPerJob = 3;

        sb.append("Week number: ").append(weekNUM).append("\n");
        sb.append("Start date: ").append(start_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n");
        sb.append("End date: ").append(end_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n\n");

        // Header Row: Job | Day of Week Date | Day of Week Date | ...
        sb.append(String.format("| %-" + jobWidth + "s |", "Job"));
        for (LocalDate date : DayInWEEK.getKeys()) {
            Day day = DayInWEEK.get(date);
            sb.append(String.format(" %-" + daydateWidth + "s|", day.getDayOfWeek() + " " + day.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        }
        sb.append("\n");

        // Header Row: Morning (Start-End) | Evening (Start-End) | ...
        sb.append(String.format("| %-" + jobWidth + "s |", ""));
        for (LocalDate date : DayInWEEK.getKeys()) {
            Day day = DayInWEEK.get(date);
            if (!day.isIsdayofrest()) {
                Shift morningShift = day.getShiftsInDay()[0];
                Shift eveningShift = day.getShiftsInDay()[1];

                String morningShiftHeader = String.format("Morning (%s-%s)",
                        morningShift.getStart_time().toString(),
                        morningShift.getEnd_time().toString());

                String eveningShiftHeader = String.format("Evening(%s-%s)",
                        eveningShift.getStart_time().toString(),
                        eveningShift.getEnd_time().toString());

                sb.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", morningShiftHeader, eveningShiftHeader));
            } else {
                sb.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", "Day off", "Day off"));
            }
        }
        sb.append("\n");

        // Separator line
        sb.append(String.join("", Collections.nCopies(jobWidth + shiftWidth * 2 * DayInWEEK.size() + DayInWEEK.size() + 3, "-"))).append("\n");

        Set<Job> jobsToFill = Shift.getNumberofWorkersPerPositionDifult().getKeys(); // Assuming all days have the same jobs to fill

        for (Job job : jobsToFill) {
            for (int row = 0; row < rowsPerJob; row++) {
                if (row == 0) {
                    sb.append(String.format("| %-" + jobWidth + "s |", job.getJobName()));
                } else {
                    sb.append(String.format("| %-" + jobWidth + "s |", ""));
                }

                for (LocalDate date : DayInWEEK.getKeys()) {
                    Day day = DayInWEEK.get(date);

                    String morningEmployee = "";
                    String eveningEmployee = "";
                    String morningRequired = "";
                    String eveningRequired = "";

                    if (!day.isIsdayofrest()) {
                        Shift morningShift = day.getShiftsInDay()[0];
                        Shift eveningShift = day.getShiftsInDay()[1];

                        List<Employee> morningShiftEmployees = morningShift.getEmployeeinshiftSet().stream()
                                .filter(emp -> emp.getJobs().contains(job))
                                .collect(Collectors.toList());
                        List<Employee> eveningShiftEmployees = eveningShift.getEmployeeinshiftSet().stream()
                                .filter(emp -> emp.getJobs().contains(job))
                                .collect(Collectors.toList());

                        morningRequired = String.valueOf(morningShift.getNumberofWorkersPerJob(job));
                        eveningRequired = String.valueOf(eveningShift.getNumberofWorkersPerJob(job));

                        if (row < morningShiftEmployees.size()) {
                            morningEmployee = morningShiftEmployees.get(row).getName() + "-" + morningShiftEmployees.get(row).getEmployeeNum();
                        }
                        if (row < eveningShiftEmployees.size()) {
                            eveningEmployee = eveningShiftEmployees.get(row).getName() + "-" + eveningShiftEmployees.get(row).getEmployeeNum();
                        }
                    } else {
                        if (row == 0) {
                            morningEmployee = "Day off";
                            eveningEmployee = "Day off";
                            morningRequired = "0";
                            eveningRequired = "0";
                        }
                    }

                    if (row == 0) {
                        sb.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", "(Req:" + morningRequired + ")" + morningEmployee, "(Req:" + eveningRequired + ")" + eveningEmployee));
                    } else {
                        sb.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", morningEmployee, eveningEmployee));
                    }
                }
                sb.append("\n");
            }
            // Separator line after each job row
            sb.append(String.join("", Collections.nCopies(jobWidth + shiftWidth * 2 * DayInWEEK.size() + DayInWEEK.size() + 3, "-"))).append("\n");
        }

        return sb.toString();
    }
}

