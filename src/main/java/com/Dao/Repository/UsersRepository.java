package com.Dao.Repository;

import com.Dao.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer>{
    @Query(value = "select * from users where username = :username and password = :password", nativeQuery = true)
    Users findUserByUsernameAndPw(@Param("username") String username, @Param("password")String pw);

    Users findUserByUsername(String username);
}
