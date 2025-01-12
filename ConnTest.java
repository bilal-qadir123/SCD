package crime.rec.management.system;

import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ConnTest {
    @Test
    void testConnection() {
        try (Conn conn = new Conn()) {
            Connection connection = conn.getConnection();
            assertNotNull(connection, "Database connection should not be null");
            assertFalse(connection.isClosed(), "Connection should be open");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}
