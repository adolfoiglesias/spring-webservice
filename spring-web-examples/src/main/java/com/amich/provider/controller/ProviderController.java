package com.amich.provider.controller;

import com.amich.provider.model.Provider;
import com.amich.provider.service.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Api(tags = "Provider Management")
@RestController
@RequestMapping("/providers")
@AllArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping
    @ApiOperation(value = "Get all providers", notes = "Get all providers")
    public ResponseEntity<List<Provider>> getAllProviders() {
        var providers = providerService.findAll();
        return new ResponseEntity<>(providers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a provider by ID", notes = "Get a provider by its unique ID")
    public ResponseEntity<Provider> getProviderById(@PathVariable Long id) {
        var provider = providerService.findById(id);
        return new ResponseEntity(provider, HttpStatus.OK);
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
