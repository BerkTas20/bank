package com.bank.bank.service;

import com.bank.bank.dto.*;
import com.bank.bank.model.City;
import com.bank.bank.model.Customer;
import com.bank.bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerDto createCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer();
        customer.setId(createCustomerRequest.getId());
        customer.setAddress(createCustomerRequest.getAddress());
        customer.setName(createCustomerRequest.getName());
        customer.setDateOfBirth(createCustomerRequest.getDateOfBirth());
        customer.setCity(City.valueOf(createCustomerRequest.getCity().name()));

        customerRepository.save(customer);
        return customerDtoConverter.convert(customer);
    }


    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        List<CustomerDto> customerDtoList = new ArrayList<>();
        for(Customer customer: customerList){
            customerDtoList.add(customerDtoConverter.convert(customer));
        }
        return customerDtoList;
    }
//Optional = içi boş ya da dolu olan özel bir veri yapısıdır
    public CustomerDto getCustomerDtoById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map( customerDtoConverter::convert).orElse(new CustomerDto());
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    public CustomerDto updateCustomer(String id, UpdateCustomerRequest customerRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        customerOptional.ifPresent(customer -> {
            customer.setName(customerRequest.getName());
            customer.setCity(City.valueOf(customerRequest.getCity().name()));
            customer.setDateOfBirth(customerRequest.getDateOfBirth());
            customer.setAddress(customerRequest.getAddress());
            customerRepository.save(customer);
        });
        return customerOptional.map( customerDtoConverter::convert).orElse(new CustomerDto());
    }

    protected Customer getCustomerById(String id){ //sadece bu servis erişsin diye protected yazdık.Diğer sınıflar erişemez.
        return customerRepository.findById(id).orElse(new Customer());
    }
}

