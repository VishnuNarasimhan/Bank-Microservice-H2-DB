package com.bank.accountservice.Mapper;

import com.bank.accountservice.dto.AccountDto;
import com.bank.accountservice.entity.Account;


public class AccountMapper {
    public static AccountDto maptoAccountDto(Account account, AccountDto accountDto) {
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBankAddress(account.getBankAddress());
        return accountDto;
    }

    public static Account maptoAccount(AccountDto accountDto, Account account) {
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBankAddress(accountDto.getBankAddress());
        return account;
    }
}
