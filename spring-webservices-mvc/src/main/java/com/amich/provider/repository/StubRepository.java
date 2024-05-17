package com.amich.provider.repository;

import com.amich.provider.model.Provider;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class StubRepository implements  ProviderRepository {

    private List<Provider> providers;

    public StubRepository(List<Provider> providers){
        this.providers = providers;
    }

    @PostConstruct
    public void init(){
         if (this.providers == null || this.providers.isEmpty()) {
             this.providers = new ArrayList<>();
             this.providers.add(Provider.builder()
                     .id(1l)
                     .name("provider1")
                     .email("email")
                     .build());
             this.providers.add(Provider.builder()
                     .id(2l)
                     .name("provider2")
                     .email("email")
                     .build());
         }
    }

    @Override
    public boolean deleteById(Long id) {
        return providers.removeIf(provider -> provider.getId().equals(id));
    }

    @Override
    public Optional<Provider> findById(Long id) {
        return providers.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public List<Provider> findAll() {
        return providers;
    }

    @Override
    public Provider save(Provider provider) {
        providers.add(provider);
        return provider;
    }
}
