package com.example.jwt.security.principal;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PrincipalDataRepository extends Repository<PrincipalData, String> {
    Optional<PrincipalData> findByUsername(String username);
}
