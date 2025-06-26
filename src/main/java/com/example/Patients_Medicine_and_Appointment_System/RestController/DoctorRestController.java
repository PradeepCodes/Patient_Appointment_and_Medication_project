package com.example.Patients_Medicine_and_Appointment_System.RestController;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import com.example.Patients_Medicine_and_Appointment_System.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorRestController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Doctor getDoctor(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor updated) {
        Doctor doc = doctorService.getDoctorById(id);
        doc.setName(updated.getName());
        doc.setEmail(updated.getEmail());
        doc.setSpecialization(updated.getSpecialization());
        return doctorService.saveDoctor(doc);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}
