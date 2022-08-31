package com.mycompany.controller;


import com.mycompany.dto.EmployeeDTO;
import com.mycompany.exception.ResourceNotFoundException;
import com.mycompany.model.AuthenticationRequest;
import com.mycompany.model.AuthenticationResponse;
import com.mycompany.model.Employee;
import com.mycompany.repository.EmployeeRepository;
import com.mycompany.service.EmployeeService;
import com.mycompany.service.MyUserDetailsService;
import com.mycompany.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getAllUser")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    @GetMapping("/getOneUser")
    public EmployeeDTO getOneEmployee(){
        return employeeService.getOneEmployee();
    }
   @GetMapping("/showOneUser/{id}")
    public EmployeeDTO showOneUser(@PathVariable Long id){
       return employeeService.showOneEmployee1(id);
    }

    @DeleteMapping("/deleteOneUser/{id}")
    public Employee deleteOneEmployee(@PathVariable Long id){
        return employeeService.deleteOneEmployee(id);
    }
    @PutMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable Long id){
        return employeeService.updateEmployee(id);
    }

    @RequestMapping("/hello")
    public String hello(){
            return "hellospringsecurity";
    }

    @RequestMapping(value="/authenticate",method=RequestMethod.POST)
    public ResponseEntity<?>createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
     try{
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
     }catch (BadCredentialsException e){
         throw new Exception("Incorrect username or password",e);
     }
     final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
     final String jwt=jwtUtil.generateToken(userDetails);
     return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    //build create employee rest api
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    //build get employee by rest api
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id" + id));
        return ResponseEntity.ok(employee);
    }

    //build update employee by rest api
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails){
        Employee updateEmployee=employeeRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id"+id));
    updateEmployee.setFirstName(employeeDetails.getFirstName());
    updateEmployee.setLastName(employeeDetails.getLastName());
    updateEmployee.setEmailId(employeeDetails.getEmailId());

    employeeRepository.save(updateEmployee);
    return ResponseEntity.ok(updateEmployee);

    }

    // build delete employee rest api
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not exist"+id));
        employeeRepository.delete(employee);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}