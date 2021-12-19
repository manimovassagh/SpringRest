package com.github.manimovassagh.ownapi.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.manimovassagh.ownapi.model.Staff;
import com.github.manimovassagh.ownapi.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;


    @GetMapping("/staffs")
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @GetMapping("/staffs/{id}")
    public ResponseEntity<Staff> getEmployeeById(@PathVariable Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(staff);
    }


    @PostMapping("/staffs")
    public Staff createEmployee(@RequestBody Staff staff) {
        return staffRepository.save(staff);
    }



    @RequestMapping()
    @PutMapping("/staffs/{id}")
    public ResponseEntity<Staff> updateEmployee(@PathVariable Long id, @RequestBody Staff employeeDetails) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        // staffRepository.save(employeeDetails);
        staff.setFirstName(employeeDetails.getFirstName());
        staff.setLastName(employeeDetails.getLastName());
        staff.setEmail_id(employeeDetails.getEmail_id());

        Staff updatedEmployee = staffRepository.save(staff);
        return ResponseEntity.ok(updatedEmployee);
    }

    // delete Staff rest api
    @DeleteMapping("/Staffs/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Staff employee = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        staffRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
