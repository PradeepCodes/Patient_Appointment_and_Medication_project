package com.example.Patients_Medicine_and_Appointment_System.Service;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Medication;
import com.example.Patients_Medicine_and_Appointment_System.Repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {
    @Autowired
    private MedicationRepository medicationRepository;

    public void saveMedication(Medication medication) {
        medicationRepository.save(medication);
    }
    public List<Medication> getMedicationsByDoctor(String doctor) {
        return medicationRepository.findByPrescribedBy(doctor);
    }

    public List<Medication> getMedicationsByUserName(String patientName) {
        return medicationRepository.findByPatientName(patientName);
    }
    public List<Medication> getMedicationsByPatient(String patientName) {
        return medicationRepository.findByPatientName(patientName);
    }
    public List<Medication> getMedicationsAll() {
        return medicationRepository.findAll();
    }
    public List<Medication> listByDoctor(String prescribedBy) {
        return medicationRepository.findByPrescribedBy(prescribedBy);
    }

    /** List all medications for a given patient */
    public List<Medication> listByPatientName(String patientName) {
        return medicationRepository.findByPatientName(patientName);
    }

    /** Fetch one medication record by its ID */
    public Medication get(Long id) {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found: " + id));
    }

    /** Create or update a medication record */
    public Medication save(Medication med) {
        return medicationRepository.save(med);
    }


    /** Delete a medication record */
    public void delete(Long id) {
        medicationRepository.deleteById(id);
    }
}
