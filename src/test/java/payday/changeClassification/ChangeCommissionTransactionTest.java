package payday.changeClassification;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.PaydayApplication;
import payday.employee.Employee;
import payday.employee.changeClassification.ChangeCommissionedTransaction;
import payday.employee.changeClassification.ChangeHourlyTransaction;
import payday.employee.classification.CommissionedClassification;
import payday.employee.classification.HourlyClassification;
import payday.employee.command.AddCommissionedEmployee;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.database.PayrollDatabase;
import payday.employee.schedule.BiWeaklySchedule;
import payday.employee.schedule.WeaklySchedule;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = PaydayApplication.class)
public class ChangeCommissionTransactionTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void testCommissionedTransaction() throws Exception {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double hourlyWage = 10000.00D;
        payrollDatabase = new PayrollDatabase();
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, name, address, hourlyWage, payrollDatabase);
        t.execute();

        final double salary = 10000.00D;
        final double commissionRate = 10.0;

        ChangeCommissionedTransaction changeCommissionedTransaction = new ChangeCommissionedTransaction(empId, salary, commissionRate, payrollDatabase);
        changeCommissionedTransaction.execute();

        Employee modifiedEmployee = payrollDatabase.getEmployee(empId);

        CommissionedClassification cc = modifiedEmployee.getClassification(CommissionedClassification.class);
        assertThat(cc.getSalary(), is(salary));

        BiWeaklySchedule bws = modifiedEmployee.getSchedule(BiWeaklySchedule.class);
        assertThat(bws, is(notNullValue()));
    }
}
