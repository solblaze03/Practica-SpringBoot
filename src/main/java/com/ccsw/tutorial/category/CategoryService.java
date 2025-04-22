package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;

import java.util.List;

public interface CategoryService {

    Category get(Long id);

    List<Category> findAll();

    void save(Long id, CategoryDto dto);

    void delete(Long id) throws Exception;

}
