package com.workshop.springboot.workshopspringboot.repository;

import com.workshop.springboot.workshopspringboot.entity.Tagihan;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagihanRepository extends PagingAndSortingRepository<Tagihan, String> {
    Tagihan findByNomorInvoice(String nomorInvoice);
}
