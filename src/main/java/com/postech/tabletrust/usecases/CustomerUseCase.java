package com.postech.tabletrust.usecases;

import com.postech.tabletrust.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerUseCase {

    private static void validarCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
    }

    public static void validarInsertCustomer(Customer customerNew, Customer customerOld) {
        validarCustomer(customerNew);
        if (customerOld!= null) {
            throw new IllegalArgumentException("Cliente já existe.");
        }
    }

    public static void validarUpdateCliente(String strId, Customer customerToUpdate, Customer customerNew) {

        validarCustomer(customerNew);
        if (!customerNew.getId().toString().equals(strId)) {
            throw new IllegalArgumentException("ID do Cliente incorreto.");
        }
        if (customerToUpdate == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
    }
    public static void validarDeleteCliente(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
    }
}
