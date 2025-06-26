package com.example.Patients_Medicine_and_Appointment_System.Repository;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Medication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MedicationRepositoryTest {

    @Autowired
    private MedicationRepository medicationRepository;

    @Test
    @DisplayName("Should find medications by patient name")
    void testFindByPatientName() {
        // Given
        Medication m1 = new Medication();
        m1.setPatientName("Alice");
        m1.setMedication("Paracetamol");
        m1.setDosage("500mg");
        m1.setPrescribedBy("Dr. John");

        Medication m2 = new Medication();
        m2.setPatientName("Alice");
        m2.setMedication("Ibuprofen");
        m2.setDosage("200mg");
        m2.setPrescribedBy("Dr. Jane");

        medicationRepository.saveAll(List.of(m1, m2));

        // When
        List<Medication> results = medicationRepository.findByPatientName("Alice");

        // Then
        assertThat(results).hasSize(2);
        assertThat(results).extracting("medication").contains("Paracetamol", "Ibuprofen");
    }

    @Test
    @DisplayName("Should find medications by doctor name")
    void testFindByPrescribedBy() {
        // Given
        Medication m1 = new Medication();
        m1.setPatientName("Bob");
        m1.setMedication("Aspirin");
        m1.setDosage("100mg");
        m1.setPrescribedBy("Dr. John");

        medicationRepository.save(m1);

        // When
        List<Medication> results = medicationRepository.findByPrescribedBy("Dr. John");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getMedication()).isEqualTo("Aspirin");
    }
}
