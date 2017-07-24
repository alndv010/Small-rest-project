package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by remote on 7/23/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountData {
    private String accountId;
    private Double balance;
    private String description;
}
