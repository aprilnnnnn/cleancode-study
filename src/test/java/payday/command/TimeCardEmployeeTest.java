package payday.command;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.employee.Employee;
import payday.employee.classification.HourlyClassification;
import payday.employee.classification.TimeCard;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.command.TimeCardTransaction;
import payday.employee.database.PayrollDatabase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class TimeCardEmployeeTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Before
    public void setUp() {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double hourlyWage = 10000.00D;
        payrollDatabase = new PayrollDatabase();
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, name, address, hourlyWage, payrollDatabase);
        t.execute();
    }

    @Test
    public void testTimeCard() {
        final long dates = System.currentTimeMillis();
        final double hours = 8.0D;

        Employee employee = payrollDatabase.getEmployee(1);

        TimeCardTransaction timeCardTransaction = new TimeCardTransaction(dates, hours, employee.getEmpId(), payrollDatabase);
        timeCardTransaction.execute();

        HourlyClassification hourlyClassification = employee.getClassification(HourlyClassification.class);
        TimeCard timeCard = hourlyClassification.getTimeCard(dates);

        assertThat(timeCard.getHours(), is(hours));
    }


}
