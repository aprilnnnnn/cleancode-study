package payday.payday;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.Paycheck;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.database.PayrollDatabase;
import payday.employee.payday.PaydayTransaction;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class PaySingleHourlyEmployeeNoTimeCardsTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void PaySingleHourlyEmployeeTest() {
        final Integer empId = 1;
        final String name = "Bill";
        final String address = "Home";
        final double hourlyWage = 15.25;
        payrollDatabase = new PayrollDatabase();
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, name, address, hourlyWage, payrollDatabase);
        t.execute();

        Calendar payCalendar = Calendar.getInstance();
        payCalendar.set(2023, Calendar.OCTOBER, 6);
        Date payDate = payCalendar.getTime();

        PaydayTransaction paydayTransaction = new PaydayTransaction(payDate, payrollDatabase);
        paydayTransaction.execute();

        Paycheck pc = paydayTransaction.getPaycheck(empId);

        //assertThat(pc, is(notNullValue()));
        assertThat(pc.getGrossPay(), is(0.0));
        assertThat(pc.getDeductions(), is(0.0));
        assertThat(pc.getNetPay(), is(0.0));
        assertThat(pc.getField("Disposition"), is("Hold"));

    }



}
