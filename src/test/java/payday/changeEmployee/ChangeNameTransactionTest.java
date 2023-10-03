package payday.changeEmployee;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.PaydayApplication;
import payday.employee.Employee;
import payday.employee.changeEmployee.ChangeNameTransaction;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.database.PayrollDatabase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = PaydayApplication.class)
public class ChangeNameTransactionTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void testChangeAddressTransaction() throws Exception {
        final Integer empId = 2;
        final String name = "Bill";
        final String address = "Home";
        final double hourlyWage = 15.25;
        payrollDatabase = new PayrollDatabase();
        AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId, name, address, hourlyWage, payrollDatabase);
        addHourlyEmployeeTransaction.execute();

        final String newName = "Bob";
        ChangeNameTransaction changeNameTransaction = new ChangeNameTransaction(empId, newName, payrollDatabase);
        changeNameTransaction.execute();
        Employee modifiedEmployee = payrollDatabase.getEmployee(empId);

        assertThat(modifiedEmployee.getItsName(), is(newName));
    }
}
