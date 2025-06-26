package com.example.Patients_Medicine_and_Appointment_System.Repository;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Appointment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    @DisplayName("Find appointments by patient name")
    void testFindByPatientName() {
        // Given
        Appointment a1 = Appointment.builder()
                .patientName("Alice")
                .doctorName("Dr. Smith")
                .category("Cardiology")
                .date("2025-06-27")
                .timeSlot("10:00 AM")
                .build();

        Appointment a2 = Appointment.builder()
                .patientName("Alice")
                .doctorName("Dr. Jones")
                .category("Neurology")
                .date("2025-06-28")
                .timeSlot("11:00 AM")
                .build();

        appointmentRepository.saveAll(List.of(a1, a2));

        // When
        List<Appointment> results = appointmentRepository.findByPatientName("Alice");

        // Then
        assertThat(results).hasSize(2);
        assertThat(results).extracting("doctorName")
                .containsExactlyInAnyOrder("Dr. Smith", "Dr. Jones");
    }
}
