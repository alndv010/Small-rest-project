package com.example;

import com.example.exception.AccountNotFound;
import com.example.exception.FromAndToAccountsCoincide;
import com.example.exception.NegativeAmountMoneyToTransfer;
import com.example.exception.NotEnoughMoneyToTransfer;
import com.example.repository.TransferInfoRepository;
import com.example.model.AccountData;
import com.example.model.TransferInfoData;
import com.google.inject.Inject;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by remote on 7/23/17.
 */
public class TransferInfoService {
    @Inject
    private TransferInfoRepository transferInfoRepository;

    @Inject
    private AccountService accountService;

    @Transactional
    public void createTransferInfo(TransferInfoData transferInfoData)
            throws NotEnoughMoneyToTransfer, NegativeAmountMoneyToTransfer, AccountNotFound, FromAndToAccountsCoincide {
        if (transferInfoData.getTransferInfoId() == null) {
            transferInfoData.setTransferInfoId(UUID.randomUUID().toString());
        }

        final AccountData from = accountService.getAccount(transferInfoData.getFromAccountId());
        final AccountData to = accountService.getAccount(transferInfoData.getToAccountId());

        validateTransfer(transferInfoData, from);
        //initial balances and transfer amounts
        BigDecimal fromAccountBalance = BigDecimal.valueOf(from.getBalance());
        BigDecimal toAccountBalance = BigDecimal.valueOf(to.getBalance());
        BigDecimal transferAmount = BigDecimal.valueOf(transferInfoData.getAmount());
        //calculating resulting balances
        fromAccountBalance = fromAccountBalance.subtract(transferAmount);
        toAccountBalance = toAccountBalance.add(transferAmount);
        //updating balances
        from.setBalance(fromAccountBalance.doubleValue());
        to.setBalance(toAccountBalance.doubleValue());
        //saving results
        transferInfoRepository.insert(transferInfoData);
        accountService.updateAccount(from);
        accountService.updateAccount(to);
    }

    private void validateTransfer(TransferInfoData transferInfoData, AccountData from) throws NegativeAmountMoneyToTransfer, NotEnoughMoneyToTransfer, FromAndToAccountsCoincide {
        if (transferInfoData.getAmount() <= 0) {
            throw new NegativeAmountMoneyToTransfer();
        } else if (from.getBalance() < transferInfoData.getAmount()) {
            throw new NotEnoughMoneyToTransfer();
        } else if (transferInfoData.getFromAccountId().equals(transferInfoData.getToAccountId())) {
            throw new FromAndToAccountsCoincide();
        }
    }

    public List<TransferInfoData> getAccountTransferInfos(String accountId){
        return transferInfoRepository.getAccountTransferInfos(accountId);
    }


}
