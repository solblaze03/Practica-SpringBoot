package com.ccsw.tutorial.loan;


import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Tag(name = "", description = "Api of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin("*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(path = "")
    public Page<LoanDto> findPage(@RequestBody LoanSearchDto dto,
                                  @RequestParam(name = "title", required = false) String title,
                                  @RequestParam(name = "customer", required = false) String customer,
                                  @RequestParam(name = "date", required = false) String date
    ) {

        Page<Loan> page = this.loanService.findPage(dto, title, customer, date);

        return new PageImpl<>(page.getContent().stream().map(e -> modelMapper.map(e, LoanDto.class)).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());
    }


    @Operation(summary = "Save", description = "Method that saves or updates a Loan")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public ResponseEntity save(@RequestBody LoanDto dto) {

        return this.loanService.save(dto);
    }

    @Operation(summary = "Delete", description = "Method than delete a Loan")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        this.loanService.delete(id);
    }


}
