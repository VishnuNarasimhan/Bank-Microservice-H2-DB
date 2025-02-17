package com.bank.accountservice.service.impl;

import com.bank.accountservice.Mapper.AccountMapper;
import com.bank.accountservice.Mapper.CustomerMapper;
import com.bank.accountservice.dto.AccountDto;
import com.bank.accountservice.dto.CardDto;
import com.bank.accountservice.dto.CustomerDetailsDto;
import com.bank.accountservice.dto.LoansDto;
import com.bank.accountservice.entity.Account;
import com.bank.accountservice.entity.Customer;
import com.bank.accountservice.exception.ResourceNotFoundException;
import com.bank.accountservice.repository.AccountRepository;
import com.bank.accountservice.repository.CustomerRepository;
import com.bank.accountservice.service.ICustomersService;
import com.bank.accountservice.service.client.CardsFeignClient;
import com.bank.accountservice.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return CustomerDetailsDto
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationID) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        // Mapper is used here to,
        // Map: Customer to CustomerDetailsDto
        // Map: Account to AccountDto
        CustomerDetailsDto customerDetailsDto = CustomerMapper.maptoCustomerDetailsDto(customer, new CustomerDetailsDto());
        AccountDto accountDto = AccountMapper.maptoAccountDto(account, new AccountDto());

        customerDetailsDto.setAccountDetails(accountDto);

        ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.getCard(mobileNumber, correlationID);
        if(null!= cardDtoResponseEntity){
            customerDetailsDto.setCardDetails(cardDtoResponseEntity.getBody());
        }


        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber, correlationID);
        if(null!= loansDtoResponseEntity){
            customerDetailsDto.setLoansDetails(loansDtoResponseEntity.getBody());
        }

        return customerDetailsDto;
    }

}
