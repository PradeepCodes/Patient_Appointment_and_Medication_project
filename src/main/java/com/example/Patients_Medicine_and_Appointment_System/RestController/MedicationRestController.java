package com.example.Patients_Medicine_and_Appointment_System.RestController;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Medication;
import com.example.Patients_Medicine_and_Appointment_System.Service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/medications")
public class MedicationRestController {
    @Autowired
    private MedicationService medicationService;

    @GetMapping
    public List<Medication> getAllMedications() {
        return medicationService.getMedicationsAll();
    }

    @GetMapping("/{id}")
    public Medication getMedication(@PathVariable Long id) {
        return medicationService.get(id);
    }

    @PostMapping
    public Medication createMedication(@RequestBody Medication med) {
        return medicationService.save(med);
    }

    @PutMapping("/{id}")
    public Medication updateMedication(@PathVariable Long id, @RequestBody Medication updated) {
        Medication med = medicationService.get(id);
        med.setPatientName(updated.getPatientName());
        med.setMedication(updated.getMedication());
        med.setDosage(updated.getDosage());
        med.setPrescribedBy(updated.getPrescribedBy());
        return medicationService.save(med);
    }

    @DeleteMapping("/{id}")
    public void deleteMedication(@PathVariable Long id) {
        medicationService.delete(id);
    }
}
