package com.postech.tabletrust.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID")
    private UUID id;

    @NotBlank(message = "O login não pode estar em branco.")
    @Size(max = 124, message = "O login deve ter no máximo 124 caracteres.")
    private String login;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(max = 60, message = "A senha deve ter no máximo 60 caracteres.")
    private String password;

    @Size(max = 60, message = "O sobrenome deve ter no máximo 60 caracteres.")
    private String lastname;

    @Size(max = 60, message = "O nome deve ter no máximo 60 caracteres.")
    private String firstname;

    @NotBlank(message = "O e-mail não pode estar em branco.")
    @Email(message = "E-mail inválido.")
    @Size(max = 124, message = "O e-mail deve ter no máximo 124 caracteres.")
    private String email;

    @Size(max = 24, message = "O telefone deve ter no máximo 24 caracteres.")
    private String phone;
}