package payday.employee.affilliation.command;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;
import payday.Transaction;
import payday.employee.Employee;
import payday.employee.database.PayrollDatabase;

import java.util.Optional;

@Configurable
public class ServiceChargeTransaction implements Transaction {

    private final Integer empId;
    private final long dates;
    private final double amount;

    @Autowired
    private PayrollDatabase payrollDatabase;

    public ServiceChargeTransaction(@NonNull Integer empId, long dates, double amount, PayrollDatabase payrollDatabase) {
        this.empId = empId;
        this.dates = dates;
        this.amount = amount;
        this.payrollDatabase = payrollDatabase;
    }

    @Transactional
    @Override
    public void execute() {
        Employee employee = Optional.ofNullable(payrollDatabase.getEmployee(empId))
                .orElseThrow(() -> new IllegalArgumentException("No such employee."));

        UnionAffiliation uf = employee.getAffiliation(UnionAffiliation.class);
        uf.addServiceCharge(new ServiceCharge(dates, amount));
    }
}
