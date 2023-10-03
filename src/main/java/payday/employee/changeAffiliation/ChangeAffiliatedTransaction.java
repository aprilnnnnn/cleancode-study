package payday.employee.changeAffiliation;

import payday.employee.Employee;
import payday.employee.affilliation.command.AbstractAffiliation;
import payday.employee.affilliation.command.Affiliation;
import payday.employee.affilliation.command.UnionAffiliation;
import payday.employee.database.PayrollDatabase;

public class ChangeAffiliatedTransaction extends ChangeAffiliationTransaction {
    private final double dues;

    public ChangeAffiliatedTransaction(Integer empId, double dues, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.dues = dues;
    }

    @Override
    AbstractAffiliation getAffiliation() {
        return new UnionAffiliation(dues);
    }

    @Override
    void RecordMembership(Integer empId, PayrollDatabase payrollDatabase) {}
}
