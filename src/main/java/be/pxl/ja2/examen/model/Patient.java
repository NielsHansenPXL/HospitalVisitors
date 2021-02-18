package be.pxl.ja2.examen.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="patient")
public class Patient {
	//PK
	@Id
	private String code;
	private LocalDateTime opname;
	//FK
	@ManyToOne
	private Afdeling afdeling;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getOpname() {
		return opname;
	}

	public void setOpname(LocalDateTime opname) {
		this.opname = opname;
	}

	public Afdeling getAfdeling() {
		return afdeling;
	}

	public void setAfdeling(Afdeling afdeling) {
		this.afdeling = afdeling;
	}
}
