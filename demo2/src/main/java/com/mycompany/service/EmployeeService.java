package com.mycompany.service;

import com.mycompany.dto.EmployeeDTO;
import com.mycompany.model.Employee;

public interface EmployeeService {
    public com.mycompany.dto.EmployeeDTO getOneEmployee();
    public EmployeeDTO showOneEmployee1(Long id);
    public Employee deleteOneEmployee(Long id);
    public Employee updateEmployee(Long id);


}
