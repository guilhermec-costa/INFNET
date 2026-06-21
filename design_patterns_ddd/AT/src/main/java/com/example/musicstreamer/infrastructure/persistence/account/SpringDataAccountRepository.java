package com.example.musicstreamer.infrastructure.persistence.account;

import com.example.musicstreamer.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataAccountRepository extends JpaRepository<Account, UUID> {
}
