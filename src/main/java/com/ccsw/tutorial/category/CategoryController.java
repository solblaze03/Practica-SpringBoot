package com.ccsw.tutorial.category;


import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


@Tag(name="Category",description="API of Category")
@RequestMapping(value = "/category")
@RestController
@CrossOrigin(origins = "*")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    @Autowired
    ModelMapper mapper;


    @Operation(summary = "Find", description = "Method that return a list of categories")
    @GetMapping(path = "")
    public List<CategoryDto> findAll(){

        List<Category> categories = this.categoryService.findAll();
        return categories.stream().map(e -> mapper.map(e, CategoryDto.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save or Update", description = "Method that saves or updates a Category")
    @PutMapping(path = {"" ,"/{id}"})
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody CategoryDto dto) {
       this.categoryService.save(id, dto);
    }

    @Operation(summary = "Delete", description = "Method that deletes a category")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Long id ) throws Exception{

        this.categoryService.delete(id);


    }

}
