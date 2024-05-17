package com.amich.provider.service;

import com.amich.provider.model.Provider;
import com.amich.provider.repository.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class ProviderServiceImplTest {

    @Autowired
    ProviderServiceImpl providerService;

    @MockBean
    ProviderRepository providerRepository;

    @Test
    public void test_getProviderAsNoOwnerProvider() {

        var provider = Provider.builder()
                .email("email")
                .name("provider2")
                .build();



    }
}
