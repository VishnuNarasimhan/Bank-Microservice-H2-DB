package com.bank.accountservice.service;

import com.bank.accountservice.dto.CustomerDetailsDto;

public interface ICustomersService {
    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return CustomerDetailsDto
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
