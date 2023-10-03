package payday.changeAffiliation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import payday.employee.Employee;
import payday.employee.affilliation.command.AbstractAffiliation;
import payday.employee.affilliation.command.UnionAffiliation;
import payday.employee.changeAffiliation.ChangeAffiliatedTransaction;
import payday.employee.changeAffiliation.ChangeUnaffiliatedTransaction;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.database.PayrollDatabase;

import static org.assertj.core.internal.bytebuddy.implementation.FixedValue.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class ChangeUnaffiliatedTransactionTest {

    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void testChangeUnaffiliatedTransaction() throws Exception {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double hourlyWage = 10000.00D;
        payrollDatabase = new PayrollDatabase();
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, name, address, hourlyWage, payrollDatabase);
        t.execute();

        ChangeUnaffiliatedTransaction changeUnaffiliatedTransaction = new ChangeUnaffiliatedTransaction(empId, payrollDatabase);
        changeUnaffiliatedTransaction.execute();

        Employee employee = payrollDatabase.getEmployee(empId);

        assertThat(employee.getAffiliation(), is(AbstractAffiliation.NONE));
    }
}
