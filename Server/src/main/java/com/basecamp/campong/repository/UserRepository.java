package com.basecamp.campong.repository;

import com.basecamp.campong.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //User findByUsername(String name);

}
