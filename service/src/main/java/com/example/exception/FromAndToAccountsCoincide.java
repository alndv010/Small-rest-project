package com.example.exception;

import com.example.MainApiException;

/**
 * Created by remote on 7/23/17.
 */
public class FromAndToAccountsCoincide extends MainApiException {

    public FromAndToAccountsCoincide() {
        super(400, "FromAndToAccountsCoincide");
    }
}
