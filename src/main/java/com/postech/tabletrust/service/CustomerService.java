package com.postech.tabletrust.service;
import com.postech.tabletrust.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer createCustomer(Customer Customer);
    Customer findCustomer(UUID CustomerUUID);
    Customer updateCustomer(Customer newCustomer);
    void deleteCustomer(UUID id);
    List<Customer> listAllCustomers();
}
