package com.postech.tabletrust.dto;

import com.postech.tabletrust.entity.Customer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.stream.Collectors;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class CustomerDTO {
    private String id;
    @NotNull(message = "O nome não pode ser nulo.")
    private String nome;

    public CustomerDTO() {
    }
    public CustomerDTO(Customer Customer) {
        if (Customer != null) {
            this.id = Customer.getId().toString();
            this.nome = Customer.getNome();
        }
    }
    public List<CustomerDTO> toList(List<Customer> customerList) {
        return customerList
                .stream()
                .map(CustomerDTO::new)
                .collect(Collectors.toList());
    }
}