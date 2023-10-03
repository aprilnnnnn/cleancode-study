package payday.command;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import com.sun.xml.bind.v2.schemagen.xmlschema.Union;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.employee.Employee;
import payday.employee.affilliation.command.ServiceCharge;
import payday.employee.affilliation.command.ServiceChargeTransaction;
import payday.employee.affilliation.command.UnionAffiliation;
import payday.employee.classification.CommissionedClassification;
import payday.employee.classification.SalesReceipt;
import payday.employee.command.AddHourlyEmployeeTransaction;
import payday.employee.command.SalesReceiptTransaction;
import payday.employee.database.PayrollDatabase;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class ServiceChargeTransactionTest {
    private PayrollDatabase payrollDatabase = new PayrollDatabase();

    @Test
    public void ServiceChargeTest() {
        final Integer empId = 2;
        final long dates = System.currentTimeMillis();
        final double amount = 12.95;

        final String name = "Bill";
        final String address = "Home";
        final double hourlyWage = 15.25;

        payrollDatabase = new PayrollDatabase();
        AddHourlyEmployeeTransaction hourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId, name, address, hourlyWage, payrollDatabase);
        hourlyEmployeeTransaction.execute();
        Employee employee = payrollDatabase.getEmployee(2);
        UnionAffiliation af = new UnionAffiliation(12.5);
        employee.setAffiliation(af);

        ServiceChargeTransaction serviceChargeTransaction = new ServiceChargeTransaction(empId, dates, amount, payrollDatabase);
        serviceChargeTransaction.execute();

        ServiceCharge serviceCharge = af.getServiceCharge(dates);

        assertThat(serviceCharge, is(notNullValue()));
        assertThat(serviceCharge.getAmount(), is(amount));

    }
}
