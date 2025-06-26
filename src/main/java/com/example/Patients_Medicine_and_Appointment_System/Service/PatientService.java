package com.example.Patients_Medicine_and_Appointment_System.Service;

import com.example.Patients_Medicine_and_Appointment_System.DTO.PatientRegistrationDto;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Doctor;
import com.example.Patients_Medicine_and_Appointment_System.Entity.Patient;
import com.example.Patients_Medicine_and_Appointment_System.Repository.DoctorRepository;
import com.example.Patients_Medicine_and_Appointment_System.Repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }
    public Patient getByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No patient: " + email));
    }
    @Transactional
    public void registerNewPatient(PatientRegistrationDto dto) {
        if (patientRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        Patient p = new Patient();
        p.setName(dto.getName());
        p.setEmail(dto.getEmail());
        p.setContact(dto.getContact());
        p.setMedicalHistory(dto.getMedicalHistory());
        p.setPassword(passwordEncoder.encode(dto.getPassword()));
        patientRepository.save(p);
    }
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + id));
    }
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new IllegalArgumentException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
}
