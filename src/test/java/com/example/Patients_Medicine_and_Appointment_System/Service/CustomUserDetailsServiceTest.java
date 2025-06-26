package com.example.Patients_Medicine_and_Appointment_System.Service;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Patient;
import com.example.Patients_Medicine_and_Appointment_System.Repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private PatientRepository patientRepository;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        patientRepository = mock(PatientRepository.class);
        userDetailsService = new CustomUserDetailsService();
        // Manual injection since we're not using @InjectMocks
        userDetailsService = new CustomUserDetailsService() {{
            patientRepository = CustomUserDetailsServiceTest.this.patientRepository;
        }};
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Given
        Patient patient = new Patient();
        patient.setEmail("john@example.com");
        patient.setPassword("securePassword");
        when(patientRepository.findByEmail("john@example.com")).thenReturn(Optional.of(patient));

        // When
        UserDetails result = userDetailsService.loadUserByUsername("john@example.com");

        // Then
        assertThat(result).isInstanceOf(CustomUserDetails.class);
        assertThat(result.getUsername()).isEqualTo("john@example.com");
        assertThat(result.getPassword()).isEqualTo("securePassword");
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        // Given
        when(patientRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        // Then
        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("missing@example.com"));
    }
}
