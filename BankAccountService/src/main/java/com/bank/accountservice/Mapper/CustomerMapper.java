package com.bank.accountservice.Mapper;

import com.bank.accountservice.dto.CustomerDto;
import com.bank.accountservice.entity.Customer;

public class CustomerMapper {
    public static CustomerDto maptoCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setMail(customer.getMail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer maptoCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setMail(customerDto.getMail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
