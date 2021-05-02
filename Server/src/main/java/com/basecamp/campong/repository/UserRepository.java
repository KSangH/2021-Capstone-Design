package com.basecamp.campong.repository;

import com.basecamp.campong.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 닉네임으로 검색
    int countByUsernick(String usernick);

    // 이메일로 검색
    int countByEmail(String email);

    // ID랑 패스워드로 검색
    User findByEmailAndPassword(String email, String passworkd);

    User findByUserid(long userid);

    User findBySession(String session);

}
