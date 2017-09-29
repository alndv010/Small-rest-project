package com.example;

import com.example.exception.AccountNotFound;
import com.example.repository.AccountRepository;
import com.example.model.AccountData;
import com.google.inject.Inject;

import java.util.UUID;


/**
 * Created by remote on 7/23/17.
 */
public class AccountService {
    @Inject
    private AccountRepository accountRepository;

    public AccountData getAccount(String accountId) throws AccountNotFound {
        AccountData accountData = accountRepository.findById(accountId);
        if (accountData == null) {
            throw new AccountNotFound(accountId);
        }
        return accountData;
    }

    public void createAccount(AccountData accountRecord){
        if (accountRecord.getAccountId() == null) {
            accountRecord.setAccountId(UUID.randomUUID().toString());
        }
        accountRepository.insert(accountRecord);
    }

    public void updateAccount(AccountData accountRecord){
        accountRepository.update(accountRecord);
    }
}
