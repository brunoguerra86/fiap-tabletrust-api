package com.postech.tabletrust.gateways;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.entities.Customer;
import com.postech.tabletrust.service.CustomerService;
import com.postech.tabletrust.interfaces.ICustomerGateway;

import java.util.List;
import java.util.UUID;

public class CustomerGateway implements ICustomerGateway {
    private final CustomerService CustomerService;

    public CustomerGateway(CustomerService CustomerService) {
        this.CustomerService = CustomerService;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO CustomerDTO) {

        Customer CustomerEntity = new Customer(CustomerDTO);
        CustomerEntity = CustomerService.createCustomer(CustomerEntity);
        return new CustomerDTO(CustomerEntity);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO CustomerDTO) {
        Customer CustomerEntity = new Customer(CustomerDTO);
        CustomerEntity = CustomerService.updateCustomer(CustomerEntity);
        return new CustomerDTO(CustomerEntity);
    }

    @Override
    public void deleteCustomer(String strId) {
        UUID uuid = UUID.fromString(strId);
        CustomerService.deleteCustomer(uuid);
    }

    @Override
    public CustomerDTO findCustomer(String strId) {
        if (strId == null) {
            return new CustomerDTO();
        }
        UUID uuid = UUID.fromString(strId);
        return new CustomerDTO(CustomerService.findCustomer(uuid));
    }

    @Override
    public List<CustomerDTO> listAllCustomers() {
        List<Customer> CustomerEntityList = CustomerService.listAllCustomers();
        return new CustomerDTO().toList(CustomerEntityList);
    }
}
