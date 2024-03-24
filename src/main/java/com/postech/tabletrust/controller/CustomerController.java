package com.postech.tabletrust.controller;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.entity.Customer;
import com.postech.tabletrust.exception.NotFoundException;
import com.postech.tabletrust.gateways.CustomerGateway;
import com.postech.tabletrust.usecases.CustomerUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerGateway customerGateway;

    @PostMapping("")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("PostMapping - createCustomer for customer [{}]", customerDTO.getNome());
        try {
            Customer customerOld = customerGateway.findCustomer(customerDTO.getId());
            Customer customerNew = new Customer(customerDTO);
            CustomerUseCase.validarInsertCustomer(customerNew, customerOld);
            Customer customerCreated = customerGateway.createCustomer(customerNew);
            return new ResponseEntity<>(customerCreated, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @RequestBody @Valid CustomerDTO customerDTO) {
        log.info("PutMapping - updateCustomer");
        try {
            Customer customerOld = customerGateway.findCustomer(id);
            Customer customerNew = new Customer(customerDTO);
            CustomerUseCase.validarUpdateCliente(id, customerOld, customerNew);
            customerNew = customerGateway.updateCustomer(customerNew);
            return new ResponseEntity<>(customerNew, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        log.info("DeleteMapping - deleteCustomer");
        try {
            Customer customer = customerGateway.findCustomer(id);
            CustomerUseCase.validarDeleteCliente(customer);
            customerGateway.deleteCustomer(id);
            return new ResponseEntity<>("Cliente removido.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> listAllCustomers() {
        log.info("GetMapping - listCustomers");
        List<Customer> customers = customerGateway.listAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCustomer(@PathVariable String id) {
        log.info("GetMapping - FindCustomer");
        Customer customer = customerGateway.findCustomer(id);
        return new ResponseEntity<>((customer != null ? customer : "Cliente não encontrado."), HttpStatus.OK);
    }
}

