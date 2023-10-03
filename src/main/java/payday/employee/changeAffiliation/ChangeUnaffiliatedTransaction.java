package payday.employee.changeAffiliation;

import payday.employee.Employee;
import payday.employee.affilliation.command.AbstractAffiliation;
import payday.employee.affilliation.command.Affiliation;
import payday.employee.database.PayrollDatabase;

import java.util.Optional;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {
    public ChangeUnaffiliatedTransaction(Integer empId, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        RecordMembership(empId, payrollDatabase);
    }

    AbstractAffiliation getAffiliation() {
        return null;
    }

    @Override
    void RecordMembership(Integer empId, PayrollDatabase payrollDatabase) {
        Employee employee = payrollDatabase.getEmployee(empId);
        Optional<Affiliation> affiliation = Optional.ofNullable(employee.getAffiliation());

        if (affiliation.isPresent()) {
            int memberId = employee.getEmpId();
            payrollDatabase.deleteUnionMember(memberId);
        }
    }
}
