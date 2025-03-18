package lk.ijse.dep13.backend.business;

import lk.ijse.dep13.backend.entity.Customer;
import lk.ijse.dep13.backend.to.CustomerTo;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

public class CustomerBusinessLogic {

    public List<CustomerTo<String>> getAllCustomers(Connection connection) {
        return null;
    }

    public CustomerTo<String> saveCustomer(Connection connection, CustomerTo<InputStream> customer) {
        return null;
    }

    public boolean deleteCustomer(Connection connection, String id) {
        return false;
    }
}
