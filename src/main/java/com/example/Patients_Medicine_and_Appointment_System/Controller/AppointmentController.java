package com.example.Patients_Medicine_and_Appointment_System.Controller;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Appointment;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Medication;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Patient;
import com.example.Patients_Medicine_and_Appointment_System.Repository.AppointmentRepository;
import com.example.Patients_Medicine_and_Appointment_System.Service.AppointmentService;
import com.example.Patients_Medicine_and_Appointment_System.Service.DoctorService;
import com.example.Patients_Medicine_and_Appointment_System.Service.MedicationService;
import com.example.Patients_Medicine_and_Appointment_System.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/patient/appointments/register")
    public String showAppointmentForm(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "appointment_register";
    }

    @PostMapping("/appointments")
    public String saveAppointment(@RequestParam String category,
                                  @RequestParam String doctorName,
                                  @RequestParam String date,
                                  @RequestParam String timeSlot,
                                  @RequestParam String patientName,
                                  Model model) {
        Appointment appointment = Appointment.builder()
                .patientName(patientName)
                .category(category)
                .doctorName(doctorName)
                .date(date)
                .timeSlot(timeSlot)
                .build();

        appointmentRepository.save(appointment);

        return "redirect:/patient/dashboard?success";
    }
    @GetMapping("/patient/dashboard")
    public String showDashboard(Model model, Principal principal) {

        String email= principal.getName();
        Patient patient = patientService.getByEmail(email);
        model.addAttribute("patient", patient);
        model.addAttribute("patientName", patient.getName());

        List<Medication> medications = medicationService
                .getMedicationsByPatient(patient.getName());
        model.addAttribute("medications", medications);

        List<Appointment> appointments = appointmentService.getAppointmentByPatient(patient.getName()); // You can filter by patient later
        model.addAttribute("appointments", appointments);

        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);

        return "patient_dashboard";
    }

}
