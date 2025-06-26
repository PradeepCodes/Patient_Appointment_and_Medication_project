package com.example.Patients_Medicine_and_Appointment_System.Service;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Appointment;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Medication;
import com.example.Patients_Medicine_and_Appointment_System.Repository.AppointmentRepository;
import com.example.Patients_Medicine_and_Appointment_System.Repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAppointmentByPatient(String patientName) {
        return appointmentRepository.findByPatientName(patientName);
    }
}
