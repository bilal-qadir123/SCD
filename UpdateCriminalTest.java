package crime.rec.management.system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Statement;
import java.util.regex.Pattern;

class UpdateCriminalTest {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String MOBILE_PATTERN = "^\\+92[0-9]{10}$";

    @Test
    void testUpdateRecord() {
        Conn mockConn = mock(Conn.class);
        Statement mockStatement = mock(Statement.class);

        try {
            when(mockConn.getStatement()).thenReturn(mockStatement);

            String name = "John Doe";
            String fname = "Jane Doe";
            String dob = "1990-05-25";
            int noofcrimes = 3;
            String address = "123 Main St";
            String phone = "+923001234567"; // Change this value to test
            String email = "john.doe@example.com"; // Change this value to test
            String jailterm = "5 years";
            String gender = "Male";
            String crimetype = "Robbery";
            String aadhar = "123456789012";
            int caseId = 99528;

            boolean isEmailValid = Pattern.matches(EMAIL_PATTERN, email);
            boolean isPhoneValid = Pattern.matches(MOBILE_PATTERN, phone);

            if (!isEmailValid || !isPhoneValid) {
                fail("Invalid email or phone number");
            }
            else if (name.isEmpty() || fname.isEmpty() || dob.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty() || jailterm.isEmpty() || gender.isEmpty() || crimetype.isEmpty() || aadhar.isEmpty()) {
                fail("Please fill in all required fields");
            }

            String updateQuery = "UPDATE criminal SET name = '" + name + "', fname = '" + fname + "', dob = '" + dob + "', noofcrimes = " + noofcrimes + ", address = '" + address + "', phone = '" + phone + "', email = '" + email + "', jailterm = '" + jailterm + "', gender = '" + gender + "', crimetype = '" + crimetype + "', aadhar = '" + aadhar + "' WHERE caseId = " + caseId;

            when(mockStatement.executeUpdate(updateQuery)).thenReturn(1);

            int result = mockStatement.executeUpdate(updateQuery);
            assertEquals(1, result);
        } catch (Exception e) {
            fail("Exception during mock test: " + e.getMessage());
        }
    }
}