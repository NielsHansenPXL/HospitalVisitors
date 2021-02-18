package be.pxl.ja2.examen.dao;

import be.pxl.ja2.examen.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientDao extends JpaRepository<Patient, String> {
    Patient findPatientByCode(String code);
    List<Patient> findPatientsByAfdeling_Code(String code);
}
