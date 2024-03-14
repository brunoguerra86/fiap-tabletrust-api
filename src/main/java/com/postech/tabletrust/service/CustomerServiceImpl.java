package com.postech.tabletrust.service;

import com.postech.tabletrust.repository.CustomerRepository;
import com.postech.tabletrust.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository CustomerRepository;

    @Override
    public Customer createCustomer(Customer Customer) {
        return CustomerRepository.save(Customer);
    }

    @Override
    public Customer updateCustomer(Customer newCustomer) {
        return CustomerRepository.save(newCustomer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        Customer Customer = findCustomer(id);
        CustomerRepository.delete(Customer);
    }

    @Override
    public Customer findCustomer(UUID id) {
        return CustomerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("cliente não encontrado"));
    }

    @Override
    public List<Customer> listAllCustomers() {
        return CustomerRepository.findAll();
    }
}
