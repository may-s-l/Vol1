package Data;


import Domain.*;
import java.util.*;

public class Temp_DataBase {
    private List<Branch> Branch_temp_database;
    private List<Job> Employeejobs_temp_database;
    private MyMap<String,Employee> Employees_temp_database;
    private MyMap<Employee,List<Constraint>> Constraint_temp_database;


    public Temp_DataBase() {
        this.Employees_temp_database = new MyMap<String,Employee>();
        this.Employeejobs_temp_database=new ArrayList<Job>();
        this.Branch_temp_database = new ArrayList<Branch>();
        this.Constraint_temp_database = new MyMap<Employee, List<Constraint>>();

    }

    public List<Branch> getBranch_temp_database() {
        return Branch_temp_database;
    }

    public void setBranch_temp_database(List<Branch> branch_temp_database) {
        Branch_temp_database = branch_temp_database;
    }

    public List<Job> getEmployeejobs_temp_database() {
        return Employeejobs_temp_database;
    }

    public void setEmployeejobs_temp_database(List<Job> employeejobs_temp_database) {
        Employeejobs_temp_database = employeejobs_temp_database;
    }

    public MyMap<String,Employee> getEmployees_temp_database() {
        return Employees_temp_database;
    }

    public void setEmployees_temp_database(MyMap<String,Employee> employees_temp_database) {
        Employees_temp_database = employees_temp_database;
    }

    public MyMap<Employee, List<Constraint>> getConstraint_temp_database() {
        return Constraint_temp_database;
    }

    public void setConstraint_temp_database(MyMap<Employee, List<Constraint>> constraint_temp_database) {
        Constraint_temp_database = constraint_temp_database;
    }

    public List<Constraint> getEmployeeConstraint(Employee employee){
        return this.Constraint_temp_database.get(employee);
    }
///////////////////////////////////////////////

    public Job getJobByName(String Jobname){
        if (Jobname==null){
            return null;
        }
        Job job;
        for(Job j : getEmployeejobs_temp_database()){
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
        for(Branch b : getBranch_temp_database()){
            if(b.getBranchNum()==branchnumber){
                branch=b;
                return branch;
            }
        }
        return null;
    }
    public List<Branch> getAllBranch() {
        return getBranch_temp_database();
    }
    public MyMap<String, Employee> getAllEmployees() {

        return getEmployees_temp_database();
    }
    public List<Job> getAlljobs() {
        return getEmployeejobs_temp_database();
    }
}

