package com.amich.provider.controller;

import com.amich.provider.SpringWebExampleApplication;
import com.amich.provider.model.Provider;
import com.amich.provider.repository.ProviderRepository;
import com.amich.provider.service.ProviderService;
import com.amich.provider.service.ProviderServiceImpl;
import com.amich.provider.util.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import java.util.Optional;

@WebMvcTest(ProviderController.class)
@ContextConfiguration(classes = {SpringWebExampleApplication.class, ProviderServiceImpl.class, ProviderRepository.class})
public class ProviderControllerWithMockMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean // mocked bean, testing web & service layer
    ProviderRepository providerRepository;

    @Autowired
    ProviderService providerService;

    List<Provider> mockedProviders;

    Provider provider1;

    @BeforeEach
    void eachSetUp(){

        mockedProviders = List.of(
                Provider.builder()
                        .name("provider1")
                        .email("email")
                        .build(),
                Provider.builder()
                        .name("provider2")
                        .email("email")
                        .build());

        provider1 = Provider.builder()
                .name("provider1")
                .email("email")
                .build();

        Mockito.when(providerRepository.findAll()).thenReturn(mockedProviders);
        Mockito.when(providerRepository.findById(1L)).thenReturn(Optional.of(provider1));
        Mockito.when(providerRepository.save(Mockito.any())).thenReturn(provider1);
    }


    @Test
    void test_listProviders() throws Exception {

        MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.get("/providers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilders)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("provider1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("email"));

    }

    @DisplayName("Get Provider details for give a Provider ID")
    @Test
    void test_getProviderAsAdmin() throws Exception {

        MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.get("/providers/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilders)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name")
                        .value("provider1"))
        ;
    }

    @DisplayName("Get Provider details for give a Provider ID")
    @Test
    void test_getNotFoundProvider() throws Exception {

        MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.get("/providers/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilders)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("provider not found with ID 2"))
        ;
    }


    @Test
    void test_addProviderAsAdmin() throws Exception {

        var provider = Provider.builder()
                .name("provider1")
                .email("email")
                .build();

        MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.post("/providers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UtilsTest.asJsonString(provider));

        mockMvc.perform(requestBuilders)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(""));
        ;
    }

}
