package com.bank.bank.service;

import com.bank.bank.dto.*;
import com.bank.bank.model.Account;
import com.bank.bank.model.Customer;
import com.bank.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
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

    public AccountDto updateAccount(String id,UpdateAccountRequest updateAccountRequest) {
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
    //para ekleme
    public AccountDto addMoney(String id, Double amount){
        Optional<Account>accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
                account.setBalance(account.getBalance() + amount);
                accountRepository.save(account);

        });
        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }

    public void transferMoney(MoneyTransferRequest moneyTransferRequest){

    }
}
