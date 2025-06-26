package com.example.Patients_Medicine_and_Appointment_System.Controller;


import com.example.Patients_Medicine_and_Appointment_System.Entity.*;
import com.example.Patients_Medicine_and_Appointment_System.Repository.AppointmentRepository;
import com.example.Patients_Medicine_and_Appointment_System.Service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private MedicationService medicationService;

    @MockBean
    private PatientService patientService;

    @MockBean
    private AppointmentRepository appointmentRepository;

    @Test
    void testShowAppointmentForm() throws Exception {
        List<Doctor> mockDoctors = List.of(new Doctor(1L, "Dr. Smith", "smith@example.com", "Cardiology"));
        when(doctorService.getAllDoctors()).thenReturn(mockDoctors);

        mockMvc.perform(get("/patient/appointments/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointment_register"))
                .andExpect(model().attributeExists("doctors"));

        verify(doctorService, times(1)).getAllDoctors();
    }

    @Test
    void testSaveAppointment() throws Exception {
        mockMvc.perform(post("/appointments")
                        .param("category", "Cardiology")
                        .param("doctorName", "Dr. Smith")
                        .param("date", "2025-06-27")
                        .param("timeSlot", "10:00 AM - 11:00 AM")
                        .param("patientName", "John Doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/dashboard?success"));

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    @WithMockUser(username = "john@example.com")
    void testShowDashboard() throws Exception {
        // Mock principal
        String email = "john@example.com";
        Patient patient = new Patient();
        patient.setName("John Doe");
        patient.setEmail(email);

        when(patientService.getByEmail(email)).thenReturn(patient);
        when(medicationService.getMedicationsByPatient("John Doe")).thenReturn(List.of());
        when(appointmentService.getAppointmentByPatient("John Doe")).thenReturn(List.of());
        when(doctorService.getAllDoctors()).thenReturn(List.of());

        mockMvc.perform(get("/patient/dashboard").principal(() -> email))
                .andExpect(status().isOk())
                .andExpect(view().name("patient_dashboard"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("medications"))
                .andExpect(model().attributeExists("appointments"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(model().attribute("patientName", "John Doe"));
    }
}
