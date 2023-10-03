package payday.employee.changeEmployee;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;
import payday.Transaction;
import payday.employee.Employee;
import payday.employee.database.PayrollDatabase;

import java.util.Optional;

@Configurable
public abstract class ChangeEmployeeTransaction implements Transaction{
    private final Integer empId;
    private PayrollDatabase payrollDatabase;

    public ChangeEmployeeTransaction(Integer empId, PayrollDatabase payrollDatabase) {
        this.empId = empId;
        this.payrollDatabase = payrollDatabase;
    }

    @Transactional
    @Override
    public final void execute() {
        Employee e = Optional.ofNullable(payrollDatabase.getEmployee(empId))
            .orElseThrow(() -> new IllegalArgumentException("Not found employee : " + empId));
        change(e);
    }

    protected abstract void change(Employee e);
}
