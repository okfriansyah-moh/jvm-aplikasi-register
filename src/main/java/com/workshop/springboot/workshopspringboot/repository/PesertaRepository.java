package com.workshop.springboot.workshopspringboot.repository;

import com.workshop.springboot.workshopspringboot.entity.Peserta;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PesertaRepository extends PagingAndSortingRepository<Peserta, String> {
    Peserta findByEmail(String username);
}

