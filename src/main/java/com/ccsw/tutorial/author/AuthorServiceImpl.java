package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.model.AuthorSearchDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Page<Author> findPage(AuthorSearchDto dto) {

        return this.authorRepository.findAll(dto.getPageable().getPageable());
    }

    @Override
    public void save(Long id, AuthorDto dto) {
        Author author;

        if(id == null){
            author = new Author();

        }else{
            author = this.authorRepository.findById(id).orElse(null);
        }

        BeanUtils.copyProperties(dto, author, "id");

        this.authorRepository.save(author);
    }

    @Override
    public void delete(Long id) throws Exception {
        if(this.authorRepository.findById(id).orElse(null) == null){
            throw new Exception("Not Exists");
        }

        this.authorRepository.deleteById(id);
    }
}
