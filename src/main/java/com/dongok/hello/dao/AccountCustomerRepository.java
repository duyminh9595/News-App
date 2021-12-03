package com.dongok.hello.dao;

import com.dongok.hello.entity.AccountCustomer;
import com.dongok.hello.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path = "accountcustomer",collectionResourceRel = "accountcustomer")
@CrossOrigin
public interface AccountCustomerRepository extends JpaRepository<AccountCustomer,Long> {
    AccountCustomer findByEmail(@Param("email")String email);

    @Query("select p from AccountCustomer  p  where p.id=?1")
    AccountCustomer findDataById(Long id);
}
