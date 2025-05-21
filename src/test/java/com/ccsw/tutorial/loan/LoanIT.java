package com.ccsw.tutorial.loan;


import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";
    public static final int PAGE_SIZE = 5;
    public static final int TOTAL_LOAN = 8;

    public CustomerDto customerDto1;
    public GameDto gameDto1;
    public GameDto gameDto2;
    public static final LocalDate loanDate = LocalDate.of(2025, 2, 8);
    public static final LocalDate returnDate = LocalDate.of(2025, 2, 12);
    public static final int deleteLoan = 8;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
    };





    @BeforeEach
    public void setUp(){
        customerDto1 = new CustomerDto();
        customerDto1.setId(1L);


        gameDto1 = new GameDto();
        gameDto1.setId(2L); // Aventureros al tren


        gameDto2 = new GameDto();
        gameDto2.setId(1L); //FFVII




    }


    @Test
    public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);
        assertNotNull(response);
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
        assertEquals(TOTAL_LOAN, response.getBody().getTotalElements());
    }


    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResult() {
        int elementsCount = TOTAL_LOAN - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));
        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);
        assertNotNull(response);
        assertEquals(TOTAL_LOAN, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }


    @Test
    public void saveShouldCreateNewLoan(){
        long newLoanSize = TOTAL_LOAN+ 1;
        LoanDto dto = new LoanDto();
        dto.setCustomer(customerDto1);
        dto.setGame(gameDto1);
        dto.setFechaInicio(loanDate);
        dto.setFechaDevolucion(returnDate);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();

        searchDto.setPageable(new PageableRequest(0, (int) newLoanSize));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);
        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());

        LoanDto loanDto = response.getBody().getContent().stream().filter(e -> e.getId().equals(newLoanSize)).findFirst().orElse(null);
        assertNotNull(loanDto);
        assertEquals(loanDate, loanDto.getFechaInicio());
        assertEquals(returnDate, loanDto.getFechaDevolucion());
    }

    @Test
    public void saveLoanWithReservedGameShouldReturnConflict(){
        LoanDto dto = new LoanDto();
        dto.setCustomer(customerDto1);
        dto.setGame(gameDto1);
        dto.setFechaInicio(LocalDate.of(2025,9, 16));
        dto.setFechaDevolucion(LocalDate.of(2025,9,22));
        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void saveLoanWithMoreThanTwoReservedGamesShouldReturnConflict(){
        LoanDto dto = new LoanDto();
        dto.setCustomer(customerDto1);
        dto.setGame(gameDto2);
        dto.setFechaInicio(LocalDate.of(2025,9, 13));
        dto.setFechaDevolucion(LocalDate.of(2025,9,22));
        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void deleteWithExistsIdShouldCategory(){

        long newLoanSize = TOTAL_LOAN - 1 ;
        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + deleteLoan, HttpMethod.DELETE, null, Void.class);
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOAN));
        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto) , responseTypePage);
        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());

    }





}
