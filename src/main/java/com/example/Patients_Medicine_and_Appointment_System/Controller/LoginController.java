package com.example.Patients_Medicine_and_Appointment_System.Controller;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@Controller
public class LoginController
{
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @GetMapping("/default")
    public String redirectByRole(Authentication authentication) {
        if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (authentication.getAuthorities().toString().contains("ROLE_DOCTOR")) {
            return "redirect:/doctor/dashboard";
        } else if (authentication.getAuthorities().toString().contains("ROLE_PATIENT")) {
            return "redirect:/patient/dashboard";
        }
        return "redirect:/login?error";
    }

}
