package com.example.Patients_Medicine_and_Appointment_System.Service;

import com.example.Patients_Medicine_and_Appointment_System.Entity.Medication;
import com.example.Patients_Medicine_and_Appointment_System.Repository.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MedicationServiceTest {

    private MedicationRepository medicationRepository;
    private MedicationService medicationService;

    @BeforeEach
    void setUp() {
        medicationRepository = mock(MedicationRepository.class);
        medicationService = new MedicationService();
        // Inject mock manually
        medicationService = new MedicationService() {{
            medicationRepository = MedicationServiceTest.this.medicationRepository;
        }};
    }

    @Test
    void testSaveMedication() {
        Medication med = new Medication();
        medicationService.saveMedication(med);
        verify(medicationRepository, times(1)).save(med);
    }

    @Test
    void testGetMedicationsByDoctor() {
        List<Medication> meds = List.of(new Medication(), new Medication());
        when(medicationRepository.findByPrescribedBy("Dr. Smith")).thenReturn(meds);

        List<Medication> result = medicationService.getMedicationsByDoctor("Dr. Smith");

        assertThat(result).hasSize(2);
        verify(medicationRepository).findByPrescribedBy("Dr. Smith");
    }

    @Test
    void testGetMedicationsByPatient() {
        List<Medication> meds = List.of(new Medication());
        when(medicationRepository.findByPatientName("Alice")).thenReturn(meds);

        List<Medication> result = medicationService.getMedicationsByPatient("Alice");

        assertThat(result).hasSize(1);
        verify(medicationRepository).findByPatientName("Alice");
    }

    @Test
    void testGetMedicationById_Success() {
        Medication med = new Medication();
        med.setId(1L);
        when(medicationRepository.findById(1L)).thenReturn(Optional.of(med));

        Medication result = medicationService.get(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void testGetMedicationById_NotFound() {
        when(medicationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> medicationService.get(99L));
    }

    @Test
    void testDeleteMedication() {
        medicationService.delete(5L);
        verify(medicationRepository, times(1)).deleteById(5L);
    }
}
