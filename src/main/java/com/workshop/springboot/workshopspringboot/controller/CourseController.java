package com.workshop.springboot.workshopspringboot.controller;

import com.workshop.springboot.workshopspringboot.entity.Materi;
import com.workshop.springboot.workshopspringboot.entity.Peserta;
import com.workshop.springboot.workshopspringboot.repository.MateriRepository;
import com.workshop.springboot.workshopspringboot.repository.PesertaRepository;
import com.workshop.springboot.workshopspringboot.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;


@Slf4j
@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private MateriRepository materiRepository;
    @Autowired private PesertaRepository pesertaRepository;

    @Autowired private RegistrationService registrationService;


    @GetMapping("/list")
    public ModelMap list(Pageable pageable) {
        Page<Materi> hasilQuery = materiRepository.findAll(pageable);

        log.info("Hasil query ada {} record", hasilQuery.getSize());

        return new ModelMap()
                .addAttribute("daftarMateri", hasilQuery);
    }

    @GetMapping("/enroll")
    public ModelMap displayEnrollment(@RequestParam Materi materi, Authentication auth) {
        log.info(auth.toString());
        User u = (User) auth.getPrincipal();
        Peserta p = pesertaRepository.findByEmail(u.getUsername());
        return new ModelMap()
                .addAttribute("peserta", p)
                .addAttribute("materi", materi);
    }


    @PostMapping("/enroll")
    public String processEnrollment() {
        return "redirect:enrollment_confirmation";
    }

    @GetMapping("/enrollment_confirmation")
    public ModelMap displayEnrollmentConfirmation() {
        return new ModelMap();
    }

}

