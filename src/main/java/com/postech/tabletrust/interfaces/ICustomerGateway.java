package com.postech.tabletrust.interfaces;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.entity.Customer;

import java.util.List;

public interface ICustomerGateway {
    public CustomerDTO createCustomer(CustomerDTO Customer);

    public CustomerDTO updateCustomer(CustomerDTO Customer);

    public void deleteCustomer(String strId);

    public Customer findCustomer(String strId);

    public List<CustomerDTO> listAllCustomers();
}
