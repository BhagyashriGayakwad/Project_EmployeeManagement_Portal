package com.springboot.emp_mang.repository;

import com.springboot.emp_mang.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUserIdAndRole(Long userId, User.Role role);
    List<User> findByRole(User.Role role);

    @Query("SELECT u FROM User u WHERE u.role = :role AND u.userId NOT IN (:userIds)")
    List<User> findByRoleAndUserIdNotIn(@Param("role") User.Role role, @Param("userIds") List<Long> userIds);

    List<User> findByRoleAndUserIdIn(User.Role role, List<Long> userIds);

}

