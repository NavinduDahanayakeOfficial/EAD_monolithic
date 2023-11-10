package com.EAD.EAD_monolithic.repo;

import com.EAD.EAD_monolithic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

}
