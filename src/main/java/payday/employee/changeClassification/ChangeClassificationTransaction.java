package payday.employee.changeClassification;

import payday.employee.Employee;
import payday.employee.changeEmployee.ChangeEmployeeTransaction;
import payday.employee.classification.AbstractPaymentClassification;
import payday.employee.database.PayrollDatabase;
import payday.employee.schedule.AbstractPaymentSchedule;

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();
    public ChangeClassificationTransaction(Integer empId, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
    }

    @Override
    protected final void change(Employee e) {
        e.setClassification(getClassification());
        e.setSchedule(getSchedule());
    }

    abstract AbstractPaymentSchedule getSchedule();

    abstract AbstractPaymentClassification getClassification();
}
