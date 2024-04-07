package com.employee.SpringBootEmployee.service;

import com.employee.SpringBootEmployee.DAO.EmployeeRepository;
import com.employee.SpringBootEmployee.model.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(int id) {
        Optional<Employee> result = employeeRepository.findById(id);
        Employee emp=null;
        if(result.isPresent()){
            emp=result.get();
        } else {
            throw new RuntimeException("Employee not found : "+id);
        }
        return emp;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        Employee emp=getEmployeeById(id);
        employeeRepository.delete(emp);
    }
}
