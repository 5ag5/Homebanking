package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PDFGeneratorService {
    void export(HttpServletResponse response, List<Transaction> transactions, Account account, String start, String end) throws IOException;

}