package payday.command;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.employee.Employee;
import payday.employee.classification.CommissionedClassification;
import payday.employee.classification.SalesReceipt;
import payday.employee.command.AddCommissionedEmployee;
import payday.employee.command.SalesReceiptTransaction;
import payday.employee.database.PayrollDatabase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class TimeSalesReceiptTest {
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
    public void testSalesReceipt() {
        final long dates = System.currentTimeMillis();
        final double amount = 1000.0D;

        Employee employee = payrollDatabase.getEmployee(1);

        SalesReceiptTransaction salesReceiptTransaction = new SalesReceiptTransaction(dates, amount, employee.getEmpId(), payrollDatabase);
        salesReceiptTransaction.execute();

        CommissionedClassification commissionedClassification = employee.getClassification(CommissionedClassification.class);
        SalesReceipt salesReceipt = commissionedClassification.getSalesReceipt(dates);

        assertThat(salesReceipt.getAmount(), is(amount));
    }


}
