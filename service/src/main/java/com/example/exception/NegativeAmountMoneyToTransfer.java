package com.example.exception;

import com.example.MainApiException;

/**
 * Created by remote on 7/23/17.
 */
public class NegativeAmountMoneyToTransfer extends MainApiException {

    public NegativeAmountMoneyToTransfer() {
        super(400, "NegativeAmountMoneyToTransfer");
    }

}
