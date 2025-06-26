package com.example.Patients_Medicine_and_Appointment_System.Controller;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Patient;
import com.example.Patients_Medicine_and_Appointment_System.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient_register";
    }

    @PostMapping("/register")
    public String registerPatient(@ModelAttribute Patient patient) {
        // Encrypt the password before saving
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patientRepository.save(patient);
        return "redirect:/login?registered";
    }
}
