package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface LoanService {
    Loan get(Long id);

    Page<Loan> findPage(LoanSearchDto dto,String title, String customer, String date);

    ResponseEntity save(LoanDto dto);

    void delete(Long id);
}
