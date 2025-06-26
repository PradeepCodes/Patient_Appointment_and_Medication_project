package com.example.Patients_Medicine_and_Appointment_System.Service;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import com.example.Patients_Medicine_and_Appointment_System.Repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    private DoctorRepository doctorRepository;
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        doctorRepository = mock(DoctorRepository.class);
        doctorService = new DoctorService() {{
            doctorRepository = DoctorServiceTest.this.doctorRepository;
        }};
    }

    @Test
    void testGetAllDoctors() {
        // Given
        List<Doctor> mockDoctors = List.of(new Doctor(), new Doctor());
        when(doctorRepository.findAll()).thenReturn(mockDoctors);

        // When
        List<Doctor> result = doctorService.getAllDoctors();

        // Then
        assertThat(result).hasSize(2);
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testFindByEmail_Success() {
        // Given
        Doctor doc = new Doctor();
        doc.setEmail("doc@example.com");
        when(doctorRepository.findByEmail("doc@example.com")).thenReturn(Optional.of(doc));

        // When
        Doctor result = doctorService.findByEmail("doc@example.com");

        // Then
        assertThat(result).isEqualTo(doc);
        verify(doctorRepository, times(1)).findByEmail("doc@example.com");
    }

    @Test
    void testFindByEmail_NotFound() {
        // Given
        when(doctorRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        // Then
        assertThrows(UsernameNotFoundException.class, () ->
                doctorService.findByEmail("missing@example.com"));
    }
}
