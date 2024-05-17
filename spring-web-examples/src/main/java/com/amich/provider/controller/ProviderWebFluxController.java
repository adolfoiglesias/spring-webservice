package com.amich.provider.controller;

import com.amich.provider.model.Provider;
import com.amich.provider.service.ProviderService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@RestController("/webflux/providers")
@AllArgsConstructor
public class ProviderWebFluxController {

    ProviderService providerService;


    @GetMapping
    @ApiOperation(value = "Webflux: Get all providers", notes = "Get all providers")
    public Flux<Provider> getAllProviders() {
        var providers = providerService.findAll();
        return Flux.fromIterable(providers);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a provider by ID", notes = "Get a provider by its unique ID")
    public Mono<Provider> getProviderById(@PathVariable Long id) {
        var provider = providerService.findById(id);
        return Mono.justOrEmpty(provider);
    }

    @PostMapping
    @ApiOperation(value = "Create a new provider", notes = "Create a new provider")
    public ResponseEntity<Void> createProvider(@RequestBody Provider provider) {
        var entity = providerService.addProvider(provider);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a provider", notes = "Update an existing provider by its ID")
    public ResponseEntity<Provider> updateProvider(@PathVariable Long id, @RequestBody Provider updatedProvider) {
        var provider = providerService.findById(id);

        provider.setName(updatedProvider.getName());
        provider.setAddress(updatedProvider.getAddress());
        provider.setEmail(updatedProvider.getEmail());
        provider.setPhoneNumber(updatedProvider.getPhoneNumber());

        Provider savedProvider = providerService.addProvider(provider);
        return new ResponseEntity<>(savedProvider, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a provider", notes = "Delete a provider by its ID")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
