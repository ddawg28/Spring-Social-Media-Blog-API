package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import com.example.entity.*;
import com.example.repository.AccountRepository;


@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account createAccount(Account account) {
        if (account.getUsername().isBlank()) {
            return null;
        }
        if (account.getPassword().length() < 4) {
            return null;
        }
        accountRepository.save(account);
        return accountRepository.findByUsername(account.getUsername()).get();
    }

    public boolean findAccount(Account account) {
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return true;
        }
        return false;
    }
}
