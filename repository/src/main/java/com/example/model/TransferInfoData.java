package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by remote on 7/23/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferInfoData {
    private String transferInfoId;
    private Double amount;
    private String fromAccountId;
    private String toAccountId;
}
