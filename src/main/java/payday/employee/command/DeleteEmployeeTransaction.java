package payday.employee.command;

import payday.Transaction;
import payday.employee.database.PayrollDatabase;

public class DeleteEmployeeTransaction implements Transaction {
    private PayrollDatabase payrollDatabase;
    private int empId;

    public DeleteEmployeeTransaction(PayrollDatabase payrollDatabase, int empId) {
        this.payrollDatabase = payrollDatabase;
        this.empId = empId;
    }

    @Override
    public void execute() {
        payrollDatabase.deleteEmployee(empId);
    }
}
