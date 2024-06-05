package Controllers;
import Domain.*;
import Data.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class HRManagerShiftController {

    private MyMap<String, Branch> Branch_temp_database;//String key address
    private List<Job> Employeejobs_temp_database;
    private MyMap<String, Employee> Employees_temp_database;//String key ID

    public HRManagerShiftController(List<Job> Employeejobs_temp_database,MyMap<String, Branch> Branch_temp_database,MyMap<String, Employee> Employees_temp_database) {
        this.Employees_temp_database=Employees_temp_database;
        this.Branch_temp_database=Branch_temp_database;
        this.Employeejobs_temp_database=Employeejobs_temp_database;
    }

    public String MakeScheduleforNextWeek(int branchNum,String date){
        Branch branch =getBranchByBranchNUM(branchNum);
        List<List<Object>> TableofEmployeeandConstrin =createEmployeeConstraintJobTable(branch.getEmployeesInBranch(),date);
        Week Weekforassignment = createWeekforassignment(date);
        return toStringEmployeeConstraintJobTable(TableofEmployeeandConstrin)+Weekforassignment.weekInTableToShow();
    }


    private List<List<Object>> createEmployeeConstraintJobTable(MyMap<String, Employee> employeeInBranch, String date) {
        List<List<Object>> employeesWithConstraints = new ArrayList<>();
        List<List<Object>> employeesWithoutConstraints = new ArrayList<>();
        Set<String> employeeIDs = employeeInBranch.getKeys();
        LocalDate startDay = LocalDate.parse(date);

        if (startDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            for (String id : employeeIDs) {
                List<Constraint> constraintList = new ArrayList<>();
                Employee emp = employeeInBranch.get(id);

                for (int i = 0; i < 7; i++) {
                    LocalDate constraintDate = startDay.plusDays(i);
                    Constraint constraint = emp.getConstraintByDate(constraintDate);

                    if (constraint != null) {
                        constraintList.add(constraint);
                    }
                }

                List<Object> employeeData = new ArrayList<>();
                employeeData.add(emp);
                employeeData.add(emp.getJobs());
                employeeData.add(constraintList);

                if (constraintList.isEmpty()) {
                    employeesWithoutConstraints.add(employeeData);
                } else {
                    employeesWithConstraints.add(employeeData);
                }
            }
        }

        List<List<Object>> Table = new ArrayList<>(employeesWithConstraints);
        Table.addAll(employeesWithoutConstraints);
        return Table;
    }

    private Week createWeekforassignment(String date) {
        LocalDate Ldate = LocalDate.parse(date);
        if (Ldate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            Week week = new Week(Ldate);
            Day day;
            for (int i = 0; i < 7; i++) {
                day = week.getDayOfWeek(Ldate.plusDays(i));
                if (!day.isIsdayofrest()) {
                    for (Job j : Employeejobs_temp_database) {
                        if (!(j instanceof ManagementJob)) {
                            day.getShiftsInDay()[0].addJobToShift(j);
                            day.getShiftsInDay()[1].addJobToShift(j);
                        }
                    }
                }
            }
            return week;
        }
        throw new IllegalArgumentException("Date of start must be SUNDAY");
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

    public Branch getBranchByBranchNUM(int branchnum)throws IllegalArgumentException{
        List<Branch> allBranch=getAllBranch();
        Branch branch=null;
        for(Branch b:allBranch){
            if(b.getBranchNum()==branchnum){
                branch=b;
                break;
            }
        }
        if (branch==null){
            throw new IllegalArgumentException("Branch is NOT exist");
        }
        return branch;
    }


    public List<Branch> getAllBranch() {
        Set<String>Brenchskey=this.Branch_temp_database.getKeys();
        List<Branch> Allbranch=new ArrayList<Branch>();
        for(String b:Brenchskey){
            Allbranch.add(this.Branch_temp_database.get(b));
        }
        return Allbranch;
    }

    private String toStringEmployeeConstraintJobTable(List<List<Object>> employeeTable) {

        int idWidth = 25;
        int jobsWidth = 40;
        int constraintsWidth = 40;

        StringBuilder tableBuilder = new StringBuilder();

        tableBuilder.append(String.format("| %-" + idWidth + "s | %-" + jobsWidth + "s | %-" + constraintsWidth + "s |%n",
                "Employee ID", "Jobs", "Constraints"));
        tableBuilder.append(String.join("", Collections.nCopies(idWidth + jobsWidth + constraintsWidth + 8, "-"))).append("\n");

        for (List<Object> employeeData : employeeTable) {
            Employee employee = (Employee) employeeData.get(0);
            List<Job> jobs = (List<Job>) employeeData.get(1);
            List<Constraint> constraints = (List<Constraint>) employeeData.get(2);

            String jobsString =  jobs.isEmpty() ? "None" : jobs.stream()
                    .map(Job::toString)
                    .collect(Collectors.joining(", "));
            String constraintsString = constraints.isEmpty() ? "None" : constraints.stream()
                    .map(Constraint::toString)
                    .collect(Collectors.joining(", "));

            tableBuilder.append(String.format("| %-" + idWidth + "s | %-" + jobsWidth + "s | %-" + constraintsWidth + "s |%n",
                    employee, jobsString, constraintsString));
        }

        return tableBuilder.toString();
    }



}
