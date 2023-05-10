package com.berktas.bank.service;

import com.berktas.bank.dto.AccountDto;
import com.berktas.bank.mapper.AccountDtoConverter;
import com.berktas.bank.controller.requests.CreateAccountRequest;
import com.berktas.bank.model.*;
import com.berktas.bank.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;

public class AccountServiceTest {
    @Mock
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private AccountDtoConverter accountDtoConverter;

    private DirectExchange directExchange;

    private AmqpTemplate rabbitTemplate;

    @Before
    public void setUp() throws Exception {
        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);
        directExchange = Mockito.mock(DirectExchange.class);
        rabbitTemplate = Mockito.mock(AmqpTemplate.class);

        accountService = new AccountService(accountRepository,
                customerService,
                accountDtoConverter, rabbitTemplate, directExchange);
    }

    @Test
    public void whenCreateAccountCalledWithValidRequest_itShouldReturnValidAccountDto(){
        CreateAccountRequest createAccountRequest = generateCreateAccountRequest();
        Customer customer = generateCustomer();
        Account account = generateAccount(createAccountRequest);
        AccountDto accountDto = generateAccountDto();


        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);

        AccountDto result = accountService.createAccount(createAccountRequest);
        Assert.assertEquals(result, accountDto);
        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);
    }

    private CreateAccountRequest generateCreateAccountRequest(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);
        return createAccountRequest;
    }
    private Customer generateCustomer() {
        return Customer.builder()
                .id("12345")
                .address(Address.builder().city(City.ISTANBUL).postcode("456312").addressDetails("bu bir adrestir").build())
                .city(City.ISTANBUL)
                .dateOfBirth(1998)
                .name("Muratcan")
                .build();
    }

    private Account generateAccount(CreateAccountRequest accountRequest) {
        return Account.builder()
                .id(accountRequest.getId())
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .customerId(accountRequest.getCustomerId())
                .city(accountRequest.getCity())
                .build();
    }

    private AccountDto generateAccountDto() {
        return AccountDto.builder()
                .id("1234")
                .customerId("12345")
                .currency(Currency.TRY)
                .balance(100.0)
                .build();
    }
}