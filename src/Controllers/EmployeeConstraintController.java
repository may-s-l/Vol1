package Controllers;

import Data.*;
import Domain.*;
import Domain.Enums.*;

import java.time.LocalDate;
import java.util.List;

public class EmployeeConstraintController {
    private Temp_DataBase Temp_Database;
    //private ConstraintMenu constraintMenu;

    public void addConstrain(Employee employee, LocalDate date, ShiftType shiftType) {
        Constraint new_constrain=new Constraint(employee, date,shiftType);
        Temp_Database.getConstraint_temp_database().get(employee).add(new_constrain);
    }

    public void deleteConstrain(Employee employee, LocalDate date, ShiftType shiftType) {
        for (int i = 0; i < this.Temp_Database.getEmployeeConstraint(employee).size(); i++) {
            Constraint constraint = this.Temp_Database.getEmployeeConstraint(employee).get(i);
            if (constraint.getShiftDate().equals(date) && constraint.getShiftType().equals(shiftType)) {
                Temp_Database.getConstraint_temp_database().get(employee).remove(constraint);
            }
        }
    }

    //true=constraint exist; false=constraint not exist
    public boolean isConstraintExist(Employee employee, LocalDate date, ShiftType shiftType){
        for (int i=0;i<this.Temp_Database.getEmployeeConstraint(employee).size();i++){
            Constraint constraint = this.Temp_Database.getEmployeeConstraint(employee).get(i);
            if (constraint.getShiftDate().equals(date) && constraint.getShiftType().equals(shiftType)){
                return true;
            }
        }
        return false;
    }

    public List<Constraint> getConstraintForEmployee(Employee employee) {
        return Temp_Database.getEmployeeConstraint(employee);
    }
}
