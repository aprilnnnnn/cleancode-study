package payday.employee.changeEmployee;

import payday.employee.Employee;
import payday.employee.database.PayrollDatabase;

public class ChangeAddressTransaction extends ChangeEmployeeTransaction {

    private final String address;
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    public ChangeAddressTransaction(Integer empId, String address, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.address = address;
    }

    @Override
    protected void change(Employee e) {
        e.setItsAddress(address);
        payrollDatabase.deleteEmployee(e.getEmpId());
        payrollDatabase.addEmployee(e.getEmpId(), e);
    }
}
