package com.mycompany.service;

import com.mycompany.dto.Address;
import com.mycompany.dto.EmployeeDTO;
import com.mycompany.model.Employee;
import com.mycompany.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;


    @Override
    public com.mycompany.dto.EmployeeDTO getOneEmployee() {
        com.mycompany.dto.EmployeeDTO employeeDTO = new com.mycompany.dto.EmployeeDTO();
        employeeDTO.setName("Fatma");
        employeeDTO.setSur_name("Demirci");
        employeeDTO.setEmails(List.of("aa@gmail","fatma@gmail.com"));

//        List<String> emailList = new ArrayList<>();
//        emailList.add("aa@gmail");
//        emailList.add("fatma@gmail.com");
//        employeeDTO.setEmails(emailList);

        List<Address> addressList= new ArrayList<>();
        addressList.add(new Address("Ankara","0311"));
        addressList.add(new Address("Istanbul","34"));
        employeeDTO.setAddressList(addressList);

        return employeeDTO;
    }

    @Override
    public EmployeeDTO showOneEmployee1(Long id){
        if (id == null)
            throw new RuntimeException("Lütfen id giriniz.");
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null)
            throw new RuntimeException("Çalışan bulunamadı.");
        EmployeeDTO employeeDTO = new com.mycompany.dto.EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getFirstName());
        employeeDTO.setSur_name(employee.getLastName());
        employeeDTO.setEmails(List.of(employee.getEmailId()));
        return employeeDTO;
    }
    @Override
    public Employee deleteOneEmployee(Long id){
        Employee employee=employeeRepository.findById(id).orElse(null);
        if (employee == null)
            throw new RuntimeException("Çalışan bulunamadı.");
        employeeRepository.delete(employee);
        return employee;
    }
    @Override
    public Employee updateEmployee(Long id){
        Employee employee=employeeRepository.findById(id).orElse(null);
        if (employee == null)
            throw new RuntimeException("Çalışan bulunamadı.");
        employee.setFirstName("Fatmaİnci");
        employeeRepository.save(employee);
        return employee;
    }





}













