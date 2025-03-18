package lk.ijse.dep13.backend.business;

import lk.ijse.dep13.backend.data.CustomerDataAccess;
import lk.ijse.dep13.backend.entity.Customer;
import lk.ijse.dep13.backend.to.CustomerTo;

import java.io.*;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CustomerBusinessLogic {

    public List<CustomerTo<String>> getAllCustomers(Connection connection) {
        return CustomerDataAccess.getAllCustomers(connection)
                .stream()
                .map(c -> new CustomerTo<>("C%03d".formatted(c.id()),
                        c.name(), c.address(), c.profilePicture()))
                .toList();
    }

    public CustomerTo<String> saveCustomer(Connection connection, String uploadDir, CustomerTo<InputStream> customer) {
        File profilePictureFile = new File(uploadDir, UUID.randomUUID().toString());

        try (FileOutputStream fos = new FileOutputStream(profilePictureFile)) {
            customer.getProfilePicture().transferTo(fos);

            Customer newCustomer = new Customer(null, customer.getName(),
                    customer.getAddress(), profilePictureFile.getName());
            try {
                int newId = CustomerDataAccess.saveCustomer(connection, newCustomer);

                return new CustomerTo<>("C%03d".formatted(newId),
                        customer.getName(), customer.getAddress(), profilePictureFile.getName());
            }catch (Exception e){
                profilePictureFile.delete();
                throw e;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteCustomer(Connection connection, String uploadDir, String id) {
        int customerId = Integer.parseInt(id.replace("C", ""));
        String profilePic = CustomerDataAccess.getProfilePic(connection, customerId);
        if (CustomerDataAccess.deleteCustomer(connection,customerId)){
            return new File(uploadDir, Objects.requireNonNull(profilePic)).delete();
        }
        return false;
    }
}
