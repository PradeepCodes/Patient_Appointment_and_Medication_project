package com.example.Patients_Medicine_and_Appointment_System.RestController;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Appointment;
import com.example.Patients_Medicine_and_Appointment_System.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentRestController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable Long id) {
        return appointmentRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment updated) {
        Appointment appt = appointmentRepository.findById(id).orElseThrow();
        appt.setCategory(updated.getCategory());
        appt.setDate(updated.getDate());
        appt.setTimeSlot(updated.getTimeSlot());
        appt.setPatientName(updated.getPatientName());
        return appointmentRepository.save(appt);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
    }
}
