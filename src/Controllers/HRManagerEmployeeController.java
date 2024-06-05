package Controllers;
import Data.*;
import Domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class HRManagerEmployeeController {

    private List<Job> Employeejobs_temp_database;
    private MyMap<String,Branch> Branch_temp_database;
    private MyMap<String, Employee> Employees_temp_database;

    public HRManagerEmployeeController(List<Job> Employeejobs_temp_database,MyMap<String,Branch> Branch_temp_database,MyMap<String, Employee> Employees_temp_database) {
        this.Employeejobs_temp_database=Employeejobs_temp_database;
        this.Branch_temp_database=Branch_temp_database;
        this.Employees_temp_database=Employees_temp_database;

    }

    public String createEmployee(String name, String ID, String bank_account, String branch, double vacationDay, String start_date, double salary, String job_type, String salary_type,String jobname){
        //NULL
        if (name == null || bank_account == null || ID == null || jobname == null || salary < 30 || vacationDay < 14 || branch == null || salary_type == null || job_type == null || start_date == null)
            throw new IllegalArgumentException("Argument's can't be NULL");
        //-------Name-------//
        if(name.contains("[0-9]+")){
            throw new IllegalArgumentException("Name contain only alphabetic characters");
        }
        //-------ID-------//
        if (ID.length() != 6 || ID.contains("[0-9]+"))
            throw new IllegalArgumentException("ID must be 6 numeric characters long");
            if(this.Employees_temp_database.get(ID) != null) {
                throw new IllegalArgumentException("Employee is already exist");
        }
        //-------Bank-------//
        if (bank_account.length() != 8 || bank_account.contains("[a-z,A-Z]+")) {
            throw new IllegalArgumentException("ID must be 8 numeric characters long");
        }
        //-------job-------//
        Job job_to_emp = null;
        for (Job j : this.Employeejobs_temp_database) {
            if (j.getJobName() == jobname) {
                job_to_emp = j;
                break;
            }
        }
        if (job_to_emp == null) {
            throw new IllegalArgumentException("Job does not exist");
        }
        //-------Branch-------//
        Branch branch_to_emp = null;
        List<Branch> Allbranch=getAllBranch();
        for (Branch b : Allbranch ) {
            if (b.getBranchName() == branch) {
                branch_to_emp = b;
                break;
            }
        }
        if (branch_to_emp == null) {
            throw new IllegalArgumentException("Branch does not exist");
        }
        //-------StartDay-------//"2018-05-05"
        LocalDate date = LocalDate.parse(start_date);
        LocalDate now = LocalDate.now();
        LocalDate twoWeeksLater = now.plusWeeks(2);
        if (!date.isBefore(now) && !date.isAfter(twoWeeksLater)) {
            throw new IllegalArgumentException("Date of strat cannot be before today or after two weeks from today");
        }
        //-------Enums-------//
        job_type=job_type.toUpperCase();
        salary_type=salary_type.toUpperCase();

        if (!job_type.equals("FULL") && !job_type.equals("PART")) {
            throw new IllegalArgumentException("Job type must be full or part");
        }
        if (!salary_type.equals("GLOBAL") && !salary_type.equals("HOURLY")) {
            throw new IllegalArgumentException("Salary type must be global or hourly");
        }
        //-------------------create------------------//
        TermsOfEmployment terms = new TermsOfEmployment(vacationDay, date, salary, job_type, salary_type);
        Employee NEWemployee = new Employee(name, ID, bank_account, branch_to_emp, terms, job_to_emp);
        this.Employees_temp_database.put(ID, NEWemployee);
        branch_to_emp.addEmployeeToBranch(NEWemployee);
        return "Employee successfully created";
    }
    public String createManagmentEmployee(String name, String ID, String bank_account, String branch, double vacationDay, String start_date, double salary, String job_type, String salary_type,String jobname){
        //NULL
        if (name == null || bank_account == null || ID == null || jobname == null || salary < 30 || vacationDay < 14 || branch == null || salary_type == null || job_type == null || start_date == null)
            throw new IllegalArgumentException("Argument's can't be NULL");
        //-------Name-------//
        if(name.contains("[0-9]+")){
            throw new IllegalArgumentException("Name contain only alphabetic characters");
        }
        //-------ID-------//
        if (ID.length() != 6 || ID.contains("[a-z,A-Z]+")) {
            throw new IllegalArgumentException("ID must be 6 numeric characters long");
        }
        if(this.Employees_temp_database.get(ID) != null) {
            throw new IllegalArgumentException("Employee is already exist");
        }
        //-------Bank-------//
        if (bank_account.length() != 8 || bank_account.contains("[a-z,A-Z]+")) {
            throw new IllegalArgumentException("ID must be 8 numeric characters long");
        }
        //-------job-------//
        Job job_to_emp = null;
        for (Job j : this.Employeejobs_temp_database) {
            if (j.getJobName() == jobname&&j instanceof ManagementJob) {
                job_to_emp = j;
                break;
            }
        }
        if (job_to_emp == null) {
            throw new IllegalArgumentException("Job does not exist");
        }
        //-------Branch-------//
        Branch branch_to_emp = null;
        List<Branch> Allbranch=getAllBranch();
        for (Branch b : Allbranch ) {
            if (b.getBranchName() == branch) {
                branch_to_emp = b;
                break;
            }
        }
        if (branch_to_emp == null) {
            throw new IllegalArgumentException("Branch does not exist");
        }
        //-------StartDay-------//"2018-05-05"
        LocalDate date = LocalDate.parse(start_date);
        LocalDate now = LocalDate.now();
        LocalDate twoWeeksLater = now.plusWeeks(2);
        if (!date.isBefore(now) && !date.isAfter(twoWeeksLater)) {
            throw new IllegalArgumentException("Date of strat cannot be before today or after two weeks from today");
        }
        //-------Enums-------//
        job_type=job_type.toUpperCase();
        salary_type=salary_type.toUpperCase();

        if (!job_type.equals("FULL") && !job_type.equals("PART")) {
            throw new IllegalArgumentException("Job type must be full or part");
        }
        if (!salary_type.equals("GLOBAL") && !salary_type.equals("HOURLY")) {
            throw new IllegalArgumentException("Salary type must be global or hourly");
        }
        //-------------------create------------------//
        TermsOfEmployment terms=new TermsOfEmployment(vacationDay,date,salary,job_type,salary_type);
        ManagerEmployee NEWemployee=new ManagerEmployee(name,ID,bank_account,branch_to_emp,terms,(ManagementJob) job_to_emp);
        this.Employees_temp_database.put(ID,NEWemployee);
        return "Employee successfully created";

    }

    // UPDATE all String name, String bank_account, Branch branch, double vacationDay, LocalDate start_date, double salary, String job_type, String Salary_type,Job job) for all employees
    public String updateEmployeeNAME(String ID,String name)throws IllegalArgumentException {
        if(ID==null||name==null){
            throw new IllegalArgumentException("Arguments can not be NULL");
        }
        if(name.contains("[0-9]+")){
            throw new IllegalArgumentException("Name contain only alphabetic characters");
        }
        if (ID.length() != 6 || ID.contains("[a-z,A-Z]+")) {
            throw new IllegalArgumentException("ID must be 6 numeric characters long");
        }
        Employee empToUpdate = this.Employees_temp_database.get(ID);
        if(empToUpdate!=null){
            empToUpdate.setName(name);
            return "The employee name has been successfully changed";
        }
        throw new IllegalArgumentException("Employee is NOT exist");
    }
    public String updateEmployeeENDINGDATE(String ID,String end_date)throws IllegalArgumentException {
        if(ID==null|end_date==null){
            throw new IllegalArgumentException("Arguments can not be NULL");
        }
        Employee empToUpdate = this.Employees_temp_database.get(ID);
        if(empToUpdate!=null){
            LocalDate end_date_toUP = LocalDate.parse(end_date);
            LocalDate start_date=empToUpdate.getTerms().getStart_date();
            if(end_date_toUP.isAfter(start_date)){
                if (empToUpdate.getTerms().getEnd_date()==null){
                    empToUpdate.getTerms().setEnd_date(end_date_toUP);
                    Branch branch=empToUpdate.getBranch();
                    branch.removEmployeeFromBranch(empToUpdate);
                    return "departure date has been successfully updated for this employee - "+ID;
                }
                throw new IllegalArgumentException("The departure date has been updated for this employee");
            }
            throw new IllegalArgumentException("End time can't be befor start time");
        }
        throw new IllegalArgumentException("Employee is NOT exist");
    }
    public String updateEmployeeSALERY(String ID,double salery)throws IllegalArgumentException {
        if(ID==null) {
            throw new IllegalArgumentException("Arguments can not be NULL");
        }
        if(salery<30) {
            throw new IllegalArgumentException("Minimum salary is 30");
        }
        Employee empToUpdate = this.Employees_temp_database.get(ID);
        if(empToUpdate!=null){
            empToUpdate.getTerms().setSalary(salery);
            return "Salary is successfully updated for employee - "+ID;
        }
        throw new IllegalArgumentException("Employee is NOT exist");
    }
    public String updateORaddEmployeeJob(String ID,String job)throws IllegalArgumentException {
        if (ID == null | job == null) {
            throw new IllegalArgumentException("Arguments can not be NULL");
        }
        Employee empToUpdate = getEmployeeByID(ID);
        Job jobToUpdate = getJobByName(job);
        if (empToUpdate == null ){
            throw new IllegalArgumentException("Employee is NOT exist");
        }
        if(jobToUpdate == null) {
            throw new IllegalArgumentException("Job is NOT exist");
        }
        if(jobToUpdate instanceof ManagementJob&&empToUpdate instanceof ManagerEmployee){/////////
            throw new IllegalArgumentException("ManagerEmployee can only have one ManagementJob");
        }
        if (!(jobToUpdate instanceof ManagementJob)&&!(empToUpdate instanceof ManagerEmployee)) {
            empToUpdate.addJob(jobToUpdate);
            return "Job is successfully add to employee";
        }
        throw new IllegalArgumentException("The job does not match the employee's grade") ;
    }
    public String updateEmployeeBANK(String ID,String bank_accuont) {
        if(ID==null||bank_accuont==null){
            throw new IllegalArgumentException("Arguments can not be NULL");
        }
        if(bank_accuont.length()!=8||bank_accuont.contains("[a-z,A-Z]+")){
            throw new IllegalArgumentException("ID must be 8 numeric characters long");
        }
        Employee empToUpdate=getEmployeeByID(ID);
        if (empToUpdate==null){
            throw new IllegalArgumentException("Employee is NOT exist");
        }
        empToUpdate.setBank_account(bank_accuont);
        return "Bank account is successfully updated for employee - "+ID;

    }
    public String updateEmployeeBranch(String ID,int branchnum){
        if(ID==null||branchnum<=0){
            throw new IllegalArgumentException("Arguments can not be NULL");
        }
        Employee empToUpdate=getEmployeeByID(ID);
        if (empToUpdate==null){
            throw new IllegalArgumentException("Employee is NOT exist");
        }
        if(empToUpdate.getBranch().getBranchNum()==branchnum){
            throw new IllegalArgumentException("Employee is already associated with this branch");
        }
        List<Branch> allBranch=getAllBranch();
        Branch branchToUpdate=null;
        for(Branch b:allBranch){
            if(b.getBranchNum()==branchnum){
                branchToUpdate=b;
                break;
            }
        }
        if(branchToUpdate==null){
            throw new IllegalArgumentException("Branch is NOT exist");
        }
        Branch oldbranch=empToUpdate.getBranch();
        empToUpdate.setBranch(branchToUpdate);
        oldbranch.removEmployeeFromBranch(empToUpdate);
        return "An employee was transferred to another branch successfully";
    }
    public boolean updateEmployeeTermsSalary(String ID,double salary, String job_type, String Salary_type){
        if(ID==null||salary<=30||job_type==null||Salary_type==null||(job_type.toUpperCase()!="PART"&&job_type.toUpperCase()!="FULL")||(Salary_type.toUpperCase()!="GLOBAL"&&Salary_type.toUpperCase()!="HOURLY")){
            return false;
        }
        Employee empToUpdate=getEmployeeByID(ID);
        if (empToUpdate==null){
            return false;
        }
        TermsOfEmployment term= empToUpdate.getTerms();
        term.setSalary(salary);
        term.setJt(job_type);
        term.setSt(Salary_type);
        return true;
    }

    //UPDATE Employee TO ManagmantEmployee
    public boolean UPDATEemployeeToManagmantEmployee(String ID,String job){
        if(ID==null|job==null){
            return false;
        }
        Employee empToUpdate =getEmployeeByID(ID);
        Job jobToUpdat=getJobByName(job);
        if (empToUpdate!=null&&jobToUpdat!=null){
            if(empToUpdate instanceof ManagerEmployee||!(jobToUpdat instanceof ManagementJob)){
                return false;
            }
            this.Employees_temp_database.remove(ID);
            ManagerEmployee empToUpdateASmaneger =new ManagerEmployee(empToUpdate.getName(),ID,empToUpdate.getBank_account(),empToUpdate.getBranch(),empToUpdate.getTerms(),(ManagementJob) jobToUpdat);
            if (empToUpdateASmaneger==null){
                return false;
            }
            this.Employees_temp_database.put(ID,(Employee)empToUpdateASmaneger);
        }
        return false;
    }

    public Employee getEmployeeByID(String ID) {
        if (this.Employees_temp_database.containsKey(ID)) {
            Employee emp = this.Employees_temp_database.get(ID);
            return emp;
        }
        return null;
    }

    public Job getJobByName(String Jobname){
        if (Jobname==null){
            return null;
        }
        Job job;
        for(Job j : this.Employeejobs_temp_database){
            if(j.getJobName()==Jobname){
                job=j;
                return job;
            }
        }
        return null;
    }

    public List<Branch> getAllBranch() {
        Set<String>Brenchskey=this.Branch_temp_database.getKeys();
        List<Branch> Allbranch=new ArrayList<Branch>();
        for(String b:Brenchskey){
            Allbranch.add(this.Branch_temp_database.get(b));
        }
        return Allbranch;
    }


    public String createJob(String name)throws IllegalArgumentException{
        if(name==null){
            throw new IllegalArgumentException("Name can not be NULL");
        }
        for (Job j: this.Employeejobs_temp_database){
            if(j.getJobName()==name){
                throw new IllegalArgumentException("The job already exists");
            }
        }
        Job newJob=new Job(name);
        this.Employeejobs_temp_database.add(newJob);
        return "The job was created successfully";
    }
    public String createManagementJob(String name){
        if(name==null){
            throw new IllegalArgumentException("Name can not be NULL");
        }
        for (Job j: this.Employeejobs_temp_database){
            if(j.getJobName()==name){
                throw new IllegalArgumentException("The job already exists");
            }
        }
        Job newJob=(ManagementJob)new ManagementJob(name);
        this.Employeejobs_temp_database.add(newJob);
        return "The job was created successfully";
    }

    public String createBranch(String name, String address, String ManagerID) throws IllegalArgumentException {
        if (name == null||address == null || ManagerID == null){
            throw new IllegalArgumentException("Argument's can't be NULL");
        }
        Branch branch=Branch_temp_database.get(address);
        if(branch!=null){
            throw new IllegalArgumentException("There is already a branch at this address");
        }
        Employee employee=this.Employees_temp_database.get(ManagerID);
        if(employee==null){
            throw new IllegalArgumentException("This employee does not exist in the system");
        }
        if (employee instanceof ManagerEmployee){
            Branch newbranch = new Branch(name,address,(ManagerEmployee) employee);
            this.Branch_temp_database.put(address,newbranch);
            return "Branch successfully created";
        }
        throw new IllegalArgumentException("Branch must be managed by a managerial employee");

    }
    public String createBranch(String name, String address) throws IllegalArgumentException {
        if (name == null||address == null){
            throw new IllegalArgumentException("Argument's can't be NULL");
        }
        Branch branch=this.Branch_temp_database.get(address);
        if(branch!=null){
            throw new IllegalArgumentException("There is already a branch at this address");
        }
        Branch newbranch = new Branch(name, address);
        this.Branch_temp_database.put(address,newbranch);
        return "Branch successfully created";
    }


}
