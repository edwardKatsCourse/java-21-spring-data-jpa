package com.eshop.repository;

import com.eshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select max (u.age) from User u")
    Integer findMaxAge();

    User findByUsername(String username);

    List<User> findAllByFirstNameAndLastName(String firstName,
                                                     String lastName);

    List<User> findAllByLastNameIn(String...lastNames);

    List<User> findAllByIsActivatedTrue();

    Page<User> findAllByCreatedOnNotNull(Pageable pageable);
}
