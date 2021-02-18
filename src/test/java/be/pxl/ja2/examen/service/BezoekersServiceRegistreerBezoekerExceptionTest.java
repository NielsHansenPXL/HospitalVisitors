package be.pxl.ja2.examen.service;

import be.pxl.ja2.examen.dao.BezoekerDao;
import be.pxl.ja2.examen.dao.PatientDao;
import be.pxl.ja2.examen.model.Bezoeker;
import be.pxl.ja2.examen.model.Patient;
import be.pxl.ja2.examen.rest.resources.RegistreerBezoekerResource;
import be.pxl.ja2.examen.util.BezoekerstijdstipUtil;
import be.pxl.ja2.examen.util.exception.BezoekersAppException;
import be.pxl.ja2.examen.util.exception.OngeldigTijdstipException;
import be.pxl.ja2.examen.util.exception.UnkownVisitorException;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.AtLeast;
import org.mockito.internal.verification.Times;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BezoekersServiceRegistreerBezoekerExceptionTest {
    @Mock
    private BezoekerDao bezoekerDao;

    @Mock
    private PatientDao patientDao;

    @InjectMocks
    private BezoekersService bezoekersService;

    @BeforeEach
    public void init() throws BezoekersAppException, OngeldigTijdstipException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void controleerBezoekIsNullTest() throws Exception {
        when(bezoekerDao.findBezoekerById(any())).thenReturn(null);
        Long longId = 50L;
        Assertions.assertThrows(UnkownVisitorException.class, () -> bezoekersService.controleerBezoek(longId, LocalDateTime.of(2020,6,16,15,30)));
    }

    @Test
    public void getBezoekersVoorAfdelingVerifyAdd() throws Exception, UnkownVisitorException {
        Bezoeker bezoeker1 = new Bezoeker();
        bezoeker1.setNaam("Hansen");

        Patient patient1 = new Patient();
        Patient patient2 = new Patient();
        patient1.setCode("P001");
        patient2.setCode("P003");

        List<Patient> patientsList = new ArrayList<>();
        patientsList.add(patient1);
        patientsList.add(patient2);

        when(bezoekerDao.findBezoekerByPatientCode(any())).thenReturn(bezoeker1);
        when(patientDao.findPatientsByAfdeling_Code(any())).thenReturn(patientsList);
        List<Bezoeker> controleList = bezoekersService.getBezoekersVoorAfdeling("KRA");

        Assertions.assertEquals(2, controleList.size());
    }
}
