package com.workshop.springboot.workshopspringboot.repository;

import com.workshop.springboot.workshopspringboot.entity.Pendaftaran;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PendaftaranRepository extends PagingAndSortingRepository<Pendaftaran, String> {
}
