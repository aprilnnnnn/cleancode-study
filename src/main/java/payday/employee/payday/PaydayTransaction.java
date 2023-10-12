package payday.employee.payday;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import payday.Paycheck;
import payday.Transaction;
import payday.employee.Employee;
import payday.employee.EmployeeRepository;
import payday.employee.database.PayrollDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaydayTransaction implements Transaction {
    private final Date payDate;

    private final Map<Integer, Paycheck> paychecks = new HashMap<>();
    private PayrollDatabase payrollDatabase;

    public PaydayTransaction(Date payDate, PayrollDatabase payrollDatabase) {
        this.payDate = (Date) payDate.clone();
        this.payrollDatabase = payrollDatabase;
    }

    @Override
    public void execute() {
        for (Map.Entry<Integer, Employee> employeeRecord : payrollDatabase.getAllEmployees().entrySet()) {
            Employee employee = employeeRecord.getValue();

            if (!employee.isPayDate(payDate)) {
                continue;
            }

            Paycheck pc = employee.payday(payDate);
            paychecks.put(employee.getEmpId(), pc);
        }
    }

    public Paycheck getPaycheck(@NonNull Integer empId) {
        return paychecks.get(empId);
    }
}
