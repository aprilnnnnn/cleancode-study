package payday.employee.changeClassification;


import payday.employee.classification.AbstractPaymentClassification;
import payday.employee.classification.HourlyClassification;
import payday.employee.database.PayrollDatabase;
import payday.employee.schedule.AbstractPaymentSchedule;
import payday.employee.schedule.WeaklySchedule;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {
    private final double hourlyRate;

    public ChangeHourlyTransaction(Integer empId, double hourlyRate, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.hourlyRate = hourlyRate;
    }

    @Override
    AbstractPaymentSchedule getSchedule() {
        return new WeaklySchedule();
    }

    @Override
    AbstractPaymentClassification getClassification() {
        return new HourlyClassification(hourlyRate);
    }
}
