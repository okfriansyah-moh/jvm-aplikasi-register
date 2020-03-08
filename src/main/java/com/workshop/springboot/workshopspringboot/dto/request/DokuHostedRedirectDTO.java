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
public class DokuHostedRedirectDTO {
    private BigDecimal AMOUNT;
    private String TRANSIDMERCHANT;
    private String WORDS;
    private String STATUSCODE;
    private String RESPONSECODE;
    private String PAYMENTCHANNEL;
    private String SESSIONID;
    private String PAYMENTCODE;
    private String CURRENCY;
    private String PURCHASECURRENCY;
    private String RESULTMSG;
}
