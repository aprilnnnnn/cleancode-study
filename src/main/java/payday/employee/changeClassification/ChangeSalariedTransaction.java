package payday.employee.changeClassification;

import payday.employee.classification.AbstractPaymentClassification;
import payday.employee.classification.SalariedClassification;
import payday.employee.database.PayrollDatabase;
import payday.employee.schedule.AbstractPaymentSchedule;
import payday.employee.schedule.MonthlySchedule;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {

    private final double salary;

    public ChangeSalariedTransaction(Integer empId, double salary, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.salary = salary;
    }

    @Override
    AbstractPaymentClassification getClassification() {
        return new SalariedClassification(salary);
    }

    @Override
    AbstractPaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}
