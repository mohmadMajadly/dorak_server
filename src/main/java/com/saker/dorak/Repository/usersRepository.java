package com.saker.dorak.Repository;

import com.saker.dorak.Entity.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface usersRepository extends JpaRepository<users, Long> {
    @Query("SELECT u FROM users u WHERE u.username = :username")
    users findByUsername(@Param("username") String username);

    @Query("SELECT u FROM users u WHERE u.email = :email")
    users findByEmail(@Param("email") String email);

    @Query("SELECT u FROM users u WHERE u.phone = :phone")
    users findByPhone(@Param("phone") String phone);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM users u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM users u WHERE u.username = :username AND u.password = :password")
    boolean checkPassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT CASE WHEN u.phone IS NOT NULL AND u.phone <> '' THEN TRUE ELSE FALSE END FROM users u WHERE u.username = :username")
    boolean isPhoneAvailableForUsername(@Param("username") String username);

}
