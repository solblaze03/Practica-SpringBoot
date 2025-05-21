package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.CustomerDto;
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

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomerIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/customer";

    public static final int TOTAL_CLIENTS = 3;

    public static final String NEW_CUSTOMER_NAME = "Nala";
    public static final Long NEW_CUSTOMER_ID = 5L;

    public static final Long MODIFY_CUSTOMER_ID = 3L;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<CustomerDto>> responseType = new ParameterizedTypeReference<List<CustomerDto>>() {};

    @Test
    public void findAllShouldReturnAllCustomers(){

        ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(LOCALHOST  + port + SERVICE_PATH , HttpMethod.GET, null , responseType);
        assertNotNull(response);

        assertEquals(4 , response.getBody().size());

    }

    @Test
    public void saveWithoutIdShouldCreateNewCustomer(){
        CustomerDto dto = new CustomerDto();
        dto.setName(NEW_CUSTOMER_NAME);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH , HttpMethod.GET, null , responseType);

        assertNotNull(response);
        assertEquals(5, response.getBody().size() );
        CustomerDto searchClient = response.getBody().stream().filter(e -> e.getId().equals(NEW_CUSTOMER_ID)).findFirst().orElse(null);
        assertNotNull(searchClient);
        assertEquals(NEW_CUSTOMER_NAME, searchClient.getName());
    }

    @Test
    public void modifyWithExistsIdShouldModifyCustomer(){
        CustomerDto dto = new CustomerDto();
        dto.setName(NEW_CUSTOMER_NAME);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_CUSTOMER_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(4 , response.getBody().size());

        CustomerDto clientSearch = response.getBody().stream().filter(item -> item.getId().equals(MODIFY_CUSTOMER_ID)).findFirst().orElse(null);

        assertNotNull(clientSearch);
        assertEquals(NEW_CUSTOMER_NAME, clientSearch.getName());

    }

    @Test
    public void modifyWithNotExistsIdShouldInternalError(){
        CustomerDto dto = new CustomerDto();
        dto.setName(NEW_CUSTOMER_NAME);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CUSTOMER_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void deleteCustomerTest(){
        ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(LOCALHOST  + port + SERVICE_PATH , HttpMethod.GET, null , responseType);
        assertNotNull(response);
        assertEquals(4, response.getBody().size());


        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/"+ 3, HttpMethod.DELETE, null, Void.class);
        ResponseEntity<List<CustomerDto>> responseWithCustomerDelete = restTemplate.exchange(LOCALHOST  + port + SERVICE_PATH , HttpMethod.GET, null , responseType);
        assertNotNull(responseWithCustomerDelete);
        assertEquals(3, responseWithCustomerDelete.getBody().size());


    }

}
