package com.example.Patients_Medicine_and_Appointment_System.Repository;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    @DisplayName("Should find doctor by email")
    void testFindByEmail() {
        // Given
        Doctor doctor = new Doctor();
        doctor.setName("Dr. Priya");
        doctor.setEmail("priya@example.com");
        doctor.setSpecialization("Cardiology");
        doctorRepository.save(doctor);

        // When
        Optional<Doctor> result = doctorRepository.findByEmail("priya@example.com");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Dr. Priya");
        assertThat(result.get().getSpecialization()).isEqualTo("Cardiology");
    }

    @Test
    @DisplayName("Should return empty if doctor email not found")
    void testFindByEmail_NotFound() {
        Optional<Doctor> result = doctorRepository.findByEmail("notfound@example.com");
        assertThat(result).isEmpty();
    }
}
