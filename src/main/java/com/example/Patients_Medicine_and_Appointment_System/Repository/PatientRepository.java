package com.example.Patients_Medicine_and_Appointment_System.Repository;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    boolean existsByEmail(String email);
}
