package com.workshop.springboot.workshopspringboot.service;

import com.workshop.springboot.workshopspringboot.entity.Pendaftaran;
import com.workshop.springboot.workshopspringboot.entity.Peserta;
import com.workshop.springboot.workshopspringboot.entity.Tagihan;
import com.workshop.springboot.workshopspringboot.dto.request.DokuHostedRequestDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DokuService {
    @Value("${doku.merchantid}")
    private String merchantId;

    @Value("${doku.sharedkey}")
    private String sharedKey;

    @Value("${doku.url}")
    private String dokuUrl;

    private DecimalFormat decFormat = new DecimalFormat("0.00");

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public String getDokuUrl(){
        return dokuUrl;
    }

    public DokuHostedRequestDTO createDokuRequest(Tagihan tagihan){
        Peserta p = tagihan.getPendaftaran().getPeserta();
        Pendaftaran pendaf = tagihan.getPendaftaran();

        String keterangan = "Pembayaran materi" + pendaf.getMateri() + " - " + pendaf.getPeserta();
        DokuHostedRequestDTO request = DokuHostedRequestDTO.builder().NAME(p.getNama())
                .EMAIL(p.getEmail()).MALLID(merchantId).CHAINMERCHANT("NA")
                .AMOUNT(decFormat.format(pendaf.getMateri().getBiaya())).PURCHASEAMOUNT(decFormat.format(pendaf.getMateri().getBiaya()))
                .TRANSIDMERCHANT(tagihan.getNomorInvoice()).PAYMENTCHANNEL("").REQUESTDATETIME(dateFormat.format(new Date()))
                .CURRENCY("360").PURCHASECURRENCY("360").SESSIONID("1234567890").BASKET(tagihan.getKeterangan()).build();

        String words = DigestUtils.sha1Hex(request.getAMOUNT() + request.getMALLID() + sharedKey + request.getTRANSIDMERCHANT());
        request.setWORDS(words);

        return request;
    }

}
