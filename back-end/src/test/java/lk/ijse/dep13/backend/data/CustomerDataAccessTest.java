package lk.ijse.dep13.backend.data;

import lk.ijse.dep13.backend.to.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

// Test Suite
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class CustomerDataAccessTest {

    Connection connection;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH",
                "", "");
        executeScripts();
    }

    private void executeScripts(){
        try(BufferedReader schemeBr = new BufferedReader(new InputStreamReader
                (Objects.requireNonNull(getClass().getResourceAsStream("/schema.sql"))))){
            StringBuilder schemaScript = new StringBuilder();
            schemeBr.lines().forEach(schemaScript::append);
            try(var stm = connection.createStatement()){
                stm.execute(schemaScript.toString());
            }

            Path path = Path.of(Objects.requireNonNull(getClass().getResource("/data.sql")).toURI());
            String dataScript = Files.readString(path);
            try(var stm = connection.createStatement()){
                stm.execute(dataScript);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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