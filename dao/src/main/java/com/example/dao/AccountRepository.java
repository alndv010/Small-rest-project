package com.example.dao;

import com.example.model.AccountData;
import com.google.inject.Inject;
import org.jooq.Configuration;
import org.jooq.example.flyway.j.db.h2.tables.Account;
import org.jooq.example.flyway.j.db.h2.tables.records.AccountRecord;
import org.jooq.impl.DAOImpl;


/**
 * Created by remote on 7/22/17.
 */
public class AccountDAO extends DAOImpl<AccountRecord, AccountData, String> {

    @Inject
    public AccountDAO(Configuration configuration) {
        super(Account.ACCOUNT, AccountData.class, configuration);
    }

    protected String getId(AccountData accountRecord) {
        return accountRecord.getAccountId();
    }

}
