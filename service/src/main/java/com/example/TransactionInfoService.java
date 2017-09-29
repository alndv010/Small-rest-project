package com.example;

import com.example.exception.AccountNotFound;
import com.example.exception.FromAndToAccountsCoincide;
import com.example.exception.NegativeAmountMoneyToTransfer;
import com.example.exception.NotEnoughMoneyToTransfer;
import com.example.repository.TransactionInfoRepository;
import com.example.model.AccountData;
import com.example.model.TransactionInfoData;
import com.google.inject.Inject;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by remote on 7/23/17.
 */
public class TransactionInfoService {
    @Inject
    private TransactionInfoRepository transactionInfoRepository;

    @Inject
    private AccountService accountService;

    @Transactional
    public void createTransactionInfo(TransactionInfoData transactionInfoData)
            throws NotEnoughMoneyToTransfer, NegativeAmountMoneyToTransfer, AccountNotFound, FromAndToAccountsCoincide {
        if (transactionInfoData.getTransactionInfoId() == null) {
            transactionInfoData.setTransactionInfoId(UUID.randomUUID().toString());
        }

        final AccountData from = accountService.getAccount(transactionInfoData.getFromAccountId());
        final AccountData to = accountService.getAccount(transactionInfoData.getToAccountId());

        validateTransaction(transactionInfoData, from);
        //initial balances and transfer amounts
        BigDecimal fromAccountBalance = BigDecimal.valueOf(from.getBalance());
        BigDecimal toAccountBalance = BigDecimal.valueOf(to.getBalance());
        BigDecimal transferAmount = BigDecimal.valueOf(transactionInfoData.getAmount());
        //calculating resulting balances
        fromAccountBalance = fromAccountBalance.subtract(transferAmount);
        toAccountBalance = toAccountBalance.add(transferAmount);
        //updating balances
        from.setBalance(fromAccountBalance.doubleValue());
        to.setBalance(toAccountBalance.doubleValue());
        //saving results
        transactionInfoRepository.insert(transactionInfoData);
        accountService.updateAccount(from);
        accountService.updateAccount(to);
    }

    private void validateTransaction(TransactionInfoData transactionInfoData, AccountData from) throws NegativeAmountMoneyToTransfer, NotEnoughMoneyToTransfer, FromAndToAccountsCoincide {
        if (transactionInfoData.getAmount() <= 0) {
            throw new NegativeAmountMoneyToTransfer();
        } else if (from.getBalance() < transactionInfoData.getAmount()) {
            throw new NotEnoughMoneyToTransfer();
        } else if (transactionInfoData.getFromAccountId().equals(transactionInfoData.getToAccountId())) {
            throw new FromAndToAccountsCoincide();
        }
    }

    public List<TransactionInfoData> getAccountTransactionInfos(String accountId){
        return transactionInfoRepository.getAccountTransactionInfos(accountId);
    }


}
