package com.berktas.bank.mapper;

import com.berktas.bank.dto.CityDto;
import com.berktas.bank.dto.CustomerDto;
import com.berktas.bank.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoConverter {


    public CustomerDto convert(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setAddress(customer.getAddress());
        customerDto.setCity(CityDto.valueOf(customer.getCity().name()));
        customerDto.setName(customer.getName());
        customerDto.setDateOfBirth(customer.getDateOfBirth());

        return customerDto;
    }
}
