package com.aayush.hrManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aayush.hrManagement.entity.Leave;
import com.aayush.hrManagement.service.LeaveService;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;
    
    @GetMapping("/records")
    public ResponseEntity<List<Leave>> getAllLeaveRecord() {
        List<Leave> leaveList = leaveService.getAll();
        if (leaveList == null || leaveList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(leaveList, HttpStatus.OK);
    }
    
    @PostMapping("/apply")
    public ResponseEntity<?> applyLeave(@RequestBody Leave leave) {
        Leave savedLeave = leaveService.addLeave(leave);
        if (savedLeave == null) {
            return new ResponseEntity<>("Leave could not be applied (invalid data or quota exceeded).", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);
    }

    @PutMapping("/updateLeave/{id}")
    public ResponseEntity<?> updateLeave(@PathVariable long id, @RequestBody Leave leave) {
        Leave updatedLeave = leaveService.updateDecisionById(id, leave);
        if (updatedLeave == null) {
            return new ResponseEntity<>("Leave record not found for ID: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedLeave, HttpStatus.OK);
    }
}
