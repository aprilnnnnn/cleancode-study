package payday.payrollDatabaseTest;

import cleancode.cleancodestudy.CleancodeStudyApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.employee.Employee;
import payday.employee.classification.AbstractPaymentClassification;
import payday.employee.classification.HourlyClassification;
import payday.employee.database.PayrollDatabase;
import payday.employee.method.AbstractPaymentMethod;
import payday.employee.method.HoldMethod;
import payday.employee.schedule.AbstractPaymentSchedule;
import payday.employee.schedule.WeaklySchedule;

import static junit.framework.Assert.assertEquals;

@SpringBootTest(classes = CleancodeStudyApplication.class)
public class PayrollDatabaseTest {
    private PayrollDatabase payrollDatabase;
    private Employee employee;
    private int empId;
    private int MemberId;

    @Before
    public void setUp() {
        empId = 1;
        MemberId = 1;
        payrollDatabase = new PayrollDatabase();

        AbstractPaymentClassification pc = getClassification();
        AbstractPaymentSchedule ps = getSchedule();
        AbstractPaymentMethod pm = new HoldMethod();
        Employee employee = new Employee(empId, "itsName", "itsAddress", pc, ps, pm);
        payrollDatabase.addEmployee(empId, employee);
    }

    @Test
    public void getEmployee(){
        assertEquals(empId, payrollDatabase.getEmployee(empId).getEmpId().intValue());
    }
    AbstractPaymentSchedule getSchedule() {
        return new WeaklySchedule();
    }

    AbstractPaymentClassification getClassification() {
        return new HourlyClassification(11.0);
    }
}
