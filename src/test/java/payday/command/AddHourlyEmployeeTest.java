package payday.command;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.database.PayrollDatabase;
import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class AddHourlyEmployeeTest {
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
    public void testAddHourlyEmployee() {
        assertEquals("Bob", payrollDatabase.getEmployee(1).getItsName());
    }

    @Test
    public void testGetSalary() {
        //assertThat(payrollDatabase.getEmployee(1).getClassification(HourlyClassification.class).getSalary(), is(10000.00D));
    }


}
