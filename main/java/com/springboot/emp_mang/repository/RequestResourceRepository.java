package com.springboot.emp_mang.repository;



import com.springboot.emp_mang.entities.RequestResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestResourceRepository extends JpaRepository<RequestResource, Long> {

}
