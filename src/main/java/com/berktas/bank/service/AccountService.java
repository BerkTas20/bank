package com.berktas.bank.service;

import com.berktas.bank.controller.requests.CreateAccountRequest;
import com.berktas.bank.controller.requests.MoneyTransferRequest;
import com.berktas.bank.controller.requests.UpdateAccountRequest;
import com.berktas.bank.dto.*;
import com.berktas.bank.mapper.AccountDtoConverter;
import com.berktas.bank.model.Account;
import com.berktas.bank.model.Customer;
import com.berktas.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private final CustomerService customerService;

    private final AccountDtoConverter accountDtoConverter;

    private final AmqpTemplate rabbitTemplate;

    private final DirectExchange directExchange;

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;

    @Value("${sample.rabbitmq.queue}")
    String queueName;

    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        Customer customer = customerService.getCustomerById(createAccountRequest.getCustomerId());
        if (customer.getId() == "" || customer.getId() == null) {
            return AccountDto.builder().build();
        }
        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();
        return accountDtoConverter.convert(accountRepository.save(account));
    }

    public AccountDto updateAccount(String id, UpdateAccountRequest updateAccountRequest) {
        Customer customer = customerService.getCustomerById(updateAccountRequest.getCustomerId());
        if (customer.getId() == "" || customer.getId() == null) {
            return AccountDto.builder().build();
        }
        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
            account.setBalance(updateAccountRequest.getBalance());
            account.setCity(updateAccountRequest.getCity());
            account.setCurrency(updateAccountRequest.getCurrency());
            account.setCustomerId(updateAccountRequest.getCustomerId());
            accountRepository.save(account);
        });
        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }

    public List<AccountDto> getAllAccounts(){
        List<Account> accountList = accountRepository.findAll();

        return accountList.stream().map(accountDtoConverter::convert).collect(Collectors.toList());
    }

    public AccountDto getAccountById(String id){
        return accountRepository.findById(id).map(accountDtoConverter::convert).orElse(new AccountDto());
    }

    public void deleteAccount(String id){
        accountRepository.deleteById(id);
    }

    public AccountDto WithdrawMoney(String id, Double amount){
        Optional<Account>accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
            if(account.getBalance() > amount){
                account.setBalance(account.getBalance() - amount);
                accountRepository.save(account);
            }else{
                System.out.println("Insufficent funds -> accountId: " + id + "balance: "+ account.getBalance() + "amount: " + amount);
            }
        });
        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }

    public AccountDto addMoney(String id, Double amount){
        Optional<Account>accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
                account.setBalance(account.getBalance() + amount);
                accountRepository.save(account);

        });
        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }

    public void transferMoney(MoneyTransferRequest moneyTransferRequest){
        rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, moneyTransferRequest);
    }
}
