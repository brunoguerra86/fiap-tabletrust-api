package com.postech.tabletrust.controller;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.gateways.CustomerGateway;
import com.postech.tabletrust.usecases.CustomerUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerGateway customerGateway;

    @PostMapping("")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO Customer) {
        log.info("create Customer for customer [{}]", Customer.getNome());
        try {
            CustomerDTO CustomerNew = customerGateway.findCustomer(Customer.getId());
            CustomerUseCase.validarInsertCustomer(Customer, CustomerNew);
            CustomerDTO CustomerCreated = customerGateway.createCustomer(Customer);
            return new ResponseEntity<>(CustomerCreated, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // ?
        }
    }

    @PutMapping("/id={id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @RequestBody @Valid CustomerDTO CustomerNew) {
        log.info("PutMapping - updateCustomer");
        try {
            CustomerDTO CustomerOld = customerGateway.findCustomer(id);
            CustomerUseCase.validarUpdateCliente(id, CustomerOld, CustomerNew);
            CustomerDTO newCustomer = customerGateway.updateCustomer(CustomerNew);
            return new ResponseEntity<>(newCustomer, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        log.info("DeleteMapping - deleteCustomer");
        try {
            customerGateway.deleteCustomer(id);
            return new ResponseEntity<>("cliente removido", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> listAllCustomers() {
        log.info("GetMapping - listCustomers");
        List<CustomerDTO> Customers = customerGateway.listAllCustomers();
        return new ResponseEntity<>(Customers, HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> findCustomer(@PathVariable String id) {
        log.info("GetMapping - FindCustomer  ");
        try {
            CustomerDTO Customer = customerGateway.findCustomer(id);
            return new ResponseEntity<>(Customer, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

