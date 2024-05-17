package com.amich.provider.controller;


import com.amich.provider.model.Provider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;


@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
public class ProviderControllerWithSpringBootTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_PATH = "http://localhost:";


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD )
    void test_listProviders() {

        var result = restTemplate
                .getForEntity(BASE_PATH +  port + "/providers", List.class);
        var list = result.getBody();
        Assertions.assertEquals(2, list.size());
    }

    @DisplayName("Get Provider details for give a Provider ID")
    @Test
    void test_getProviderAsAdmin() {
        var result = restTemplate.getForEntity(BASE_PATH + port + "/providers/{id}", Provider.class, 1l);
        Assertions.assertEquals("provider1", result.getBody().getName());
    }

    @DisplayName("Get Provider details for give a Provider ID")
    @Test
    void test_getNotFoundProvider()  {
        var result = restTemplate.getForEntity(BASE_PATH + port + "/providers/{id}", String.class, 3l);
        Assertions.assertEquals(404, result.getStatusCode().value());
    }


    @Test
    void test_addProvider() {

        var provider = Provider.builder()
                .id(10l)
                .name("provider1")
                .email("email")
                .build();
        var result = restTemplate.postForEntity(BASE_PATH + port + "/providers", provider, Void.class);
        Assertions.assertEquals(HttpStatus.CREATED.value(), result.getStatusCode().value());
    }
}
