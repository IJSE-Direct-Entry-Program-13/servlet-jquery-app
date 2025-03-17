package lk.ijse.dep13.backend.data;

import lk.ijse.dep13.backend.to.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test Suite
class CustomerDataAccessTest {

    Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dep13_customers_db",
                "postgres", "psql");
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    // Test Case
    @Test
    void getAllCustomers() {
        // 2. Exercise (SUT = System Under Test)
        List<Customer> customerList = CustomerDataAccess.getAllCustomers(connection);

        // 3. Verify (State)
        assertFalse(customerList.isEmpty());
        customerList.forEach(System.out::println);
    }

    // Test Case
    @Test
    void saveCustomer() {
    }

    // Test Case
    @Test
    void deleteCustomer() {
    }
}