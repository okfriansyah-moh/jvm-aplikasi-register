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
public class RegistrasiController {

    @GetMapping("/registrasi")
    public ModelMap registrasi() {
        log.info("Registrasi");
        ModelMap mp = new ModelMap();
        mp.addAttribute("nama","okfri");
        mp.addAttribute("waktu", LocalDateTime.now());
        return mp;
    }

    @PostMapping("/registrasi")
    public String prosesRegistrasiForm() {
        log.info("Seharusnya nanti di sini insert ke database");

        /* jangan return html, return redirect supaya tidak dobel submit
        ModelAndView mav = new ModelAndView("konfirmasi");
        return mav;
         */

        return "redirect:konfirmasi";

    }

    @RequestMapping("/konfirmasi")
    public void konfirmasi() {
        log.info("Konfirmasi");
    }
}
