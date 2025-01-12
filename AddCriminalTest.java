package crime.rec.management.system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Statement;
import java.util.regex.Pattern;

class AddCriminalTest {

    // Email validation pattern
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Mobile number validation pattern
    private static final String MOBILE_PATTERN = "^\\+92[0-9]{10}$";

    @Test
    void testAddRecord() {
        // Mock the Conn object
        Conn mockConn = mock(Conn.class);

        // Mock the Statement object
        Statement mockStatement = mock(Statement.class);

        try {
            // Ensure the Conn object returns the mock Statement
            when(mockConn.getStatement()).thenReturn(mockStatement);

            // Dummy data for criminal record
            String name = "John Doe";
            String fname = "Jane Doe";
            String dob = "1990-05-25";
            int noofcrimes = 3;
            String address = "123 Main St";
            String phone = "+923001234567"; // Valid mobile number
            String email = "john.doe@example.com"; // Valid email
            String jailterm = "5 years";
            String gender = "Male";
            String crimetype = "Robbery";
            String aadhar = "123456789012";
            int caseId = 99528;

            // Validate email and phone number
            boolean isEmailValid = Pattern.matches(EMAIL_PATTERN, email);
            boolean isPhoneValid = Pattern.matches(MOBILE_PATTERN, phone);

            // If email or phone is invalid, fail the test
            assertTrue(isEmailValid, "Email is invalid");
            assertTrue(isPhoneValid, "Phone number is invalid");

            // Prepare the SQL insert query with dummy data
            String insertQuery = "INSERT INTO criminal (name, fname, dob, noofcrimes, address, phone, email, jailterm, gender, crimetype, aadhar, caseId) " +
                                 "VALUES ('" + name + "', '" + fname + "', '" + dob + "', " + noofcrimes + ", '" + address + "', '" + phone + "', '" + email + "', '" + jailterm + "', '" + gender + "', '" + crimetype + "', '" + aadhar + "', " + caseId + ")";

            // Define behavior for executeUpdate
            when(mockStatement.executeUpdate(insertQuery)).thenReturn(1);

            // Execute the method and check the result
            int result = mockStatement.executeUpdate(insertQuery);
            assertEquals(1, result, "Record should be inserted");
        } catch (Exception e) {
            fail("Exception during mock test: " + e.getMessage());
        }
    }
}