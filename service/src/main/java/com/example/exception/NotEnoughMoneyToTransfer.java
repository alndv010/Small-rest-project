package com.example.exception;

import com.example.MainApiException;

/**
 * Created by remote on 7/23/17.
 */
public class NotEnoughMoneyToTransfer extends MainApiException {

    public NotEnoughMoneyToTransfer() {
        super(400, "NotEnoughMoneyToTransfer");
    }
}
