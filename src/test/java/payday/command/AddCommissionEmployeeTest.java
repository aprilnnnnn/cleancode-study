package payday.command;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.employee.command.AddCommissionedEmployee;
import payday.employee.database.PayrollDatabase;
import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class AddCommissionEmployeeTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Before
    public void setUp() {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double salary = 10000.00D;
        final double commissionRate = 10.0;

        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, name, address, salary, commissionRate, payrollDatabase);
        t.execute();
    }

    @Test
    public void testAddSalariedEmployee() {
        assertEquals("Bob", payrollDatabase.getEmployee(1).getItsName());
    }

    @Test
    public void testGetSalary() {
        //assertThat(payrollDatabase.getEmployee(1).getClassification(SalariedClassification.class).getSalary(), is(10000.00D));
    }
}
