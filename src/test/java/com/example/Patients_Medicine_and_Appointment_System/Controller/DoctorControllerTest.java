package com.example.Patients_Medicine_and_Appointment_System.Controller;

import com.example.Patients_Medicine_and_Appointment_System.Entity.*;
import com.example.Patients_Medicine_and_Appointment_System.Repository.AppointmentRepository;
import com.example.Patients_Medicine_and_Appointment_System.Service.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicationService medicationService;

    @MockBean
    private AppointmentRepository appointmentRepository;

    @MockBean
    private PatientService patientService;

    @MockBean
    private DoctorService doctorService;

    @Test
    void testShowDoctorDashboard() throws Exception {
        when(doctorService.getAllDoctors()).thenReturn(List.of(new Doctor()));
        when(medicationService.getMedicationsAll()).thenReturn(List.of(new Medication()));
        when(appointmentRepository.findAll()).thenReturn(List.of(new Appointment()));
        when(patientService.getAllPatient()).thenReturn(List.of(new Patient()));

        mockMvc.perform(get("/doctor/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor_dashboard"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(model().attributeExists("medications"))
                .andExpect(model().attributeExists("appointments"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    void testAddMedication() throws Exception {
        mockMvc.perform(post("/doctor/medications/add")
                        .param("patientName", "John")
                        .param("medication", "Paracetamol")
                        .param("dosage", "500mg")
                        .param("prescribedBy", "Dr. Smith"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor/dashboard?medSuccess"));

        ArgumentCaptor<Medication> medCaptor = ArgumentCaptor.forClass(Medication.class);
        verify(medicationService).saveMedication(medCaptor.capture());

        Medication savedMed = medCaptor.getValue();
        assertThat(savedMed.getPatientName()).isEqualTo("John");
        assertThat(savedMed.getMedication()).isEqualTo("Paracetamol");
        assertThat(savedMed.getDosage()).isEqualTo("500mg");
        assertThat(savedMed.getPrescribedBy()).isEqualTo("Dr. Smith");
    }

    @Test
    void testEditMedicationForm() throws Exception {
        Medication med = new Medication();
        med.setId(1L);
        when(medicationService.get(1L)).thenReturn(med);
        when(patientService.getAllPatient()).thenReturn(List.of(new Patient()));
        when(doctorService.getAllDoctors()).thenReturn(List.of(new Doctor()));

        mockMvc.perform(get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("medications/form"))
                .andExpect(model().attributeExists("medication"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("doctors"));
    }

    @Test
    void testDeleteMedication() throws Exception {
        doNothing().when(medicationService).delete(1L);

        mockMvc.perform(get("/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor_dashboard?medDeleted"));

        verify(medicationService, times(1)).delete(1L);
    }


}
