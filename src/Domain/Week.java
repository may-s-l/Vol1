package Domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

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
}

