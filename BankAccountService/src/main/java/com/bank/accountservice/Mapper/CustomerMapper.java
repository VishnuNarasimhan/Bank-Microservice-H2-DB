package com.bank.accountservice.Mapper;

import com.bank.accountservice.dto.CustomerDetailsDto;
import com.bank.accountservice.dto.CustomerDto;
import com.bank.accountservice.entity.Customer;

public class CustomerMapper {
    public static CustomerDto maptoCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setMail(customer.getMail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static CustomerDetailsDto maptoCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {
        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setMail(customer.getMail());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());
        return customerDetailsDto;
    }

    public static Customer maptoCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setMail(customerDto.getMail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
