package com.example.Patients_Medicine_and_Appointment_System.Repository;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findByEmail(String email);
}
