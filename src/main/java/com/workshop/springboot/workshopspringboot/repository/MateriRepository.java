package com.workshop.springboot.workshopspringboot.repository;

import com.workshop.springboot.workshopspringboot.entity.Materi;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MateriRepository extends PagingAndSortingRepository<Materi, String> {
}

