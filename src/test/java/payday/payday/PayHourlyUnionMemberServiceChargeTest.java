package payday.payday;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.Paycheck;
import payday.employee.changeAffiliation.ChangeAffiliatedTransaction;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.command.TimeCardTransaction;
import payday.employee.database.PayrollDatabase;
import payday.employee.payday.PaydayTransaction;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class PayHourlyUnionMemberServiceChargeTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void PayHourlyUnionMemberServiceChargeTest() {
        final Integer empId = 1;
        final String name = "Bill";
        final String address = "Home";
        final double hourlyWage = 15.24;
        payrollDatabase = new PayrollDatabase();
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, name, address, hourlyWage, payrollDatabase);
        t.execute();

        double dues = 9.42;
        new ChangeAffiliatedTransaction(empId, dues, payrollDatabase).execute();

        Calendar payCalendar = Calendar.getInstance();
        payCalendar.set(2023, Calendar.OCTOBER, 13);
        Date payDate = payCalendar.getTime();
        double hours = 8.0;                 // 일한 시간
        new TimeCardTransaction(payDate.getTime(), hours, empId, payrollDatabase).execute();

        PaydayTransaction paydayTransaction = new PaydayTransaction(payDate, payrollDatabase);
        paydayTransaction.execute();

        Paycheck pc = paydayTransaction.getPaycheck(empId);

        final double pay = hourlyWage * hours;

        assertThat(pc, is(notNullValue()));
        assertThat(pc.getGrossPay(), is(pay));
        assertThat(pc.getDeductions(), is(dues));
        assertThat(pc.getNetPay(), is(pay-dues));
        assertThat(pc.getField("Disposition"), is("Hold"));

    }



}
