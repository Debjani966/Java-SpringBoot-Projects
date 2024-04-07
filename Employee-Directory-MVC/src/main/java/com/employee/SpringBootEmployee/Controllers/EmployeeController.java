package com.employee.SpringBootEmployee.Controllers;

import com.employee.SpringBootEmployee.model.Employee;
import com.employee.SpringBootEmployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/list")
    public String displayAllEmployee(Model model)
    {
        List<Employee> employeeList=employeeService.getAllEmployee();
        model.addAttribute("employees",employeeList);
        return "/employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model)
    {
        Employee employee=new Employee();
        model.addAttribute("employee",employee);
        return "/employees/showFormForAdd";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee emp)
    {
        employeeService.saveEmployee((emp));

        //prevents duplicate subbmission
        return "redirect:/employees/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId,
                                    Model model)
    {
        Employee employee=employeeService.getEmployeeById(theId);
        model.addAttribute("employee",employee);
        return "/employees/showFormForAdd";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int id)
    {
        employeeService.deleteEmployee(id);
        return "redirect:/employees/list";
    }
}
