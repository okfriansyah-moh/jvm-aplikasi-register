package com.workshop.springboot.workshopspringboot.service;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.workshop.springboot.workshopspringboot.entity.Peserta;
import com.workshop.springboot.workshopspringboot.entity.VerifikasiEmail;
import com.workshop.springboot.workshopspringboot.repository.PesertaRepository;
import com.workshop.springboot.workshopspringboot.repository.VerifikasiEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.time.LocalDateTime;
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

    public void registrasiPesertaBaru(Peserta p) {
        VerifikasiEmail ve = new VerifikasiEmail();
        ve.setPeserta(p);
        ve.setToken(UUID.randomUUID().toString());
        ve.setExpire(LocalDateTime.now().plusDays(tokenExpiryDays));

        pesertaRepository.save(p);
        verifikasiEmailRepository.save(ve);
        kirimVerifikasi(ve);
    }
    private void kirimVerifikasi(VerifikasiEmail ve) {
        Mustache templateEmail =
                mustacheFactory.compile("templates/notification/verification.html");
        Map<String, String> data = new HashMap<>();
        data.put("nama", ve.getPeserta().getNama());
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

}
