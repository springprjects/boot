package com.thbs.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thbs.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

   public User findOneByUsername(String username);

}
