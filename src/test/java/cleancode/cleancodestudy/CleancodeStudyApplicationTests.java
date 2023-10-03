package cleancode.cleancodestudy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import payday.employee.command.AddSalariedEmployee;
import payday.employee.database.PayrollDatabase;

@SpringBootTest
class CleancodeStudyApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testAddSalariedEmployee() throws Exception {
		// given
		final Integer empId = 1;
		final String name = "Bob";
		final String address = "Home";
		final double salary = 10000.00D;
		final PayrollDatabase payrollDatabase = new PayrollDatabase();

		AddSalariedEmployee t = new AddSalariedEmployee(empId, name, address, salary, payrollDatabase);
	}

}
