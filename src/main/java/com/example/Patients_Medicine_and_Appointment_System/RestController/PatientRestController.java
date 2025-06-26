package com.example.Patients_Medicine_and_Appointment_System.RestController;

import com.example.Patients_Medicine_and_Appointment_System.DTO.PatientRegistrationDto;
import com.example.Patients_Medicine_and_Appointment_System.Service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
public class PatientRestController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody PatientRegistrationDto dto) {
        try {
            patientService.registerNewPatient(dto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Patient registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
}
