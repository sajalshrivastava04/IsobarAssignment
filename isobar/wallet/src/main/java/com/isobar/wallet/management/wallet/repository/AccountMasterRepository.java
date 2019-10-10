package com.isobar.wallet.management.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isobar.wallet.management.wallet.entity.AccountMaster;
@Repository
public interface AccountMasterRepository extends JpaRepository<AccountMaster, Integer> {

}
