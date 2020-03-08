package com.workshop.springboot.workshopspringboot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DokuHostedNotifyDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DokuHostedIdentifyDTO {
    private BigDecimal AMOUNT;
    private String TRANSIDMERCHANT;
    private String PAYMENTCHANNEL;
    private String SESSIONID;
}
