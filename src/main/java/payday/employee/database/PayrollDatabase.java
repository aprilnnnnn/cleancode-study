package payday.employee.database;

import payday.employee.Employee;
import payday.employee.affilliation.command.UnionAffiliation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PayrollDatabase {
    private Map<Integer, Employee> employee;
    private Map<Integer, Integer>  unionMembers;

    public PayrollDatabase() {
        employee = new HashMap<>();
        unionMembers = new HashMap<>();
    }

    public Employee getEmployee(int empid){
        if (employee.isEmpty()) {
            return null;
        }
        return employee.get(empid);
    }

    public void addEmployee(int empid, Employee employee) {
        this.employee.put(empid, employee);
    }

    public void deleteEmployee(int empid) {
        this.employee.remove(empid);
    }

    public void addUnionMember(int memberId, int employeeId) {
        this.unionMembers.put(memberId, employeeId);
    }

    public int getUnionEmployeeId(int memberId) {
        return unionMembers.get(memberId);
    }

    public void deleteUnionMember(int memberId) {
        unionMembers.remove(memberId);
    }
    public Map<Integer, Employee> getAllEmployees() {
        return employee;
    }
}
