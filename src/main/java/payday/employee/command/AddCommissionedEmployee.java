package payday.employee.command;

import payday.employee.classification.AbstractPaymentClassification;
import payday.employee.classification.CommissionedClassification;
import payday.employee.database.PayrollDatabase;
import payday.employee.schedule.AbstractPaymentSchedule;
import payday.employee.schedule.BiWeaklySchedule;

public class AddCommissionedEmployee extends AddEmployeeTransaction {
    private final double salary;
    private final double commissionRate;

    public AddCommissionedEmployee(Integer empId, String name, String address, double salary, double commissionRate, PayrollDatabase payrollDatabase) {
        super(empId, name, address, payrollDatabase);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    AbstractPaymentClassification getClassification() {
        return new CommissionedClassification(salary, commissionRate);
    }

    @Override
    AbstractPaymentSchedule getSchedule() {
        return new BiWeaklySchedule();
    }
}
