package com.example.web_jpa;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    private final CustomerModelAssembler assembler;

    CustomerController(CustomerRepository customerRepository, CustomerModelAssembler customerModelAssembler) {
        this.customerRepository = customerRepository;
        this.assembler = customerModelAssembler;
    }

    @GetMapping("/customers")
//    List<Customer> all() {
//        return customerRepository.findAll();
//    }
    CollectionModel<EntityModel<Customer>> all() {
        List<EntityModel<Customer>> customers = customerRepository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(customers,linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

    @PostMapping("/customers")
//    Customer newCustomer(@RequestBody Customer newCustomer) {
//        return customerRepository.save(newCustomer);
//    }
    ResponseEntity<EntityModel<Customer>> newCustomer(@RequestBody Customer customer) {

        EntityModel<Customer> customerEntityModel = assembler.toModel(customerRepository.save(customer)); // customerRepository.save(customer) return object

        return ResponseEntity.created(customerEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(customerEntityModel);// create new resource, However, it comes preloaded with a Location response header.
    }

    @GetMapping("/customers/{id}")
//    Customer one(@PathVariable Long id) {
//
//        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
//    }
    EntityModel<Customer> one(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return assembler.toModel(customer);
    }

    // replace customer
    @PutMapping("/customers/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody Customer newCustomer) {
        Customer c = customerRepository.findById(id).map(customer -> {
            customer.setName(newCustomer.getName());
            customer.setRole(newCustomer.getRole());
            return customerRepository.save(customer);
        }).orElseGet(() -> {
            return customerRepository.save(newCustomer);
        });

        EntityModel<Customer> customerEntityModel = assembler.toModel(c);

        return ResponseEntity.ok(customerEntityModel); // return present resource
    }

    // DeleteMapping Default not to throw an exception if id is not present.
    @DeleteMapping("/customers/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();// returns an HTTP 204 No Content response
    }
}
