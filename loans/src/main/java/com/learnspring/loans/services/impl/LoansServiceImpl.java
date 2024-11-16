package com.learnspring.loans.services.impl;

import com.learnspring.loans.dtos.LoanDto;
import com.learnspring.loans.entities.Loan;
import com.learnspring.loans.constants.LoansConstants;
import com.learnspring.loans.exceptions.ResourceNotFoundException;
import com.learnspring.loans.mappers.LoansMapper;
import com.learnspring.loans.repositories.LoansRepository;
import com.learnspring.loans.services.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {
    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> loan = loansRepository.findByMobileNumber(mobileNumber);

        if (loan.isPresent()) {
            throw new RuntimeException("Loan already exists for the given mobile number");
        }

        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(String.valueOf(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        loansRepository.save(newLoan);
    }

    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loan loan = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));

        return LoansMapper.mapToLoanDto(loan, new LoanDto());
    }

    @Override
    public boolean updateLoan(LoanDto loanDto) {
        Loan loan = loansRepository.findByLoanNumber(loanDto.getLoanNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loanDto.getLoanNumber()));

        LoansMapper.mapToLoan(loanDto, loan);
        loansRepository.save(loan);

        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loan.getLoanId());
        return true;
    }
}
