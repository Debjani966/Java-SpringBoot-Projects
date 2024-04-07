package com.employee.SpringBootEmployee.service;

import com.employee.SpringBootEmployee.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployee();
    Employee updateEmployee(Employee employee);
    void deleteEmployee(int id);
}
