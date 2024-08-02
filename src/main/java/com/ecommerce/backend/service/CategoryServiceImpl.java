package com.ecommerce.backend.service;

import com.ecommerce.backend.exceptions.APIException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ecommerce.backend.payload.CategoryDTO;
import com.ecommerce.backend.payload.CategoryResponse;
import com.ecommerce.backend.repositories.CategoryRespository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRespository categoryRespository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        Category categoryFromDb = categoryRespository.findByCategoryName(category.getCategoryName());
        if(categoryFromDb != null)
            throw new APIException("Categoria con id " + category.getCategoryId() + " no existe!!");
        Category savedCategory = categoryRespository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category categoryFromDb = categoryRespository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", "categoryId", categoryId));
        Category category = modelMapper.map(categoryDTO,Category.class);
        categoryFromDb.setCategoryName(category.getCategoryName());
        Category savedCategory = categoryRespository.save(categoryFromDb);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRespository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria","categoryId",categoryId));
        categoryRespository.delete(category);
        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> categoryPage = categoryRespository.findAll(pageable);
        List<Category> categoryList = categoryPage.getContent();
        if(categoryList.isEmpty())
            throw new APIException("La lista de categorias esta vacia");

        List<CategoryDTO> categoryDTOList = categoryList.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOList);
        categoryResponse.setTotalpages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setPageSize(categoryPage.getSize());
        return categoryResponse;
    }
}
