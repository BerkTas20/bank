package com.bank.bank.controller;

import com.bank.bank.dto.AccountDto;
import com.bank.bank.dto.CreateAccountRequest;
import com.bank.bank.dto.MoneyTransferRequest;
import com.bank.bank.dto.UpdateAccountRequest;
import com.bank.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return ResponseEntity.ok(accountService.createAccount(createAccountRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable String id, @RequestBody UpdateAccountRequest updateAccountRequest) {
        return ResponseEntity.ok(accountService.updateAccount(id,updateAccountRequest));
    }

    @DeleteMapping("/withdraw/{id}/{amount}")
    public ResponseEntity<Void>deleteAccount(@PathVariable String id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/add/{id}/{amount}")
    public ResponseEntity<AccountDto> withdrawMoney(@PathVariable String id, @PathVariable Double amount){
        return ResponseEntity.ok(accountService.addMoney(id,amount));
    }


    public ResponseEntity<String> transferMoney(@RequestBody MoneyTransferRequest moneyTransferRequest){
        accountService.transferMoney(moneyTransferRequest);
        return ResponseEntity.ok("İşleminiz başarıyla alındı!");
    }
}

//HTTP PATCH Nedir = Bir modelin sadece bir alanını değiştireceksin.Bir güncelle işlemi