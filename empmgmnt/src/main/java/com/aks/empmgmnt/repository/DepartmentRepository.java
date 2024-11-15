package com.aks.empmgmnt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aks.empmgmnt.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	Optional<Department> findBydeptName(String name);
}
