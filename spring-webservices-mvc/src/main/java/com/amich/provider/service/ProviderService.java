package com.amich.provider.service;

import com.amich.provider.model.Provider;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProviderService {

    public Provider addProvider(Provider provider);

    public List<Provider> findAll();

    public Provider  findById(Long id);
    public boolean  deleteById(Long id);


}
