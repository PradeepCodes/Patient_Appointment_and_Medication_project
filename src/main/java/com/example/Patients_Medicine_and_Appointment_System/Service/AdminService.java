package com.example.Patients_Medicine_and_Appointment_System.Service;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Appointment;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import com.example.Patients_Medicine_and_Appointment_System.Repository.AppointmentRepository;
import com.example.Patients_Medicine_and_Appointment_System.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public void addDoctor(String name, String email, String specialization) {
        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setEmail(email);
        doctor.setSpecialization(specialization);
        doctorRepository.save(doctor);
    }
}
