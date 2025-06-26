package com.example.Patients_Medicine_and_Appointment_System.Service;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Appointment;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import com.example.Patients_Medicine_and_Appointment_System.Repository.AppointmentRepository;
import com.example.Patients_Medicine_and_Appointment_System.Repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class AdminServiceTest {

    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        doctorRepository = mock(DoctorRepository.class);
        appointmentRepository = mock(AppointmentRepository.class);
        adminService = new AdminService();
        // Inject mocks manually since we're not using @InjectMocks
        adminService = new AdminService() {{
            // Using anonymous subclass for simple injection
            doctorRepository = AdminServiceTest.this.doctorRepository;
            appointmentRepository = AdminServiceTest.this.appointmentRepository;
        }};
    }

    @Test
    void testGetAllDoctors() {
        List<Doctor> dummyDoctors = List.of(new Doctor(), new Doctor());
        when(doctorRepository.findAll()).thenReturn(dummyDoctors);

        List<Doctor> result = adminService.getAllDoctors();

        assertThat(result).hasSize(2);
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testGetAllAppointments() {
        List<Appointment> dummyAppointments = List.of(new Appointment());
        when(appointmentRepository.findAll()).thenReturn(dummyAppointments);

        List<Appointment> result = adminService.getAllAppointments();

        assertThat(result).hasSize(1);
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void testAddDoctor() {
        adminService.addDoctor("Dr. Smith", "smith@example.com", "Cardiology");

        ArgumentCaptor<Doctor> doctorCaptor = ArgumentCaptor.forClass(Doctor.class);
        verify(doctorRepository).save(doctorCaptor.capture());

        Doctor savedDoctor = doctorCaptor.getValue();
        assertThat(savedDoctor.getName()).isEqualTo("Dr. Smith");
        assertThat(savedDoctor.getEmail()).isEqualTo("smith@example.com");
        assertThat(savedDoctor.getSpecialization()).isEqualTo("Cardiology");
    }
}
