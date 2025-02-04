package com.bank.accountservice.service.impl;

import com.bank.accountservice.Mapper.AccountMapper;
import com.bank.accountservice.Mapper.CustomerMapper;
import com.bank.accountservice.constants.AccountConstants;
import com.bank.accountservice.dto.AccountDto;
import com.bank.accountservice.dto.CustomerDto;
import com.bank.accountservice.entity.Account;
import com.bank.accountservice.entity.Customer;
import com.bank.accountservice.exception.CustomerAlreadyExistsException;
import com.bank.accountservice.exception.ResourceNotFoundException;
import com.bank.accountservice.repository.AccountRepository;
import com.bank.accountservice.repository.CustomerRepository;
import com.bank.accountservice.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    // Autowired is not required because there is only single constructor
    // and Single constructor will accept below two params.
    public AccountRepository accountRepository;
    public CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            // Exception will be sent to CustomerAlreadyExistsException.
            // And it is caught by GlobalExceptionHandler.
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile Number : "
                    + customerDto.getMobileNumber());
        }

        Customer customer = CustomerMapper.maptoCustomer(customerDto, new Customer());
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * @return the new Account details
     */
    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBankAddress(AccountConstants.ADDRESS);
        return newAccount;
    }

    /**
     * @param mobileNumber - Input mobileNumber
     * @return Account details based on a give number
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        // Need to map Customer -> CustomerDto and
        // mapping Account -> AccountDto inside CustomerDto.
        CustomerDto customerDto = CustomerMapper.maptoCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(
                AccountMapper.maptoAccountDto(account, new AccountDto())
        );

        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        // Checking account is present or not.
        // Get the account details and save it in
        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto != null) {
            // Finding with accountNumber. So that client will not be able to update the accountNumber.
            Account account = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", accountDto.getAccountNumber().toString())
            );

            // Map to accountDto -> account and save it to DB.
            AccountMapper.maptoAccount(accountDto, account);
            account = accountRepository.save(account);

            // Find customerId with help of account which we fetched above.
            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );

            CustomerMapper.maptoCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the Account deletion is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        // This is custom deleteByCustomerId we created, so we need to
        // annotate @Transactional and @Modifying in accountRepository
        accountRepository.deleteByCustomerId(customer.getCustomerId());

        // This is generated from the JPA Framework
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
