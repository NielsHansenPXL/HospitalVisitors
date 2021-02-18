package be.pxl.ja2.examen.rest.resources;

import java.time.LocalTime;

public class RegistreerBezoekerResource {
	private String patientCode;
	private LocalTime tijdstip;
	private String naam;
	private String voornaam;
	private String telefoonnummer;

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public LocalTime getTijdstip() {
		return tijdstip;
	}

	public void setTijdstip(LocalTime tijdstip) {
		this.tijdstip = tijdstip;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getTelefoonnummer() {
		return telefoonnummer;
	}

	public void setTelefoonnummer(String telefoonnummer) {
		this.telefoonnummer = telefoonnummer;
	}
}
