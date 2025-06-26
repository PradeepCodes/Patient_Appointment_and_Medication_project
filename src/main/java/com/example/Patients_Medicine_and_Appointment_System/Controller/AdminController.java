package com.example.Patients_Medicine_and_Appointment_System.Controller;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Appointment;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import com.example.Patients_Medicine_and_Appointment_System.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Doctor> doctors = adminService.getAllDoctors();
        List<Appointment> appointments = adminService.getAllAppointments();
        model.addAttribute("doctors", doctors);
        model.addAttribute("appointments", appointments);
        return "admin_dashboard";
    }

    @PostMapping("/doctors/add")
    public String addDoctor(@RequestParam String name,
                            @RequestParam String email,
                            @RequestParam String specialization
                           ) {
        adminService.addDoctor(name, email, specialization);
        return "redirect:/admin/dashboard?docSuccess";
    }
}
