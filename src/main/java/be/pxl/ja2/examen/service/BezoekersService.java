package be.pxl.ja2.examen.service;

import be.pxl.ja2.examen.dao.BezoekerDao;
import be.pxl.ja2.examen.dao.PatientDao;
import be.pxl.ja2.examen.model.Afdeling;
import be.pxl.ja2.examen.model.Bezoeker;
import be.pxl.ja2.examen.model.Patient;
import be.pxl.ja2.examen.rest.resources.RegistreerBezoekerResource;
import be.pxl.ja2.examen.util.BezoekerstijdstipUtil;
import be.pxl.ja2.examen.util.exception.UnkownVisitorException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BezoekersService {
	@Autowired
	private PatientDao patientDao;
	@Autowired
	private BezoekerDao bezoekerDao;

	private static final Logger LOGGER = LogManager.getLogger(BezoekersService.class);
	public static final int BEZOEKERS_PER_TIJDSTIP_PER_AFDELING = 2;

	public Long registreerBezoeker(RegistreerBezoekerResource registreerBezoekerResource) throws Exception {
		if(registreerBezoekerResource.getNaam() == null || registreerBezoekerResource.getNaam().equals("")){
			throw new Exception("Geen naam ingevuld");
		} else if(registreerBezoekerResource.getVoornaam() == null || registreerBezoekerResource.getVoornaam().equals("")){
			throw new Exception("Geen voornaam ingevuld");
		} else if (registreerBezoekerResource.getTelefoonnummer() == null || registreerBezoekerResource.getTelefoonnummer().equals("")){
			throw new Exception("Geen telefoonnummer ingegeven");
		} else if (registreerBezoekerResource.getTijdstip() == null){
			throw new Exception("Geen tijdstip ingegeven");
		} else if (registreerBezoekerResource.getPatientCode() == null || registreerBezoekerResource.getPatientCode().equals("")){
			throw new Exception("Geen patientcode ingegeven");
		} else if (patientNietGekend(registreerBezoekerResource.getPatientCode())){
			throw new Exception("Deze patient is niet gekend");
		} else if(bezoekerGeregistreerd(registreerBezoekerResource.getPatientCode())){
			throw new Exception("Er is reeds een bezoeker voor deze patient geregistreerd");
		} else if (meerDanTweeBezoekersPerAfdelingOpDezelfdeTijd(registreerBezoekerResource.getPatientCode(), registreerBezoekerResource.getTijdstip())){
			throw new Exception("Kies een ander tijdstip");
		}
		BezoekerstijdstipUtil.controleerBezoekerstijdstip(registreerBezoekerResource.getTijdstip());

		Bezoeker newBezoeker = mapBezoeker(registreerBezoekerResource);
		Patient patient = patientDao.findPatientByCode(registreerBezoekerResource.getPatientCode());
		newBezoeker.setPatient(patient);
		newBezoeker = bezoekerDao.save(newBezoeker);
		return newBezoeker.getId();
	}

	public void controleerBezoek(Long bezoekerId, LocalDateTime aanmelding) throws Exception, UnkownVisitorException {
		Bezoeker controleBezoeker =  bezoekerDao.findBezoekerById(bezoekerId);
		if(controleBezoeker == null){
			throw new UnkownVisitorException("Deze bezoeker is niet gekend");
		}
		if(controleBezoeker.isReedsAangemeld(aanmelding.toLocalDate())) {
			throw new Exception("Deze bezoeker werd op deze datum reeds geregistreerd");
		} else{
			BezoekerstijdstipUtil.controleerAanmeldingstijdstip(aanmelding, controleBezoeker.getTijdstip());
			controleBezoeker.setAanmelding(aanmelding);
			bezoekerDao.save(controleBezoeker);
		}
	}

	public List<Bezoeker> getBezoekersVoorAfdeling(String afdelingCode) {
		List<Bezoeker> bezoekersPerAfdeling = new ArrayList<>();
		List<Patient> patientsByAfdeling = patientDao.findPatientsByAfdeling_Code(afdelingCode);
		for (Patient patient: patientsByAfdeling) {
			Bezoeker bezoeker = bezoekerDao.findBezoekerByPatientCode(patient.getCode());
			if(bezoeker != null){
				bezoekersPerAfdeling.add(bezoeker);
			}
		}
		return bezoekersPerAfdeling;
	}

	private static Bezoeker mapBezoeker (RegistreerBezoekerResource registreerBezoekerResource){
		Bezoeker bezoeker = new Bezoeker();
		bezoeker.setNaam(registreerBezoekerResource.getNaam());
		bezoeker.setVoornaam(registreerBezoekerResource.getVoornaam());
		bezoeker.setTelefoonnummer(registreerBezoekerResource.getTelefoonnummer());
		bezoeker.setTijdstip(registreerBezoekerResource.getTijdstip());
		//bezoeker.setPatient(patient);
		return bezoeker;
	}

	private boolean patientNietGekend(String code) {
		Patient patient = patientDao.findPatientByCode(code);
		if(patient == null){
			return true;
		}
		return false;
	}

	private boolean bezoekerGeregistreerd(String code) {
		Bezoeker bezoeker = bezoekerDao.findBezoekerByPatientCode(code);
		if(bezoeker != null){
			return true;
		}
		return false;
	}


	private boolean meerDanTweeBezoekersPerAfdelingOpDezelfdeTijd(String code, LocalTime time) {
		int controleAfdeling = 0;
		Patient newPatient = patientDao.findPatientByCode(code);
		Afdeling afdelingNewPatient = newPatient.getAfdeling();

		List<Bezoeker> allBezoekersOpZelfdeTijdstip = bezoekerDao.findBezoekersByTijdstip(time);
		for (Bezoeker bezoeker : allBezoekersOpZelfdeTijdstip) {
			Patient controlePatient = bezoeker.getPatient();
			if(controlePatient.getAfdeling() == afdelingNewPatient){
				controleAfdeling++;
				if(controleAfdeling >= BEZOEKERS_PER_TIJDSTIP_PER_AFDELING){
					return true;
				}
			}
		}
		return false;
	}
}
