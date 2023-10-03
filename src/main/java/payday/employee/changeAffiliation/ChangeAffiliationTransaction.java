package payday.employee.changeAffiliation;

import payday.employee.Employee;
import payday.employee.affilliation.command.AbstractAffiliation;
import payday.employee.changeEmployee.ChangeEmployeeTransaction;
import payday.employee.database.PayrollDatabase;

abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {
    ChangeAffiliationTransaction(Integer empId, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
    }

    @Override
    protected void change(Employee e) {
        e.setAffiliation(getAffiliation());
    }

    abstract AbstractAffiliation getAffiliation();

    abstract void RecordMembership(Integer empId, PayrollDatabase payrollDatabase);
}
