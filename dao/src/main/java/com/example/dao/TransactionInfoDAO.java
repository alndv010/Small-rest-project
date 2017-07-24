package com.example.dao;

import com.example.model.TransactionInfoData;
import com.google.inject.Inject;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.example.flyway.j.db.h2.tables.TransactionInfo;
import org.jooq.example.flyway.j.db.h2.tables.records.TransactionInfoRecord;
import org.jooq.impl.DAOImpl;

import java.util.List;


/**
 * Created by remote on 7/22/17.
 */
public class TransactionInfoDAO extends DAOImpl<TransactionInfoRecord, TransactionInfoData, String> {

    private final DSLContext context;

    @Inject
    public TransactionInfoDAO(DSLContext dsl, Configuration configuration) {
        super(TransactionInfo.TRANSACTION_INFO, TransactionInfoData.class, configuration);
        this.context = dsl;
    }

    protected String getId(TransactionInfoData transactionInfoRecord) {
        return transactionInfoRecord.getTransactionInfoId();
    }

    public List<TransactionInfoData> getAccountTransactionInfos(String accountId){
        return context.selectFrom(TransactionInfo.TRANSACTION_INFO)
                .where(TransactionInfo.TRANSACTION_INFO.FROM_ACCOUNT_ID.eq(accountId))
                .or(TransactionInfo.TRANSACTION_INFO.TO_ACCOUNT_ID.eq(accountId))
                .orderBy(TransactionInfo.TRANSACTION_INFO.CREATED_AT.desc())
                .fetch()
                .map(mapper());
    }
}
