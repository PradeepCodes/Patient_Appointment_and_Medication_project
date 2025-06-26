package com.example.Patients_Medicine_and_Appointment_System.Service;

import com.example.Patients_Medicine_and_Appointment_System.DTO.PatientRegistrationDto;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Patient;
import com.example.Patients_Medicine_and_Appointment_System.Repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    private PatientRepository patientRepository;
    private PasswordEncoder passwordEncoder;
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        patientRepository = mock(PatientRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        patientService = new PatientService();
        // Inject manually
        patientService = new PatientService() {{
            patientRepository = PatientServiceTest.this.patientRepository;
            passwordEncoder = PatientServiceTest.this.passwordEncoder;
        }};
    }

    @Test
    void testGetAllPatient() {
        List<Patient> patients = List.of(new Patient(), new Patient());
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.getAllPatient();

        assertThat(result).hasSize(2);
        verify(patientRepository).findAll();
    }

    @Test
    void testGetByEmail_Success() {
        Patient patient = new Patient();
        patient.setEmail("test@example.com");
        when(patientRepository.findByEmail("test@example.com")).thenReturn(Optional.of(patient));

        Patient result = patientService.getByEmail("test@example.com");

        assertThat(result).isEqualTo(patient);
    }

    @Test
    void testGetByEmail_NotFound() {
        when(patientRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                patientService.getByEmail("missing@example.com"));
    }

    @Test
    void testRegisterNewPatient_Success() {
        PatientRegistrationDto dto = new PatientRegistrationDto();
        dto.setName("John Doe");
        dto.setEmail("john@example.com");
        dto.setContact("1234567890");
        dto.setMedicalHistory("None");
        dto.setPassword("password");

        when(patientRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        patientService.registerNewPatient(dto);

        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void testRegisterNewPatient_EmailExists() {
        PatientRegistrationDto dto = new PatientRegistrationDto();
        dto.setEmail("already@used.com");

        when(patientRepository.existsByEmail("already@used.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                patientService.registerNewPatient(dto));
    }
}
