package com.postech.tabletrust.usecases;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerUseCase {


    private static void validarCustomer(CustomerDTO CustomerDTO) {

        if (CustomerDTO == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
    }

    public static void validarInsertCustomer(CustomerDTO CustomerDTO, Customer CustomerNew) {

        validarCustomer(CustomerDTO);
        if (CustomerNew.getId() != null) {
            throw new IllegalArgumentException("Cliente já existe");
        }
    }

    public static void validarUpdateCliente(String strId, Customer CustomerDTOOld, CustomerDTO CustomerDTONew) {

        validarCustomer(CustomerDTONew);
        if (!CustomerDTONew.getId().equals(strId)) {
            throw new IllegalArgumentException("ID do Cliente incorreto");
        }
        if (CustomerDTOOld == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

    }

}
