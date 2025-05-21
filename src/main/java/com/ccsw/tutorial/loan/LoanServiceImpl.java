package com.ccsw.tutorial.loan;


import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.customer.CustomerService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository repository;

    @Autowired
    CustomerService customerService;

    @Autowired
    GameService gameService;

    @Override
    public Loan get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Loan> get() {
        return (List<Loan>) repository.findAll();
    }

    @Override
    public Page<Loan> findPage(LoanSearchDto dto, String title, String customer, String date) {

        LocalDate lDate = null;
        if (date != null) lDate = LocalDate.parse(date);

        LoanSpecification titleSpec = new LoanSpecification(new SearchCriteria("game.title", ":", title));
        LoanSpecification customerSpec = new LoanSpecification(new SearchCriteria("customer.name", ":", customer));
        LoanSpecification loanDate = new LoanSpecification(new SearchCriteria("fechaInicio", "<=", lDate));
        LoanSpecification returnDate = new LoanSpecification(new SearchCriteria("fechaDevolucion", ">=", lDate));
        Specification<Loan> spec = Specification.where(titleSpec).and(customerSpec).and(loanDate).and(returnDate);
        return this.repository.findAll(spec, dto.getPageable().getPageable());

    }

    @Override
    public ResponseEntity<?> save(LoanDto dto) {
        Loan loan = new Loan();
        BeanUtils.copyProperties(dto, loan, "id", "game", "customer");
        try {

            if (loanDtoIsNotNullOrNotBlank(dto)) {

                if (isFormValid(dto)) {
                    loan.setCustomer(customerService.get(dto.getCustomer().getId()));
                    loan.setGame(gameService.get(dto.getGame().getId()));
                    this.repository.save(loan);
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el formulario");


        } catch (UnexpectedTypeException | ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el formulario");
        }
    }



    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    private boolean loanDtoIsNotNullOrNotBlank(LoanDto dto) {
        if (dto.getCustomer() == null &&
                dto.getGame() == null) {
            return true;
        }
        if (dto.getGame().getTitle() == null &&
                dto.getCustomer().getName() == null) {
            return true;
        }
        return !dto.getGame().getTitle().isBlank() && !dto.getCustomer().getName().isBlank();
    }



    private boolean isFormValid(LoanDto dto) {
        boolean vDate = validateDate(dto.getFechaInicio(), dto.getFechaDevolucion());
        boolean vdBetween = validateDaysBetween(dto.getFechaInicio(), dto.getFechaDevolucion());
        boolean vgAvailable = validateGameAvailable(dto);
        boolean vngBorrowed = validateNumberGamesBorrowed(dto);

        return vDate && vdBetween && vgAvailable && vngBorrowed;
    }


    private boolean validateDate(LocalDate loanDate, LocalDate returnDate) {
        return loanDate.isBefore(returnDate);

    }

    private boolean validateDaysBetween(LocalDate loanDate, LocalDate returnDate) {
        long days = ChronoUnit.DAYS.between(loanDate, returnDate);
        return days <= 14;
    }

    private boolean validateGameAvailable(LoanDto dto) {

        LoanSpecification idSpec = new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGame().getId()));
        LoanSpecification startDate = new LoanSpecification(new SearchCriteria("fechaInicio", "<=", dto.getFechaDevolucion()));
        LoanSpecification returnDate = new LoanSpecification(new SearchCriteria("fechaDevolucion", ">=", dto.getFechaInicio()));
        Specification<Loan> spec = Specification.where(idSpec).and(startDate).and(returnDate);
        long count = this.repository.count(spec);

        return !(count >= 1);
    }

    private boolean validateNumberGamesBorrowed(LoanDto dto) {

        LoanSpecification idCustomer = new LoanSpecification(new SearchCriteria("customer.id", ":", dto.getCustomer().getId()));
        LoanSpecification startDate = new LoanSpecification(new SearchCriteria("fechaInicio", "<=", dto.getFechaDevolucion()));
        LoanSpecification returnDate = new LoanSpecification(new SearchCriteria("fechaDevolucion", ">=", dto.getFechaInicio()));
        Specification<Loan> spec = Specification.where(idCustomer).and(startDate).and(returnDate);
        long count = this.repository.count(spec);

        return count <= 1;
    }


}
