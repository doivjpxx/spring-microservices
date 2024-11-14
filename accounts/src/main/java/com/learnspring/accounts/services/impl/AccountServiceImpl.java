package com.learnspring.accounts.services.impl;

import com.learnspring.accounts.constants.AccountsConstants;
import com.learnspring.accounts.dtos.AccountDto;
import com.learnspring.accounts.dtos.CustomerDto;
import com.learnspring.accounts.entities.Account;
import com.learnspring.accounts.entities.Customer;
import com.learnspring.accounts.exceptions.CustomerAlreadyExistsException;
import com.learnspring.accounts.exceptions.ResourceNotFoundException;
import com.learnspring.accounts.mappers.AccountsMapper;
import com.learnspring.accounts.repositories.AccountsRepository;
import com.learnspring.accounts.repositories.CustomersRepository;
import com.learnspring.accounts.services.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;
    private CustomersRepository customersRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomersMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customersRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customersRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto getAccount(String mobileNumber) {
        Customer customer = customersRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Account account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomersMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountsMapper.mapToAccountDto(account, new AccountDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();

        if (accountDto != null) {
            Account account = accountsRepository.findById(accountDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString()));

            AccountsMapper.mapToAccount(accountDto, account);
            account = accountsRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customersRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));

            CustomersMapper.mapToCustomer(customerDto, customer);
            customersRepository.save(customer);
            isUpdated = true;
        }

        return isUpdated;
    }


    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
