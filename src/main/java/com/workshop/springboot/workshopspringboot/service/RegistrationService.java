package com.workshop.springboot.workshopspringboot.service;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.workshop.springboot.workshopspringboot.entity.Materi;
import com.workshop.springboot.workshopspringboot.entity.Pendaftaran;
import com.workshop.springboot.workshopspringboot.entity.Peserta;
import com.workshop.springboot.workshopspringboot.entity.Tagihan;
import com.workshop.springboot.workshopspringboot.entity.VerifikasiEmail;
import com.workshop.springboot.workshopspringboot.repository.PendaftaranRepository;
import com.workshop.springboot.workshopspringboot.repository.PesertaRepository;
import com.workshop.springboot.workshopspringboot.repository.TagihanRepository;
import com.workshop.springboot.workshopspringboot.repository.VerifikasiEmailRepository;
import com.workshop.springboot.workshopspringboot.dto.request.DokuHostedRequestDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class RegistrationService {
    @Value("${token.expiry.days}")
    private Integer tokenExpiryDays;

    @Value("${server.url}")
    private String serverUrl;

    @Value("${gmail.account.username}")
    private String mailFrom;

    @Autowired
    private PesertaRepository pesertaRepository;
    @Autowired private VerifikasiEmailRepository verifikasiEmailRepository;
    @Autowired private EmailService emailService;
    @Autowired private MustacheFactory mustacheFactory;
    @Autowired private DokuService dokuService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private PendaftaranRepository pendaftaranRepository;
    @Autowired private TagihanRepository tagihanRepository;


    public void registrasiPesertaBaru(Peserta p) {
        VerifikasiEmail ve = new VerifikasiEmail();
        ve.setPeserta(p);
        ve.setToken(UUID.randomUUID().toString());
        ve.setExpire(LocalDateTime.now().plusDays(tokenExpiryDays));
        String generatedPassword = RandomStringUtils.randomAlphanumeric(8);
        p.setPassword(passwordEncoder.encode(generatedPassword));

        pesertaRepository.save(p);
        verifikasiEmailRepository.save(ve);
        kirimVerifikasi(ve, generatedPassword);
    }
    private void kirimVerifikasi(VerifikasiEmail ve, String password) {
        Mustache templateEmail =
                mustacheFactory.compile("templates/notification/verification.html");
        Map<String, String> data = new HashMap<>();
        data.put("nama", ve.getPeserta().getNama());
        data.put("password", password);
        data.put("server.url", serverUrl);
        data.put("token", ve.getToken());

        StringWriter output = new StringWriter();
        templateEmail.execute(output, data);

        emailService.kirimEmail(
                mailFrom,
                ve.getPeserta().getEmail(),
                "Verifikasi Email "+ve.getPeserta().getNama(),
                output.toString());
    }

    public void verifikasiToken(String token) {
        VerifikasiEmail v = verifikasiEmailRepository.findByToken(token);
        if (v != null) {
            Peserta p = v.getPeserta();
            p.setEmailTerverifikasi(true);
            verifikasiEmailRepository.delete(v);
        }
    }

    public DokuHostedRequestDTO daftarWorkshop(Peserta p, Materi m) {
        Pendaftaran pendaftaran = new Pendaftaran();
        pendaftaran.setMateri(m);
        pendaftaran.setPeserta(p);
        pendaftaran.setSudahBayar(false);

        Tagihan tagihan = new Tagihan();
        tagihan.setPendaftaran(pendaftaran);
        tagihan.setNamaRekening(p.getNama());
        tagihan.setNomorInvoice(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                .format(LocalDateTime.now()));
        tagihan.setNomorRekening(tagihan.getNomorInvoice());
        tagihan.setBank("DOKU");
        tagihan.setKeterangan("Biaya workshop "+m.getNama());
        tagihan.setNilai(m.getBiaya());

        pendaftaranRepository.save(pendaftaran);
        tagihanRepository.save(tagihan);

        kirimTagihan(tagihan);
        return dokuService.createDokuRequest(tagihan);
    }

    private void kirimTagihan(Tagihan tagihan) {
        Mustache templateEmail =
                mustacheFactory.compile("templates/notification/invoice.html");
        Map<String, String> data = new HashMap<>();
        data.put("invoice", tagihan.getNomorInvoice());
        data.put("bank", tagihan.getBank());
        data.put("rekening", tagihan.getNomorRekening());
        data.put("nama", tagihan.getNamaRekening());
        data.put("nilai", tagihan.getNilai().toPlainString());
        data.put("keterangan", tagihan.getKeterangan());

        StringWriter output = new StringWriter();
        templateEmail.execute(output, data);

        emailService.kirimEmail(
                mailFrom,
                tagihan.getPendaftaran().getPeserta().getEmail(),
                tagihan.getKeterangan(),
                output.toString());
    }



}
