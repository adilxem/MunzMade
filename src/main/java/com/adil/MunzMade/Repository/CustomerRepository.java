package com.adil.MunzMade.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adil.MunzMade.Model.Customer;
import com.adil.MunzMade.Model.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {


    Page<Customer> findByUser(User user, Pageable pageable);


    @Query("SELECT c FROM Customer c WHERE c.user.id = :userId")
    List<Customer> findByUserId(@Param("userId") String userId);

    Page<Customer> findByUserAndNameContaining(User user, String nameKeyword, Pageable pageable);
    Page<Customer> findByUserAndEmailContaining(User user, String emailKeyword, Pageable pageable);
    Page<Customer> findByUserAndPhoneNumberContaining(User user, String phoneKeyword, Pageable pageable);

}
