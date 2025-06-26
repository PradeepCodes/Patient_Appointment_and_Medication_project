package com.example.Patients_Medicine_and_Appointment_System.RestController;

import com.example.Patients_Medicine_and_Appointment_System.DTO.PatientRegistrationDto;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Patient;
import com.example.Patients_Medicine_and_Appointment_System.Service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatient();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient updated) {
        Patient p = patientService.getPatientById(id);
        p.setName(updated.getName());
        p.setEmail(updated.getEmail());
        return patientService.savePatient(p);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

}
