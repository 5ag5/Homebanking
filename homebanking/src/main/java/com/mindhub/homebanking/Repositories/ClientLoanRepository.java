package com.mindhub.homebanking.Repositories;

import com.mindhub.homebanking.Models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RestController;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
    ClientLoan findBySerialNumber (String serialNumber);
}
