package crime.rec.management.system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {
    @Test
    void testValidEmail() {
        Register register = new Register();
        assertTrue(register.isValidEmail("test@example.com"));
        assertFalse(register.isValidEmail("invalid-email"));
    }

    @Test
    void testValidMobile() {
        Register register = new Register();

        assertTrue(register.isValidMobile("+923456789012"));
        assertFalse(register.isValidMobile("12345"));
        assertFalse(register.isValidMobile("+123456789012"));
        assertFalse(register.isValidMobile("+92345"));
        assertFalse(register.isValidMobile("+923456789012345"));
    }
}
