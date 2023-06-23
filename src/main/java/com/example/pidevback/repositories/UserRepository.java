package com.example.pidevback.repositories;

import com.example.pidevback.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users,Long>{

    Optional<Users> findUserByEmail(String email);

}
