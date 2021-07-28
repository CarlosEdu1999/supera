package com.example.demo.repository;

import com.example.demo.model.Endereço;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndereçoRepository extends JpaRepository<Endereço,Long> {
}
