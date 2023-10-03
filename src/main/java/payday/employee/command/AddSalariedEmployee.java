package payday.employee.command;

import payday.employee.classification.AbstractPaymentClassification;
import payday.employee.classification.SalariedClassification;
import payday.employee.database.PayrollDatabase;
import payday.employee.schedule.AbstractPaymentSchedule;
import payday.employee.schedule.MonthlySchedule;

public class AddSalariedEmployee extends AddEmployeeTransaction{

    private final double salary;

    public AddSalariedEmployee(Integer empId, String itsName, String itsAddress, double salary, PayrollDatabase payrollDatabase) {
        super(empId, itsName, itsAddress, payrollDatabase);
        this.salary = salary;
    }

    @Override
    AbstractPaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }

    @Override
    AbstractPaymentClassification getClassification() {
        return new SalariedClassification(salary);
    }
}
