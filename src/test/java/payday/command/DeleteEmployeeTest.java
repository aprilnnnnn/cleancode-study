package payday.command;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.employee.command.AddCommissionedEmployee;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.command.AddSalariedEmployee;
import payday.employee.command.DeleteEmployeeTransaction;
import payday.employee.database.PayrollDatabase;

import static org.junit.Assert.*;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class DeleteEmployeeTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void testDeleteSalariedEmployee() {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double salary = 10000.00D;

        AddSalariedEmployee t = new AddSalariedEmployee(empId, name, address, salary, payrollDatabase);
        t.execute();
        assertEquals("Bob", payrollDatabase.getEmployee(1).getItsName());
        DeleteEmployeeTransaction d = new DeleteEmployeeTransaction(payrollDatabase, empId);
        d.execute();
        assertNull(payrollDatabase.getEmployee(1));
    }

    @Test
    public void testDeleteHourlyEmployee() {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double hourlyWage = 10000.00D;
        payrollDatabase = new PayrollDatabase();
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, name, address, hourlyWage, payrollDatabase);
        t.execute();

        assertEquals("Bob", payrollDatabase.getEmployee(1).getItsName());
        DeleteEmployeeTransaction d = new DeleteEmployeeTransaction(payrollDatabase, empId);
        d.execute();
        assertNull(payrollDatabase.getEmployee(1));
    }

    @Test
    public void testDeleteCommissionEmployee() {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double salary = 10000.00D;
        final double commissionRate = 10.0;

        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, name, address, salary, commissionRate, payrollDatabase);
        t.execute();

        assertEquals("Bob", payrollDatabase.getEmployee(1).getItsName());
        DeleteEmployeeTransaction d = new DeleteEmployeeTransaction(payrollDatabase, empId);
        d.execute();
        assertNull(payrollDatabase.getEmployee(1));
    }
}
