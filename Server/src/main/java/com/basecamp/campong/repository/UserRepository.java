package com.basecamp.campong.repository;

import com.basecamp.campong.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 닉네임으로 검색
    User findByUsernick(String usernick);

    // 이메일로 검색
    User findByEmail(String email);

}
