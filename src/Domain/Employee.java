package Domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private static int EmployeeNUM=1;
    private String Name;
    private String ID;
    private String Bank_account;
    private Branch Branch;
    private int employeeNum;
    private TermsOfEmployment terms;
    private List<Job> Jobs;
    private MyMap<LocalDate, Constraint> constraintMyMap;


    //constructor- gets all the data for employee
    public Employee(String name, String ID, String bank_account, Branch branch, TermsOfEmployment terms,Job job) {
        Name = name;
        this.ID = ID;
        Bank_account = bank_account;
        Branch = branch;
        this.terms = terms;
        Jobs = new ArrayList<Job>();
        Jobs.add(job);
        this.employeeNum =EmployeeNUM;
        EmployeeNUM+=1;
        constraintMyMap=null;
    }

    //constructor- gets all the data and terms for employee
    public Employee(String name, String ID, String bank_account, Branch branch, double vacationDay, LocalDate start_date, double salary, String job_type, String Salary_type, Job job) {
        Name = name;
        this.ID = ID;
        Bank_account = bank_account;
        Branch = branch;
        this.terms = new TermsOfEmployment(vacationDay,start_date,salary,job_type,Salary_type);
        Jobs = new ArrayList<Job>();
        Jobs.add(job);
        this.employeeNum =EmployeeNUM;
        EmployeeNUM+=1;
        constraintMyMap=null;
    }

    public String getName() {
        return Name;
    }

    public boolean setName(String name) {
        if (name==null||name.contains("[0-9]+")) {
            return false;
        }
        this.Name = name;
        return true;
    }

    public String getID() {
        return ID;
    }

    public boolean setID(String ID) {
        if (ID==null||ID.contains("[a-z,A-Z]+")||ID.length()!=6) {
            return false;
        }
        this.ID = ID;
        return true;
    }

    public String getBank_account() {
        return Bank_account;
    }

    public boolean setBank_account(String bank_account) {
        if (bank_account==null||bank_account.contains("[a-z,A-Z]+")||bank_account.length()!=8) {
            return false;
        }
        Bank_account = bank_account;
        return true;
    }

    public Branch getBranch() {
        return Branch;
    }

    public boolean setBranch(Branch branch) {
        if (branch==null){
            return false;
        }
        Branch = branch;
        return true;
    }


    public TermsOfEmployment getTerms() {
        return terms;
    }

    public boolean setTerms(TermsOfEmployment terms) {
        if(terms==null){
            return false;
        }
        this.terms = terms;
        return true;
    }

    public List<Job> getJobs() {
        return Jobs;
    }

    public boolean setJobs(List<Job> jobs) {
        if(jobs==null||jobs.isEmpty()){
            return false;
        }
        Jobs = jobs;
        return true;
    }

    public boolean addJob(Job job) {
        if(job==null){
            return false;
        }
        return Jobs.add(job);
    }

    public boolean employeeCanbe(Job job){
        return this.Jobs.contains(job);
    }

    public int getEmployeeNum() {
        return employeeNum;
    }

    public MyMap<LocalDate, Constraint> getConstraintMyMap() {
        return constraintMyMap;
    }

    public void setConstraintMyMap(MyMap<LocalDate, Constraint> constraintMyMap) {
        this.constraintMyMap = constraintMyMap;
    }

    public Constraint getConstraintByDate(LocalDate date) {
        if (this.constraintMyMap==null){
            return null;
        }
        if(!constraintMyMap.containsKey(date)){
            return null;
        }
        return constraintMyMap.get(date);
    }


    @Override
    public String toString() {
        return "Name:" + Name +" "+
                "employeeNum:" + employeeNum;
    }

    public String toStringfullinfo() {
        return "Name: " + Name + "ID: " + ID + '\n' +
                "employeeNum=: " + employeeNum +
                " Bank_account: " + Bank_account + '\n' +
                "Branch: " + Branch +
                "Jobs: " + Jobs+ "\n"+
                "terms" + terms ;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o instanceof Employee) {
            return false;
        }
        Employee othre = (Employee) o;
        return this.getID()==othre.getID();
    }
}
