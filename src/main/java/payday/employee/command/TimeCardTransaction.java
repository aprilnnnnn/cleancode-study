package payday.employee.command;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;
import payday.Transaction;
import payday.employee.Employee;
import payday.employee.EmployeeRepository;
import payday.employee.classification.HourlyClassification;
import payday.employee.classification.TimeCard;
import payday.employee.database.PayrollDatabase;

import java.util.Optional;

@Configurable
public class TimeCardTransaction implements Transaction {
    private final Long dates;
    private final double hours;
    private final Integer empId;
    @Autowired
    private PayrollDatabase payrollDatabase;

    public TimeCardTransaction(@NonNull Long dates, double hours, @NonNull Integer empId, @NonNull PayrollDatabase payrollDatabase) {
        this.payrollDatabase = payrollDatabase;
        this.dates = dates;
        this.hours = hours;
        this.empId = empId;
    }

    @Transactional
    @Override
    public void execute() {
        Employee employee = Optional.ofNullable(payrollDatabase.getEmployee(empId))
                .orElseThrow(() -> new IllegalArgumentException("No such employee."));

        try {
            HourlyClassification hourlyClassification = employee.getClassification(HourlyClassification.class);
            hourlyClassification.addTimeCard(new TimeCard(dates, hours));
        } catch (Exception e) {
            new IllegalArgumentException("Tried to add timecard to non-hourly employee");
        }
    }
}
