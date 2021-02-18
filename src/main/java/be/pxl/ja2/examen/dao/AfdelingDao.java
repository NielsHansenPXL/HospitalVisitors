package be.pxl.ja2.examen.dao;

import be.pxl.ja2.examen.model.Afdeling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AfdelingDao extends JpaRepository<Afdeling, String> {
}
