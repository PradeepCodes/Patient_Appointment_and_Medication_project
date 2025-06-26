package com.example.Patients_Medicine_and_Appointment_System.Repository;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    @DisplayName("Should find patient by email")
    void testFindByEmail() {
        // Given
        Patient patient = new Patient();
        patient.setName("Arjun");
        patient.setEmail("arjun@example.com");
        patient.setContact("9876543210");
        patient.setPassword("encoded-password");
        patient.setMedicalHistory("None");

        patientRepository.save(patient);

        // When
        Optional<Patient> result = patientRepository.findByEmail("arjun@example.com");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Arjun");
    }

    @Test
    @DisplayName("Should return empty if patient email not found")
    void testFindByEmail_NotFound() {
        Optional<Patient> result = patientRepository.findByEmail("unknown@example.com");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return true if email exists")
    void testExistsByEmail_True() {
        Patient patient = new Patient();
        patient.setName("Meena");
        patient.setEmail("meena@example.com");
        patient.setContact("9998887776");
        patient.setPassword("securepass");
        patient.setMedicalHistory("Diabetes");

        patientRepository.save(patient);

        boolean exists = patientRepository.existsByEmail("meena@example.com");
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false if email does not exist")
    void testExistsByEmail_False() {
        boolean exists = patientRepository.existsByEmail("ghost@example.com");
        assertThat(exists).isFalse();
    }
}
