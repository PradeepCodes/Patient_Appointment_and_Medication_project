package com.example.Patients_Medicine_and_Appointment_System.Controller;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Appointment;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import com.example.Patients_Medicine_and_Appointment_System.Service.AdminService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Test
    public void testShowDashboard() throws Exception {
        List<Doctor> mockDoctors = List.of(new Doctor(1L, "Dr. John", "john@example.com", "Cardiology"));
        List<Appointment> mockAppointments = List.of(new Appointment());

        when(adminService.getAllDoctors()).thenReturn(mockDoctors);
        when(adminService.getAllAppointments()).thenReturn(mockAppointments);

        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_dashboard"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(model().attributeExists("appointments"));

        verify(adminService, times(1)).getAllDoctors();
        verify(adminService, times(1)).getAllAppointments();
    }

    @Test
    public void testAddDoctor() throws Exception {
        mockMvc.perform(post("/admin/doctors/add")
                        .param("name", "Dr. Smith")
                        .param("email", "smith@example.com")
                        .param("specialization", "Neurology"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard?docSuccess"));

        verify(adminService, times(1))
                .addDoctor("Dr. Smith", "smith@example.com", "Neurology");
    }
}
