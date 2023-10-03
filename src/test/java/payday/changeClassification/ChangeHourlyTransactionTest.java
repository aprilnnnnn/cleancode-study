package payday.changeClassification;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.PaydayApplication;
import payday.employee.Employee;
import payday.employee.changeClassification.ChangeHourlyTransaction;
import payday.employee.classification.HourlyClassification;
import payday.employee.command.AddCommissionedEmployee;
import payday.employee.database.PayrollDatabase;
import payday.employee.schedule.WeaklySchedule;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = PaydayApplication.class)
public class ChangeHourlyTransactionTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void testChangeHourlyTransaction() throws Exception {
        final Integer empId = 3;
        final String name = "Lance";
        final String address = "Home";
        final double salary = 2500;
        final double commissionRate = 3.2;

        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, name, address, salary, commissionRate, payrollDatabase);
        t.execute();

        final double rate = 27.52;

        ChangeHourlyTransaction changeHourlyTransaction = new ChangeHourlyTransaction(empId, rate, payrollDatabase);
        changeHourlyTransaction.execute();

        Employee modifiedEmployee = payrollDatabase.getEmployee(empId);

        HourlyClassification hc = modifiedEmployee.getClassification(HourlyClassification.class);
        assertThat(hc.getRate(), is(rate));

        WeaklySchedule ws = modifiedEmployee.getSchedule(WeaklySchedule.class);
        assertThat(ws, is(notNullValue()));
    }
}
