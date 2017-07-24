package com.example;

import com.example.dao.AccountDAO;
import com.example.model.AccountData;
import com.google.inject.Inject;

import java.util.UUID;


/**
 * Created by remote on 7/23/17.
 */
public class AccountService {
    @Inject
    private AccountDAO accountDAO;

    public AccountData getAccount(String accountId) throws AccountNotFound {
        AccountData accountData = accountDAO.findById(accountId);
        if (accountData == null) {
            throw new AccountNotFound(accountId);
        }
        return accountData;
    }

    public void createAccount(AccountData accountRecord){
        if (accountRecord.getAccountId() == null) {
            accountRecord.setAccountId(UUID.randomUUID().toString());
        }
        accountDAO.insert(accountRecord);
    }

    public void updateAccount(AccountData accountRecord){
        accountDAO.update(accountRecord);
    }
}
