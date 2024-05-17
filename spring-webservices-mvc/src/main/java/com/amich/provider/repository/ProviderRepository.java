package com.amich.provider.repository;

import com.amich.provider.model.Provider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository {

    List<Provider> findAll();

    Provider save(Provider provider);

    Optional<Provider> findById(Long id);

    boolean deleteById(Long id);

}