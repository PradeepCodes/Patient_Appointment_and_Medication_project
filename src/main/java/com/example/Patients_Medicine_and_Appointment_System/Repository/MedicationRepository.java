package com.example.Patients_Medicine_and_Appointment_System.Repository;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication,Long> {
    List<Medication> findByPatientName(String patientName);
    List<Medication> findByPrescribedBy(String prescribedBy);
   }
