package com.kcs3.bid.repository;

import com.kcs3.bid.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

