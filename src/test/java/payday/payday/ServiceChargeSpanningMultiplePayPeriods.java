package payday.payday;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.Paycheck;
import payday.employee.affilliation.command.ServiceChargeTransaction;
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
public class ServiceChargeSpanningMultiplePayPeriods {
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

        double dues = 9.42;
        new ChangeAffiliatedTransaction(empId, dues, payrollDatabase).execute();

        Calendar payCalendar = Calendar.getInstance();
        payCalendar.set(2001, Calendar.NOVEMBER, 9);
        Date payDate = payCalendar.getTime();

        payCalendar.set(2001, Calendar.NOVEMBER, 2);
        Date payDate2 = payCalendar.getTime();

        payCalendar.set(2001, Calendar.NOVEMBER, 16);
        Date payDate3 = payCalendar.getTime();

        final double charge = 19.42;
        addServiceCharge(empId, payDate2, 100.00);   // 지난 주 금요일
        addServiceCharge(empId, payDate, charge);           // 기간 내
        addServiceCharge(empId, payDate3, 200.00);   // 다음 주 금요일

        double hours = 8.0;
        new TimeCardTransaction(payDate.getTime(), hours, empId, payrollDatabase).execute();

        PaydayTransaction paydayTransaction = new PaydayTransaction(payDate, payrollDatabase);
        paydayTransaction.execute();

        Paycheck pc = paydayTransaction.getPaycheck(empId);

        assertThat(pc, is(notNullValue()));
        assertThat(pc.getGrossPay(), is(hourlyWage * hours));
        assertThat(pc.getDeductions(), is(dues + charge));
        assertThat(pc.getNetPay(), is((hourlyWage * hours) - (dues + charge)));
        assertThat(pc.getField("Disposition"), is("Hold"));

    }
    private void addServiceCharge(Integer empId, Date date, double charge) {
        new ServiceChargeTransaction(empId, date.getTime(), charge, payrollDatabase).execute();
    }



}
