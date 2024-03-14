package com.postech.tabletrust.gateways;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.entities.Customer;
import com.postech.tabletrust.exception.NotFoundException;
import com.postech.tabletrust.interfaces.ICustomerGateway;
import com.postech.tabletrust.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CustomerGateway implements ICustomerGateway {
    private final CustomerRepository customerRepository;

    public CustomerGateway(CustomerRepository CustomerRepository) {
        this.customerRepository = CustomerRepository;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO CustomerDTO) {

        Customer CustomerEntity = new Customer(CustomerDTO);
        CustomerEntity = customerRepository.save(CustomerEntity);
        return new CustomerDTO(CustomerEntity);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO CustomerDTO) {
        Customer CustomerEntity = new Customer(CustomerDTO);
        CustomerEntity = customerRepository.save(CustomerEntity);
        return new CustomerDTO(CustomerEntity);
    }

    @Override
    public void deleteCustomer(String strId) {
        UUID uuid = UUID.fromString(strId);
        customerRepository.deleteById(uuid);
    }

    @Override
    public Customer findCustomer(String strId) {
        UUID uuid = UUID.fromString(strId);
        return customerRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }

    @Override
    public List<CustomerDTO> listAllCustomers() {
        List<Customer> CustomerEntityList = customerRepository.findAll();
        return new CustomerDTO().toList(CustomerEntityList);
    }
}
