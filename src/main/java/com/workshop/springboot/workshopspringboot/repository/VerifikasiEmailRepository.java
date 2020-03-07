package com.workshop.springboot.workshopspringboot.repository;

import com.workshop.springboot.workshopspringboot.entity.VerifikasiEmail;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VerifikasiEmailRepository extends PagingAndSortingRepository<VerifikasiEmail, String> {
    VerifikasiEmail findByToken(String token);
}
