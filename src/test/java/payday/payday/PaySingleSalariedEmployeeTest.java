package payday.payday;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.Paycheck;
import payday.employee.command.AddSalariedEmployee;
import payday.employee.database.PayrollDatabase;
import payday.employee.payday.PaydayTransaction;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class PaySingleSalariedEmployeeTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void PaySingleSalariedEmployeeTest() throws Exception {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double salary = 10000.00D;

        AddSalariedEmployee addSalariedEmployee = new AddSalariedEmployee(empId, name, address, salary, payrollDatabase);
        addSalariedEmployee.execute();

        Calendar payCalendar = Calendar.getInstance();
        payCalendar.set(2023, Calendar.OCTOBER, 31);
        Date payDate = payCalendar.getTime();

        PaydayTransaction paydayTransaction = new PaydayTransaction(payDate, payrollDatabase);
        paydayTransaction.execute();

        Paycheck paycheck = paydayTransaction.getPaycheck(empId);

        assertThat(paycheck, is(notNullValue()));
        assertThat(paycheck.getGrossPay(), is(salary));
        assertThat(paycheck.getDeductions(), is(0.0));
        assertThat(paycheck.getDeductions(), is(0.0));
        assertThat(paycheck.getNetPay(), is(salary));
    }
}
