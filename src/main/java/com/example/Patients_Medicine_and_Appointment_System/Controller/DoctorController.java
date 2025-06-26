package com.example.Patients_Medicine_and_Appointment_System.Controller;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Appointment;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Medication;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Patient;
import com.example.Patients_Medicine_and_Appointment_System.Repository.AppointmentRepository;
import com.example.Patients_Medicine_and_Appointment_System.Service.DoctorService;
import com.example.Patients_Medicine_and_Appointment_System.Service.MedicationService;
import com.example.Patients_Medicine_and_Appointment_System.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;



    @GetMapping("/doctor/dashboard")
    public String showDoctorDashboard(Model model, Principal principal) {

        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);

        List<Medication> meds = medicationService.getMedicationsAll();
        model.addAttribute("medications", meds);

        List<Appointment> appointments = appointmentRepository.findAll(); // You can filter by patient later
        model.addAttribute("appointments", appointments);

        List<Patient> patients = patientService.getAllPatient(); // You can filter by patient later
        model.addAttribute("patients", patients);
        return "doctor_dashboard";
    }

    @PostMapping("/doctor/medications/add")
    public String addMedication(
            @RequestParam String patientName,
            @RequestParam String medication,
            @RequestParam String dosage,
            @RequestParam String prescribedBy
            // ← injected by Spring Security
    ) {
        // Build and save
        Medication med = new Medication();
        med.setPatientName(patientName);
        med.setMedication(medication);
        med.setDosage(dosage);
        med.setPrescribedBy(prescribedBy);
        medicationService.saveMedication(med);

        return "redirect:/doctor/dashboard?medSuccess";
    }

    @GetMapping("/doctor/medications/edit/{id}")
    public String editMedicationForm(@PathVariable Long id, Model model) {
        Medication med = medicationService.get(id);
        model.addAttribute("medication", med);
        model.addAttribute("patients", patientService.getAllPatient());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "medications/form"; // You should have this HTML page
    }

    @GetMapping("/doctor/medications/delete/{id}")
    public String deleteMedication(@PathVariable Long id) {
        medicationService.delete(id);
        return "redirect:/doctor/dashboard?medDeleted";
    }

    @PostMapping("/doctor/medications/update/{id}")
    public String updateMedication(@PathVariable Long id,
                                   @RequestParam String patientName,
                                   @RequestParam String medication,
                                   @RequestParam String dosage,
                                   @RequestParam String prescribedBy) {
        Medication med = medicationService.get(id);
        med.setPatientName(patientName);
        med.setMedication(medication);
        med.setDosage(dosage);
        med.setPrescribedBy(prescribedBy);
        medicationService.saveMedication(med);
        return "redirect:/doctor/dashboard?medUpdated";
    }
}
