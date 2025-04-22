package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.model.AuthorSearchDto;
import org.springframework.data.domain.Page;

public interface AuthorService {

    Page<Author> findPage(AuthorSearchDto dto);


    void save(Long id, AuthorDto dto);

    void delete(Long id) throws Exception;

}
