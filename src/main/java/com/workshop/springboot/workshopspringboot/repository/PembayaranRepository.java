package com.workshop.springboot.workshopspringboot.repository;

import com.workshop.springboot.workshopspringboot.entity.Pembayaran;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PembayaranRepository extends PagingAndSortingRepository<Pembayaran, String> {
}
