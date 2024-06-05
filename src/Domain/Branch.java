package Domain;

import java.util.ArrayList;
import java.util.List;

public class Branch {
    private static int numBranch=0;
    private String name;
    private String address;
    private ManagerEmployee managerEmployee;
    //האם צריך לתמוך בסניף שנסגר ואיך ןמה לעשות עם עובדי הבניף האם למנהל כוח אדם בסניף אחר יש הרשאה לסניפים אחרים אם לא האם זה אומר שעובד לא יעבור בן סניפים
    private MyMap<String,Employee> EmployeesInBranch;
    private int branchNum;



    public Branch(String name, String address, ManagerEmployee managerEmployee) {
        this.name = name;
        this.address = address;
        this.managerEmployee = managerEmployee;
        this.branchNum=numBranch+1;
        this.EmployeesInBranch=new MyMap<String,Employee>();
        numBranch=+1;
    }

    public Branch(String name, String address) {
        this.name = name;
        this.address = address;
        this.managerEmployee = null;
        this.branchNum=numBranch+1;
        this.EmployeesInBranch=new MyMap<String,Employee>();
        numBranch=+1;
    }

    public String getBranchName() {
        return name;
    }

    public void setBranchName(String name) {
        this.name = name;
    }

    public String getBranchAddress() {
        return address;
    }

    public void setBranchAddress(String address) {
        this.address = address;
    }

    public ManagerEmployee getManagerEmployee() {
        return managerEmployee;
    }

    public MyMap<String,Employee> getEmployeesInBranch() {
        return EmployeesInBranch;
    }

    public void setEmployeesInBranch(MyMap<String,Employee> employeesInBranch) {
        EmployeesInBranch = employeesInBranch;
    }

    public void setManagerEmployee(ManagerEmployee managerEmployee) {
        this.managerEmployee = managerEmployee;
    }

    public int getBranchNum() {
        return branchNum;
    }

    public void addEmployeeToBranch(Employee employee){
        this.EmployeesInBranch.put(employee.getID(),employee);
    }

    public Employee getEmployee(String ID){
        return this.EmployeesInBranch.get(ID);
    }

    public void removEmployeeFromBranch(Employee employee){
        this.EmployeesInBranch.remove(employee.getID());
    }
    @Override
    public String toString() {
        return "Branch name: " + name + '\n' +
                "address: " + address + '\n' +
                "branch Num: " + branchNum +'\n' +
                "managerEmployee: " + managerEmployee ;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o instanceof Branch) {
            return false;
        }
        Branch othre = (Branch) o;
        return this.getBranchNum()==othre.getBranchNum();
    }
}
