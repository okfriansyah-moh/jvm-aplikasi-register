package com.workshop.springboot.workshopspringboot.controller;

import com.workshop.springboot.workshopspringboot.repository.PembayaranRepository;
import com.workshop.springboot.workshopspringboot.repository.TagihanRepository;
import com.workshop.springboot.workshopspringboot.service.DokuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doku")
public class DokuController {
    @Autowired
    DokuService dokuService;

    @Autowired
    TagihanRepository tagihanDao;

    @Autowired
    PembayaranRepository pembayaranDao;

    @GetMapping("/continue")
    public String kirimCustomerKeDoku(ModelMap model){
        return "doku";
    }

}
