package payday.changeAffiliation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import payday.employee.Employee;
import payday.employee.affilliation.command.UnionAffiliation;
import payday.employee.changeAffiliation.ChangeAffiliatedTransaction;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.database.PayrollDatabase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class ChangeAffiliatedTransactionTest {

    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void testChangeAffiliatedTransaction() throws Exception {
        final Integer empId = 1;
        final String name = "Bob";
        final String address = "Home";
        final double hourlyWage = 10000.00D;
        payrollDatabase = new PayrollDatabase();
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, name, address, hourlyWage, payrollDatabase);
        t.execute();

        final double dues = 99.42;
        ChangeAffiliatedTransaction changeAffiliatedTransaction = new ChangeAffiliatedTransaction(empId, dues, payrollDatabase);
        changeAffiliatedTransaction.execute();

        Employee employee = payrollDatabase.getEmployee(empId);
        UnionAffiliation unionAffiliation = employee.getAffiliation(UnionAffiliation.class);

        assertThat(unionAffiliation.getDues(), is(dues));
    }
}
