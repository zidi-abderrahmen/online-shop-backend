package com.na.store.repositories;

import com.na.store.entities.Product;
import com.na.store.enums.ClotheSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    Page<Product> findBySize(ClotheSize size, Pageable pageable);

    boolean existsBySize(ClotheSize size);


}