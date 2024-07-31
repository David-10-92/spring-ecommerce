package com.ecommerce.backend.repositories;

import com.ecommerce.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRespository extends JpaRepository<Category,Long> {
    Category findByCategoryName(String categoryName);
}
