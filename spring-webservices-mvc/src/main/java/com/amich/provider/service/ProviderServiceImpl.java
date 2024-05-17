package com.amich.provider.service;

import com.amich.provider.exception.EntityNotFoundException;
import com.amich.provider.model.Provider;
import com.amich.provider.repository.ProviderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProviderServiceImpl implements  ProviderService {

    final ProviderRepository providerRepository;

    @Override
    public Provider addProvider(Provider provider) {
         return providerRepository.save(provider);
    }

    @Override
    public List<Provider> findAll() {
        return providerRepository.findAll();
    }

    @Override
    public Provider findById(Long id) {
        var optional =  providerRepository.findById(id);
        return optional.orElseThrow(  () -> new EntityNotFoundException("provider not found with ID "+ id));
    }

    @Override
    public boolean deleteById(Long id) {
        return providerRepository.deleteById(id);
    }
}
