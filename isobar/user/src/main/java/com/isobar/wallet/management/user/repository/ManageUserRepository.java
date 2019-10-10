package com.isobar.wallet.management.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isobar.wallet.management.user.entity.User;

@Repository
public interface ManageUserRepository extends JpaRepository<User, Integer> {

}
