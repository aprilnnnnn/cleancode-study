package payday.employee.changeEmployee;
import payday.employee.Employee;
import payday.employee.database.PayrollDatabase;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {
    private final String name;
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    public ChangeNameTransaction(Integer empId, String name, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.name = name;
    }

    @Override
    protected void change(Employee e) {
        e.setItsName(name);
        payrollDatabase.deleteEmployee(e.getEmpId());
        payrollDatabase.addEmployee(e.getEmpId(), e);
    }
}
