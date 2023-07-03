package com.example.controllers;

import com.example.interfaces.*;
import com.example.models.Customer;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("api/v1/customers")
public class CustomersController {

    private final ICustomerService customerService;

    public CustomersController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ok(customerService.getCustomers());
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
        return ok("Customer with id " + customerId + " deleted successfully");
    }

    @PostMapping
    public ResponseEntity<String> addCustomer(@Valid @RequestBody Customer request) {
        if (request.getAge() == null || request.getName() == null || request.getEmail() == null) {
            return badRequest().body("All fields are required");
        }
        customerService.addCustomer(request);
        return ok("Customer created");
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable Integer customerId, @Valid @RequestBody Customer customerFromRequest) {
        if (customerFromRequest.getName() == null || customerFromRequest.getEmail() == null || customerFromRequest.getAge() == null) {
            return badRequest().body("All fields are required");
        }
        customerService.updateCustomer(customerId, customerFromRequest);
        return ok("Customer with id " + customerId + " updated");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException entityNotFoundException) {
        return notFound().build();
    }
}
