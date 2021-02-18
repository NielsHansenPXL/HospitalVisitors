package be.pxl.ja2.examen.dao;

import be.pxl.ja2.examen.model.Bezoeker;
import be.pxl.ja2.examen.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BezoekerDao extends JpaRepository<Bezoeker, Long> {
    Bezoeker findBezoekerByPatientCode(String code);
    List<Bezoeker> findBezoekersByTijdstip(LocalTime tijdstip);
    Bezoeker findBezoekerById(Long id);
}
