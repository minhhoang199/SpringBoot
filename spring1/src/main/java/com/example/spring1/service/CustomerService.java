package com.example.spring1.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomer() {
        return this.customerRepository.getAll();
    }

    public Customer getCustomerById(Integer id) {
        var oCustomer = this.customerRepository.getByID(id);
        if (oCustomer.isPresent()) {
            return oCustomer.get();
        } else {
            return null;
        }
    }

    public boolean addCustomer(Customer newCustomer) {
        if (customerRepository.addCustomer(newCustomer)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateById(Integer id, Customer customer) {
        if (customerRepository.updateById(id, customer) != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteById(Integer id) {
        if (customerRepository.deleteById(id) != null) {
            return true;
        } else {
            return false;
        }
    }
}
