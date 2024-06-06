package Domain;

import Domain.Enums.ShiftType;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Shift {

    private MyMap<Employee,Job> employee_in_shift;
    private ShiftType shiftType;
    public static MyMap<Job,Integer> NumberofWorkersPerPositionDifult =new MyMap<Job,Integer>();//מה שצריך להיות משותף
    public MyMap<Job,Integer> NumberofWorkersPerJob;//מה שצריך להיות למשמרת
    private Time start_time;
    private Time end_time;
    private LocalDate date;

    public Shift() {
        this.start_time =null;
        this.end_time=null;
        this.date=null;
        this.employee_in_shift=new MyMap<Employee,Job>();
        this.NumberofWorkersPerJob=null;
    }
    public Shift(Time start_time,Time end_time,LocalDate date,ShiftType shiftType){
        this.date=date;
        this.start_time=start_time;
        this.end_time=end_time;
        this.employee_in_shift=new MyMap<Employee,Job>();
        this.NumberofWorkersPerJob=null;
        this.shiftType=shiftType;

    }
    public static MyMap<Job, Integer> getNumberofWorkersPerPositionDifult() {
        return NumberofWorkersPerPositionDifult;
    }
    public static boolean ChangingtheDifultNumberOfemployeesPerJob(Job job,int quantity) {
        if (job == null || quantity < 0) {
            return false;
        }
        NumberofWorkersPerPositionDifult.put(job,quantity);
        return true;
    }
    public void ChangingTheNumberOfemployeesPerJobInShift(Job job,int quantity){
        if(this.NumberofWorkersPerJob==null){
            this.NumberofWorkersPerJob =new MyMap<Job,Integer>();
            for (Job j:NumberofWorkersPerPositionDifult.getKeys()){
                this.NumberofWorkersPerJob.put(j, NumberofWorkersPerPositionDifult.get(j));
            }
        }
        if (quantity==0){
            this.NumberofWorkersPerJob.remove(job);
            if(!getallEmployeePerJob(job).isEmpty()){
                for (Employee e:getallEmployeePerJob(job)){
                    removeEmployeeFromShift(e);
                }
            }
        }
        else {
            if(getallEmployeePerJob(job).size()>quantity){
                while(getallEmployeePerJob(job).size()>quantity){
                    Employee e=getallEmployeePerJob(job).get(0);
                    removeEmployeeFromShift(e);
                }
            }
            this.NumberofWorkersPerJob.put(job, quantity);
        }
    }
    public boolean addJobToShift(Job job){
        if(job==null){
            return false;
        }
        NumberofWorkersPerPositionDifult.put(job,1);
        return true;
    }
    public List<Employee> getallEmployeePerJob(Job job){
        Set<Employee> employeeSet = this.getEmployeeinshiftSet();
        List<Employee> employeeList = new ArrayList<Employee>();
        for (Employee emp :employeeSet){
            if(emp.getJobs().contains(job)){
                employeeList.add(emp);
            }
        }
        return employeeList;
    }
////לבדוק
    public boolean isJobInShiftisFull(Job job) {
        List<Employee> employeeList = this.getallEmployeePerJob(job);
        if(this.NumberofWorkersPerJob==null){
            if (employeeList != null && NumberofWorkersPerPositionDifult.containsKey(job)) {
                return  (employeeList.size() == NumberofWorkersPerPositionDifult.get(job));
            }
            return false;

        }
        return (this.NumberofWorkersPerJob.get(job)==employeeList.size());
    }
    public ShiftType getShiftType() {
        return shiftType;
    }
    public Time getStart_time() {
        return start_time;
    }
    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }
    public Time getEnd_time() {
        return end_time;
    }
    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public MyMap<Employee,Job> getEmployeeinshiftMap() {
        return employee_in_shift;
    }
    public Set<Employee> getEmployeeinshiftSet() {
        Set<Employee> employees;
        if(this.employee_in_shift!=null){
            employees=this.employee_in_shift.getKeys();
            return employees;

        }
        return null;
    }
    public void setEmployee_in_shift(MyMap<Employee,Job> employee_in_shift) {
        this.employee_in_shift = employee_in_shift;
    }
    public void addEmployeeToShift(Employee employee, Job job) {
        employee_in_shift.put(employee,job);
    }
    public void removeEmployeeFromShift(Employee employee) {
        if (employee_in_shift.containsKey(employee)) {
            employee_in_shift.remove(employee);
        }
    }
    public Set<Job> getAllJobInShift(){
        if(this.NumberofWorkersPerJob==null){
            return NumberofWorkersPerPositionDifult.getKeys();
        }
        return this.NumberofWorkersPerJob.getKeys();
    }

    public int getNumberofWorkersPerJob(Job job){
        if(this.NumberofWorkersPerJob==null){
            return NumberofWorkersPerPositionDifult.get(job);
        }
        return this.NumberofWorkersPerJob.get(job);
    }

    @Override
    public String toString() {
        return   start_time + " - " + end_time+"\n"+this.employee_in_shift+"\n";
    }
}
