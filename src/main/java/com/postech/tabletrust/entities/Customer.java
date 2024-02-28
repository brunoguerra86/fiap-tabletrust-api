package com.postech.tabletrust.entities;

import com.postech.tabletrust.dto.CustomerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@Entity
@Table(name = "tb_Customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;

    public Customer(CustomerDTO CustomerDTO) {
        this.id = CustomerDTO.getId() == null ? this.id : UUID.fromString(CustomerDTO.getId());
        this.nome = CustomerDTO.getNome();
    }
}
