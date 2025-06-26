package com.example.Patients_Medicine_and_Appointment_System.Service;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Appointment;
import com.example.Patients_Medicine_and_Appointment_System.Repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void testGetAppointmentByPatient() {
        // Given
        String patientName = "John Doe";
        Appointment appt1 = new Appointment();
        appt1.setPatientName(patientName);
        Appointment appt2 = new Appointment();
        appt2.setPatientName(patientName);

        List<Appointment> mockAppointments = List.of(appt1, appt2);
        when(appointmentRepository.findByPatientName(patientName)).thenReturn(mockAppointments);

        // When
        List<Appointment> result = appointmentService.getAppointmentByPatient(patientName);

        // Then
        assertThat(result).hasSize(2);
        verify(appointmentRepository, times(1)).findByPatientName(patientName);
    }
}
