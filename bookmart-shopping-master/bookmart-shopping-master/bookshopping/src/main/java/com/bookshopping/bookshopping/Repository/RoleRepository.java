package com.bookshopping.bookshopping.Repository;

import com.bookshopping.bookshopping.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

Role findByName(String name);}
