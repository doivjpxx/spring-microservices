package com.learnspring.accounts.services;

import com.learnspring.accounts.dtos.AccountDto;
import com.learnspring.accounts.dtos.CustomerDto;

public interface IAccountService {

    /**
     * Creates a new account based on the provided account details.
     *
     * @param accountDto the account details to create a new account
     */
    void createAccount(CustomerDto accountDto);
    CustomerDto getAccount(String mobileNumber);
    boolean updateAccount(CustomerDto accountDto);
}
