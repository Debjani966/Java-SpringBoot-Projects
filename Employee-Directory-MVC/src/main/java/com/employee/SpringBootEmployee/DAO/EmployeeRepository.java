package com.employee.SpringBootEmployee.DAO;

import com.employee.SpringBootEmployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    //add a method to sort by last name
    public List<Employee> findAllByOrderByLastNameAsc();
}
