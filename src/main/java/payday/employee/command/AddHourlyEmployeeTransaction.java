package payday.employee.command;

import payday.employee.classification.AbstractPaymentClassification;
import payday.employee.classification.HourlyClassification;
import payday.employee.database.PayrollDatabase;
import payday.employee.schedule.AbstractPaymentSchedule;
import payday.employee.schedule.WeaklySchedule;

public class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {
    private final double hourlyWage;

    public AddHourlyEmployeeTransaction(Integer empId, String name, String address, double hourlyWage, PayrollDatabase payrollDatabase) {
        super(empId, name, address, payrollDatabase);
        this.hourlyWage = hourlyWage;
    }

    @Override
    AbstractPaymentSchedule getSchedule() {
        return new WeaklySchedule();
    }

    @Override
    AbstractPaymentClassification getClassification() {
        return new HourlyClassification(this.hourlyWage);
    }
}
