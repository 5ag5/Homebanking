package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.DTOs.LoanAplicationDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.TypeAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {

    void SaveAccount(Account account);

    public List<AccountDTO> getAccounts();

    public AccountDTO getAccount(@PathVariable Long id);

    Account getAccount(String number);

    Account findByNumber(String number);

    Account findAccountByNunber(LoanAplicationDTO loanAplication);

}
