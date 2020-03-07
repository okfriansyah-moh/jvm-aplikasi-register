package com.workshop.springboot.workshopspringboot.controller;

import com.workshop.springboot.workshopspringboot.entity.Peserta;
import com.workshop.springboot.workshopspringboot.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/form")
    public ModelMap showRegistrationForm() {
        log.info("Menjalankan method showRegistrationForm");
        ModelMap mm = new ModelMap();
        mm.addAttribute("nama", "Muhammad Okfriansyah");
        mm.addAttribute("waktu", LocalDateTime.now());
        return mm;
    }

    @PostMapping("/form")
    public String formRegistrationProcess(@ModelAttribute @Valid Peserta peserta,
                                          BindingResult errors,
                                          SessionStatus status
    ) {
        log.info("Seharusnya nanti di sini insert ke database");

        /* jangan return html, return redirect supaya tidak dobel submit
        ModelAndView mav = new ModelAndView("konfirmasi");
        return mav;
         */

        if (errors.hasErrors()) {
            return "form";
        }

        registrationService.registrasiPesertaBaru(peserta);
        status.setComplete();
        return "redirect:confirmation";
    }

    @GetMapping("/confirmation")
    public void showConfirmationPage() {

    }

    @GetMapping("/verify")
    public String emailVerification(@RequestParam String token) {
        registrationService.verifikasiToken(token);
        return "redirect:verified";
    }


    @GetMapping("verified")
    public void emailVerificationSuccess() {

    }
}
