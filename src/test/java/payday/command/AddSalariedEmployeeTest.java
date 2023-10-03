package payday.command;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.employee.classification.SalariedClassification;
import payday.employee.command.AddSalariedEmployee;
import payday.employee.database.PayrollDatabase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class AddSalariedEmployeeTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Before
    public void setUp() {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double salary = 10000.00D;

        AddSalariedEmployee t = new AddSalariedEmployee(empId, name, address, salary, payrollDatabase);
        t.execute();
    }

    @Test
    public void testAddSalariedEmployee() {
        assertEquals("Bob", payrollDatabase.getEmployee(1).getItsName());
    }

    @Test
    public void testGetSalary() {
        assertThat(payrollDatabase.getEmployee(1).getClassification(SalariedClassification.class).getSalary(), is(10000.00D));
    }
}
