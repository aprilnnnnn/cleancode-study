package payday.changeClassification;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.PaydayApplication;
import payday.employee.Employee;
import payday.employee.changeClassification.ChangeHourlyTransaction;
import payday.employee.changeClassification.ChangeSalariedTransaction;
import payday.employee.classification.HourlyClassification;
import payday.employee.classification.SalariedClassification;
import payday.employee.command.AddCommissionedEmployee;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.command.AddSalariedEmployee;
import payday.employee.database.PayrollDatabase;
import payday.employee.schedule.MonthlySchedule;
import payday.employee.schedule.WeaklySchedule;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = PaydayApplication.class)
public class ChangeSalariedTransactionTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void testChangeSalariedTransaction() throws Exception {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double hourlyWage = 10000.00D;
        payrollDatabase = new PayrollDatabase();
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, name, address, hourlyWage, payrollDatabase);
        t.execute();

        final double salary = 10000.00D;

        ChangeSalariedTransaction changeSalariedTransaction = new ChangeSalariedTransaction(empId, salary, payrollDatabase);
        changeSalariedTransaction.execute();

        Employee modifiedEmployee = payrollDatabase.getEmployee(empId);

        SalariedClassification hc = modifiedEmployee.getClassification(SalariedClassification.class);
        assertThat(hc.getSalary(), is(salary));

        MonthlySchedule ws = modifiedEmployee.getSchedule(MonthlySchedule.class);
        assertThat(ws, is(notNullValue()));
    }
}
