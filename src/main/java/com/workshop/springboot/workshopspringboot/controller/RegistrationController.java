package com.workshop.springboot.workshopspringboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @GetMapping("/form")
    public ModelMap showRegistrationForm() {
        log.info("Menjalankan method showRegistrationForm");
        ModelMap mm = new ModelMap();
        mm.addAttribute("nama", "Muhammad Okfriansyah");
        mm.addAttribute("waktu", LocalDateTime.now());
        return mm;
    }

    @PostMapping("/form")
    public String formRegistrationProcess() {
        log.info("Seharusnya nanti di sini insert ke database");

        /* jangan return html, return redirect supaya tidak dobel submit
        ModelAndView mav = new ModelAndView("konfirmasi");
        return mav;
         */

        return "redirect:confirmation";
    }

    @GetMapping("/confirmation")
    public void showConfirmationPage() {

    }

    @GetMapping("verified")
    public void emailVerificationn() {

    }
}
