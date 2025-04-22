package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public Category get(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }


    @Override
    public void save(Long id, CategoryDto dto) {
        Category category;

        if(id == null){
            category = new Category();

        }else{
            category = this.get(id);
        }


        category.setName(dto.getName());

        this.categoryRepository.save(category);


    }

    @Override
    public void delete(Long id) throws Exception {

        if(this.get(id) != null){
            throw new Exception("NotExists");
        }

        this.categoryRepository.deleteById(id);
    }
}
