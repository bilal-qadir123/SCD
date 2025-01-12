package crime.rec.management.system;

import java.sql.*;

public class Conn implements AutoCloseable {

    public Connection c;
    public Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/crimerecordmanagementsystem", "root", "Rootroot12345");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized Statement getStatement() throws SQLException {
        // Synchronizing access to the Statement object to avoid deadlocks
        if (s == null || s.isClosed()) {
            s = c.createStatement();
        }
        return s;
    }

    public Connection getConnection() {
        return c;
    }

    @Override
    public void close() throws SQLException {
        // Close the resources when the Conn object is closed
        if (s != null) {
            s.close();
        }
        if (c != null) {
            c.close();
        }
    }
}
