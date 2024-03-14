package com.postech.tabletrust.controller;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.entities.Customer;
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
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("create Customer for customer [{}]", customerDTO.getNome());
        try {
            Customer customerOld = customerGateway.findCustomer(customerDTO.getId());
            CustomerUseCase.validarInsertCustomer(customerDTO, customerOld);
            CustomerDTO CustomerCreated = customerGateway.createCustomer(customerDTO);
            return new ResponseEntity<>(CustomerCreated, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // ?
        }
    }

    @PutMapping("/id={id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @RequestBody @Valid CustomerDTO customerNew) {
        log.info("PutMapping - updateCustomer");
        try {
            Customer customerOld = customerGateway.findCustomer(id);
            CustomerUseCase.validarUpdateCliente(id, customerOld, customerNew);
            CustomerDTO newCustomer = customerGateway.updateCustomer(customerNew);
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
            Customer Customer = customerGateway.findCustomer(id);
            return new ResponseEntity<>(Customer, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

