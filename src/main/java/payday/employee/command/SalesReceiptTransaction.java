package payday.employee.command;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;
import payday.Transaction;
import payday.employee.Employee;
import payday.employee.classification.CommissionedClassification;
import payday.employee.classification.HourlyClassification;
import payday.employee.classification.SalesReceipt;
import payday.employee.classification.TimeCard;
import payday.employee.database.PayrollDatabase;

import java.util.Optional;

@Configurable
public class SalesReceiptTransaction implements Transaction {
    private final Long dates;
    private final double amount;
    private final Integer empId;
    @Autowired
    private PayrollDatabase payrollDatabase;

    public SalesReceiptTransaction(@NonNull Long dates, double amount, @NonNull Integer empId, @NonNull PayrollDatabase payrollDatabase) {
        this.payrollDatabase = payrollDatabase;
        this.dates = dates;
        this.amount = amount;
        this.empId = empId;
    }

    @Transactional
    @Override
    public void execute() {
        Employee employee = Optional.ofNullable(payrollDatabase.getEmployee(empId))
                .orElseThrow(() -> new IllegalArgumentException("No such employee."));

        try {
            CommissionedClassification commissionedClassification = employee.getClassification(CommissionedClassification.class);
            commissionedClassification.addSalesReceipt(new SalesReceipt(dates, amount));
        } catch (Exception e) {
            new IllegalArgumentException("Tried to add timecard to non-hourly employee");
        }
    }
}
