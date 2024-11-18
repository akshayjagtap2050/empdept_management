package com.aks.empmgmnt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aks.empmgmnt.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByempEmail(String name);

}
