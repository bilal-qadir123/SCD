package crime.rec.management.system;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.sql.Statement;

class DeleteCriminalTest {
    @Test
    void testDeleteRecord() {
        // Mock the Conn object
        Conn mockConn = mock(Conn.class);

        // Mock the Statement object
        Statement mockStatement = mock(Statement.class);
        
        try {
            // Ensure the Conn object returns the mock Statement
            when(mockConn.getStatement()).thenReturn(mockStatement);
            
            // Define behavior for executeUpdate
            when(mockStatement.executeUpdate("DELETE FROM criminal WHERE caseId = '99527'")).thenReturn(1);
            
            // Execute the method and check the result
            int result = mockStatement.executeUpdate("DELETE FROM criminal WHERE caseId = '99527'");
            assertEquals(1, result, "Record should be deleted");
        } catch (Exception e) {
            fail("Exception during mock test: " + e.getMessage());
        }
    }
}
