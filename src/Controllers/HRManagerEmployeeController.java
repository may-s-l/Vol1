package Controllers;
import Data.*;
import Domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class HRManagerEmployeeController {

    private Temp_DataBase Temp_Database;
    public HRManagerEmployeeController() {
        this.Temp_Database=new Temp_DataBase();
    }


    public Branch createBranch(String name, String address, ManagerEmployee memp){
        if(name==null||address==null||memp==null){
            return null;
        }
        for (Branch b: this.Temp_Database.getBranch_temp_database()){
            if(b.getBranchName()==name||b.getBranchAddress()==address){
                return null;
            }
        }
        Branch newbranch=new Branch(name,address,memp);
        this.Temp_Database.getBranch_temp_database().add(newbranch);
        return newbranch;
    }
    public Branch createBranch(String name,String address){
        if(name==null||address==null){
            return null;
        }
        for (Branch b: this.Temp_Database.getBranch_temp_database()){
            if(b.getBranchName()==name||b.getBranchAddress()==address){
                return null;
            }
        }
        Branch newbranch=new Branch(name,address);
        this.Temp_Database.getBranch_temp_database().add(newbranch);
        return newbranch;
    }
    public Job createJob(String name){
        if(name==null){
            return null;
        }
        for (Job j: this.Temp_Database.getEmployeejobs_temp_database()){
            if(j.getJobName()==name){
                return null;
            }
        }
        Job newJob=new Job(name);
        this.Temp_Database.getEmployeejobs_temp_database().add(newJob);
        return newJob;
    }
    public Job createManagementJob(String name){
        if(name==null){
            return null;
        }
        for (Job j: this.Temp_Database.getEmployeejobs_temp_database()){
            if(j.getJobName()==name){
                return null;
            }
        }
        Job newJob=(ManagementJob)new ManagementJob(name);
        this.Temp_Database.getEmployeejobs_temp_database().add(newJob);
        return newJob;
    }
    public Employee createEmployee(String name, String ID, String bank_account, String branch, double vacationDay, String start_date, double salary, String job_type, String salary_type,String jobname){
        //NULL
        if(name==null||name.contains("[0-9]+")||bank_account==null||ID==null||jobname==null||salary<30|| vacationDay<14||branch==null||salary_type==null||job_type==null||start_date==null)
            return null;
        //-------ID-------//
        if(ID.length()!=6||getEmployeeByID(ID)!=null){
            return null;
        }
        //-------Bank-------//
        if(bank_account.length()!=8||bank_account.contains("[a-z,A-Z]+")){
            return null;
        }
        //-------job-------//
        Job job_to_emp=null;
        for(Job j :this.Temp_Database.getEmployeejobs_temp_database()){
            if(j.getJobName()==jobname){
                job_to_emp=j;
                break;
            }
        }
        if(job_to_emp==null){
            return null;
        }
        //-------Branch-------//
        Branch branch_to_emp=null;
        for(Branch b :this.Temp_Database.getBranch_temp_database()){
            if(b.getBranchName()==branch){
                branch_to_emp=b;
                break;
            }
        }
        if(branch_to_emp==null){
            return null;
        }
        //-------StartDay-------//"2018-05-05"
        LocalDate date = LocalDate.parse(start_date);
        if(LocalDate.now().getMonth()!=date.getMonth()){
            return null;
        }
        //-------Enums-------//
        if(job_type.toUpperCase(Locale.ROOT)!="FULL"&&job_type.toUpperCase(Locale.ROOT)!="PART"){
            return null;
        }
        if (salary_type.toUpperCase()!="GLOBAL"&&salary_type.toUpperCase()!="HOURLY"){
            return null;
        }
        //-------------------create------------------//
        TermsOfEmployment terms=new TermsOfEmployment(vacationDay,date,salary,job_type,salary_type);
        Employee NEWemployee=new Employee(name,ID,bank_account,branch_to_emp,terms,job_to_emp);
        this.Temp_Database.getEmployees_temp_database().put(ID,NEWemployee);
        return NEWemployee;
    }
    public ManagerEmployee createManagmentEmployee(String name, String ID, String bank_account, String branch, double vacationDay, String start_date, double salary, String job_type, String salary_type,String jobname){
        //NULL
        if(name==null||name.contains("[0-9]+")||bank_account==null||ID==null||jobname==null||salary<30|| vacationDay<14||branch==null||salary_type==null||jobname==null)
            return null;
        //-------ID-------//
        if(ID.length()!=6||getEmployeeByID(ID)!=null){
            return null;
        }
        //-------Bank-------//
        if(bank_account.length()!=8||bank_account.contains("[a-z,A-Z]+")){
            return null;
        }
        //-------job-------//
        Job job_to_emp=null;
        for(Job j :this.Temp_Database.getEmployeejobs_temp_database()){
            if(j.getJobName()==jobname){
                if(j instanceof ManagementJob) {
                    job_to_emp = j;
                    break;
                }
            }
        }
        if(job_to_emp==null){
            return null;
        }
        //-------Branch-------//
        Branch branch_to_emp=null;
        for(Branch b :this.Temp_Database.getBranch_temp_database()){
            if(b.getBranchName()==branch){
                branch_to_emp=b;
                break;
            }
        }
        if(branch_to_emp==null){
            return null;
        }
        //-------StartDay-------//"2018-05-05"
        LocalDate date = LocalDate.parse(start_date);
        if(LocalDate.now().getMonth()!=date.getMonth()){
            return null;
        }
        //-------Enums-------//
        if(job_type.toUpperCase(Locale.ROOT)!="FULL"&&job_type.toUpperCase(Locale.ROOT)!="PART"){
            return null;
        }
        if (salary_type.toUpperCase()!="GLOBAL"&&salary_type.toUpperCase()!="HOURLY"){
            return null;
        }
        TermsOfEmployment terms=new TermsOfEmployment(vacationDay,date,salary,job_type,salary_type);
        ManagerEmployee NEWemployee=new ManagerEmployee(name,ID,bank_account,branch_to_emp,terms,(ManagementJob) job_to_emp);
        this.Temp_Database.getEmployees_temp_database().put(ID,NEWemployee);
        return NEWemployee;

    }

    // UPDATE all String name, String bank_account, Branch branch, double vacationDay, LocalDate start_date, double salary, String job_type, String Salary_type,Job job) for all employees
    public boolean updateEmployeeNAME(String ID,String name) {
        if(ID==null|name==null|name.contains("[0-9]+")){
            return false;
        }
        Employee empToUpdate = getEmployeeByID(ID);
        if(empToUpdate!=null){
            empToUpdate.setName(name);
            return true;
        }

        return false;
    }
    public boolean updateEmployeeENDINGDATE(String ID,String end_date) {
        if(ID==null|end_date==null){
            return false;
        }
        Employee empToUpdate = getEmployeeByID(ID);
        if(empToUpdate!=null){
            LocalDate end_date_toUP = LocalDate.parse(end_date);
            LocalDate start_date=empToUpdate.getTerms().getStart_date();
            if(end_date_toUP.isAfter(start_date)){
                if (empToUpdate.getTerms().getEnd_date()==null){
                    empToUpdate.getTerms().setEnd_date(end_date_toUP);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
    public boolean updateEmployeeSALERY(String ID,double salery) {
        if(ID==null|salery<=0){
            return false;
        }
        Employee empToUpdate = getEmployeeByID(ID);
        if(empToUpdate!=null){
                empToUpdate.getTerms().setSalary(salery);
                return true;
        }
        return false;
    }
    public boolean updateORaddEmployeeJob(String ID,String job) {
        if (ID == null | job == null) {
            return false;
        }
        Employee empToUpdate = getEmployeeByID(ID);
        Job jobToUpdate = getJobByName(job);
        if (empToUpdate == null || jobToUpdate == null) {
            return false;
        }
        if(jobToUpdate instanceof ManagementJob&&empToUpdate instanceof ManagerEmployee){
            return empToUpdate.addJob(jobToUpdate);
        }
        if (!(jobToUpdate instanceof ManagementJob)&&!(empToUpdate instanceof ManagerEmployee)) {
            return empToUpdate.addJob(jobToUpdate);
        }
        return false;
    }
    public boolean updateEmployeeBANK(String ID,String bank_accuont) {
        if(ID==null||bank_accuont==null||bank_accuont.length()!=8||bank_accuont.contains("[a-z,A-Z]+")){
            return false;
        }
        Employee empToUpdate=getEmployeeByID(ID);
        if (empToUpdate==null){
            return false;
        }
        return empToUpdate.setBank_account(bank_accuont);
    }
    public boolean updateEmployeeBranch(String ID,int branchnum){
        if(ID==null||branchnum<=0){
            return false;
        }
        Employee empToUpdate=getEmployeeByID(ID);
        Branch branchToUpdate=getBranchByNUM(branchnum);
        if (empToUpdate==null||branchToUpdate==null){
            return false;
        }
        return empToUpdate.setBranch(branchToUpdate);
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
            this.Temp_Database.getEmployees_temp_database().remove(ID);
            ManagerEmployee empToUpdateASmaneger =new ManagerEmployee(empToUpdate.getName(),ID,empToUpdate.getBank_account(),empToUpdate.getBranch(),empToUpdate.getTerms(),(ManagementJob) jobToUpdat);
            if (empToUpdateASmaneger==null){
                return false;
            }
            return this.addEmployee(empToUpdateASmaneger);
        }
        return false;
    }
    public Boolean addEmployee(Employee employee) {
        Employee empToUpdate = getEmployeeByID(employee.getID());
        if(empToUpdate!=null){
            return false;
        }
        this.Temp_Database.getEmployees_temp_database().put(employee.getID(),employee);
        return true;
    }
    public Employee getEmployeeByID(String ID) {
        if (this.Temp_Database.getEmployees_temp_database().containsKey(ID)) {
            Employee emp = this.Temp_Database.getEmployees_temp_database().get(ID);
            return emp;
        }
        return null;
    }

    public Temp_DataBase getTemp_Database() {
        return Temp_Database;
    }

    public void setTemp_Database(Temp_DataBase temp_Database) {
        Temp_Database = temp_Database;
    }

    public Job getJobByName(String Jobname){
        if (Jobname==null){
            return null;
        }
        Job job;
        for(Job j : this.Temp_Database.getEmployeejobs_temp_database()){
            if(j.getJobName()==Jobname){
                job=j;
                return job;
            }
        }
        return null;
    }
    public Branch getBranchByNUM(int branchnumber){
        if (branchnumber<=0){
            return null;
        }
        Branch branch;
        for(Branch b : this.Temp_Database.getBranch_temp_database()){
            if(b.getBranchNum()==branchnumber){
                branch=b;
                return branch;
            }
        }
        return null;
    }
    public List<Branch> getAllBranch() {

        return Temp_Database.getBranch_temp_database();
    }
    public MyMap<String, Employee> getAllEmployees() {

        return this.Temp_Database.getEmployees_temp_database();
    }
    public List<Job> getAlljobs() {
        return this.Temp_Database.getEmployeejobs_temp_database();
    }


}
