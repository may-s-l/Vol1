package Controllers;
import Domain.*;
import Domain.Enums.ShiftType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import static java.time.DayOfWeek.*;


public class HRManagerShiftController {

    private MyMap<String, Branch> Branch_temp_database;//String key address
    private List<Job> Employeejobs_temp_database;
    private MyMap<String, Employee> Employees_temp_database;//String key ID
    private MyMap<Integer,MyMap<Integer, Week>> Employees_INBranch_temp_database;//INT keys BranchNUM and EmployeeNUM
    public HRManagerShiftController(List<Job> Employeejobs_temp_database,MyMap<String, Branch> Branch_temp_database,MyMap<String, Employee> Employees_temp_database) {
        this.Employees_temp_database=Employees_temp_database;
        this.Branch_temp_database=Branch_temp_database;
        this.Employeejobs_temp_database=Employeejobs_temp_database;
        this.Employees_INBranch_temp_database=new MyMap<Integer,MyMap<Integer,Week>>();
    }


    //======================Functions for working on weekly employee placement=====================================================//
    public MyTripel<Week,List<List<Object>>,MyMap<Integer, Employee>> MakeScheduleforNextWeek(int branchNum,String date){
        Branch branch =getBranchByBranchNUM(branchNum);
        MyMap<Integer, Employee>BranchemployeeBYemployeeNUM = new MyMap<Integer, Employee>();
        List<List<Object>> TableofEmployeeandConstrin =createEmployeeConstraintJobTable(branch.getEmployeesInBranch(),date,BranchemployeeBYemployeeNUM);
        Week Weekforassignment = createWeekforassignment(date);
        MyTripel<Week,List<List<Object>>,MyMap<Integer, Employee>> pair = new MyTripel<Week,List<List<Object>>,MyMap<Integer, Employee>>(Weekforassignment,TableofEmployeeandConstrin,BranchemployeeBYemployeeNUM);
        return pair;
    }
    public List<Object> checkaddEmployeesToShiftsByDateANDJob(List<Integer> employeeNum,String jobname,String shiftype,String date,Week week) throws IllegalArgumentException{
        if(employeeNum==null||employeeNum.isEmpty()||jobname==null||shiftype==null||date==null){
            throw new IllegalArgumentException("Argumets can not be NULL");
        }
        //-------date------//
        LocalDate dateToCheck= LocalDate.parse(date);
        if(!((dateToCheck.isEqual(week.getStart_date()) || dateToCheck.isAfter(week.getStart_date())) && (dateToCheck.isEqual(week.getEnd_date()) || dateToCheck.isBefore(week.getEnd_date())))){
           throw new IllegalArgumentException("Date must be in week of work");
        }
        //-------EnumShiftType-------//
        shiftype=shiftype.toUpperCase();
        if (!shiftype.equals("MORNING") && !shiftype.equals("EVENING")) {
           throw new IllegalArgumentException("Job type must be morning or evening");
        }
        //-------Job-------//
        int i=1;
        if(shiftype.equals("MORNING")){
           i=0;
        }
        Shift shiftassignment=week.getDayOfWeek(dateToCheck).getShiftsInDay()[i];
        Job job = null;
        for (Job j : shiftassignment.getAllJobInShift()) {
           if (j.getJobName() == jobname) {
               job = j;
               break;
           }
        }
        if (job == null) {
           throw new IllegalArgumentException("Job does not exist in this Shift");
        }
        if(shiftassignment.isJobInShiftisFull(job)){
           throw new IllegalArgumentException("Job is in max capacity for this Shift");
        }
        List<Object> CleanInformation =new ArrayList<Object>() ;
        CleanInformation.add(employeeNum);
        CleanInformation.add(shiftassignment);
        CleanInformation.add(job);
        return CleanInformation;
   }
    public List<String> addEmployeetoshift(List<Object> empsNUM_shift_job,MyTripel<Week,List<List<Object>>,MyMap<Integer, Employee>>WeekAndConstrainAndMAPemployee )throws IllegalArgumentException{
        if(empsNUM_shift_job==null){
            throw new IllegalArgumentException("Argumets can not be NULL");
        }
        Week week =WeekAndConstrainAndMAPemployee.getFirst();
        MyMap<Integer, Employee> empMAP=WeekAndConstrainAndMAPemployee.getThird();
        List<Integer> employeeNum=(List<Integer>)empsNUM_shift_job.get(0);
        Shift shift=(Shift) empsNUM_shift_job.get(1);
        Job job=(Job) empsNUM_shift_job.get(2);
        List<String> employeeListNOTasing=new ArrayList<String>();
        for(int e:employeeNum){
            Employee emp_to_workon=null;
            if(!(empMAP.containsKey(e))){
                employeeListNOTasing.add( e+"- Employee NUMBER is not in this branch or DATA");
            }
            emp_to_workon=empMAP.get(e);
            if(!emp_to_workon.employeeCanbe(job)){
                employeeListNOTasing.add(emp_to_workon.toString()+" Can't work as "+job.getJobName()+"\n");
                continue;
            }
            if(emp_to_workon.getConstraintByDate(shift.getDate())!=null&&(emp_to_workon.getConstraintByDate(shift.getDate()).getShiftType()==shift.getShiftType()||emp_to_workon.getConstraintByDate(shift.getDate()).getShiftType()== ShiftType.FULLDAY)){
                employeeListNOTasing.add(emp_to_workon.toString()+" there is a shift constraint "+emp_to_workon.getConstraintByDate(shift.getDate()).toString()+"\n");
                continue;
            }
            if(shift.isJobInShiftisFull(job)){
                employeeListNOTasing.add(emp_to_workon.toString()+" the number of worker needed is already full \n");
                continue;
            }
            shift.addEmployeeToShift(emp_to_workon,job);
        }
        return employeeListNOTasing;

    }
    public String toStringforweekANDemlpoyeeinbanc(Week week,List<List<Object>> employeeTable){
        return toStringEmployeeConstraintJobTable(employeeTable)+week.weekInTableToShow();
    }
    private List<List<Object>> createEmployeeConstraintJobTable(MyMap<String, Employee> employeeInBranch, String date,MyMap<Integer, Employee>BranchemployeeBYemployeeNUM) {
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
                BranchemployeeBYemployeeNUM.put(emp.getEmployeeNum(),emp);
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
    private Branch getBranchByBranchNUM(int branchnum)throws IllegalArgumentException{
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
    private List<Branch> getAllBranch() {
        Set<String>Brenchskey=this.Branch_temp_database.getKeys();
        List<Branch> Allbranch=new ArrayList<Branch>();
        for(String b:Brenchskey){
            Allbranch.add(this.Branch_temp_database.get(b));
        }
        return Allbranch;
    }
    public String toStringEmployeeConstraintJobTable(List<List<Object>> employeeTable) {

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
    public boolean isItTheTIMEtoAssignmenttToShifts(){
        LocalDate today= LocalDate.now();
        DayOfWeek week_day=today.getDayOfWeek();
        if(week_day==THURSDAY|week_day==FRIDAY){
            return true;
        }
        return false;
    }

    //---------------------------Functions for changing default values for a specific shift-----------------------------------------//
    public String ChangingdefaultvaluesinSpecificShiftNUMworkertoJob(String date,String shiftype,String jobname,int numworker,Week week)throws IllegalArgumentException{
        if(jobname==null||shiftype==null||date==null||numworker>=0){
            throw new IllegalArgumentException("Argumets can not be NULL");
        }
        //-------date------//
        LocalDate dateToCheck= LocalDate.parse(date);
        if(!((dateToCheck.isEqual(week.getStart_date()) || dateToCheck.isAfter(week.getStart_date())) && (dateToCheck.isEqual(week.getEnd_date()) || dateToCheck.isBefore(week.getEnd_date())))){
            throw new IllegalArgumentException("Date must be in week of work");
        }
        //-------EnumShiftType-------//
        shiftype=shiftype.toUpperCase();
        if (!shiftype.equals("MORNING") && !shiftype.equals("EVENING")) {
            throw new IllegalArgumentException("Job type must be morning or evening");
        }
        //-------Job-------//
        int i=1;
        if(shiftype.equals("MORNING")){
            i=0;
        }
        Shift shift=week.getDayOfWeek(dateToCheck).getShiftsInDay()[i];
        Job job = null;
        for (Job j : this.Employeejobs_temp_database) {
            if (j.getJobName() == jobname) {
                job = j;
                break;
            }
        }
        if (job == null) {
            throw new IllegalArgumentException("Job does not exist");
        }
        shift.ChangingTheNumberOfemployeesPerJobInShift(job,numworker);
        return "The number of worker for "+jobname+" is change to "+numworker;

    }


}
