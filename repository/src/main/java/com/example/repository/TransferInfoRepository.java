package com.example.repository;

import com.example.model.TransferInfoData;
import com.google.inject.Inject;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.example.flyway.j.db.h2.tables.TransferInfo;
import org.jooq.example.flyway.j.db.h2.tables.records.TransferInfoRecord;
import org.jooq.impl.DAOImpl;

import java.util.List;


/**
 * Created by remote on 7/22/17.
 */
public class TransferInfoRepository extends DAOImpl<TransferInfoRecord, TransferInfoData, String> {

    private final DSLContext context;

    @Inject
    public TransferInfoRepository(DSLContext dsl, Configuration configuration) {
        super(TransferInfo.TRANSFER_INFO, TransferInfoData.class, configuration);
        this.context = dsl;
    }

    protected String getId(TransferInfoData transferInfoRecord) {
        return transferInfoRecord.getTransferInfoId();
    }

    public List<TransferInfoData> getAccountTransferInfos(String accountId){
        return context.selectFrom(TransferInfo.TRANSFER_INFO)
                .where(TransferInfo.TRANSFER_INFO.FROM_ACCOUNT_ID.eq(accountId))
                .or(TransferInfo.TRANSFER_INFO.TO_ACCOUNT_ID.eq(accountId))
                .orderBy(TransferInfo.TRANSFER_INFO.CREATED_AT.desc())
                .fetch()
                .map(mapper());
    }
}
