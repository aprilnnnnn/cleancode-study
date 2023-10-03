package payday.employee.command;
import org.springframework.lang.NonNull;
import payday.Transaction;
import payday.employee.Employee;
import payday.employee.classification.AbstractPaymentClassification;
import payday.employee.database.PayrollDatabase;
import payday.employee.method.AbstractPaymentMethod;
import payday.employee.method.HoldMethod;
import payday.employee.schedule.AbstractPaymentSchedule;

public abstract class AddEmployeeTransaction implements Transaction {
    private final Integer empId;
    private final String itsAddress;
    private final String itsName;

    private PayrollDatabase payrollDatabase;

    AddEmployeeTransaction(@NonNull Integer empId, @NonNull String itsName, @NonNull String itsAddress, PayrollDatabase payrollDatabase) {
        this.empId = empId;
        this.itsName = itsName;
        this.itsAddress = itsAddress;
        this.payrollDatabase = payrollDatabase;
    }

    @Override
    public final void execute() {
        AbstractPaymentClassification pc = getClassification();
        AbstractPaymentSchedule ps = getSchedule();
        AbstractPaymentMethod pm = new HoldMethod();
        Employee employee = new Employee(empId, itsName, itsAddress, pc, ps, pm);
        payrollDatabase.addEmployee(empId, employee);
    }

    abstract AbstractPaymentSchedule getSchedule();

    abstract AbstractPaymentClassification getClassification();
}
