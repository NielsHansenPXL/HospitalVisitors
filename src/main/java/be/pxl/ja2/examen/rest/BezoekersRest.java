package be.pxl.ja2.examen.rest;

import be.pxl.ja2.examen.rest.resources.RegistreerBezoekerResource;
import be.pxl.ja2.examen.service.BezoekersService;
import be.pxl.ja2.examen.util.exception.UnkownVisitorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(path = "bezoekers")
public class BezoekersRest {
	@Autowired
	BezoekersService bezoekersService;

	private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHH:mm");
	private static final Logger LOGGER = LogManager.getLogger(BezoekersRest.class);

	// TODO Add Rest endpoints
	// Hint: gebruik ResponseEntity<Long> als return-type voor het rest endpoint om een bezoeker te registreren

	@PostMapping
	public ResponseEntity<Long> registreerBezoeker(@RequestBody RegistreerBezoekerResource registreerBezoekerResource){
		try {
			Long bezoekerId = bezoekersService.registreerBezoeker(registreerBezoekerResource);
			return new ResponseEntity<>(bezoekerId, HttpStatus.ACCEPTED);
		} catch (Exception e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/{bezoekerId}/{entranceTimeStamp}")
	public HttpStatus controleerToegang(@PathVariable("bezoekerId") String id, @PathVariable("entranceTimeStamp") String entranceTimestamp){
		Long convertedId = Long.parseLong(id);
		LocalDateTime converterdDateTime = LocalDateTime.parse(entranceTimestamp, TIMESTAMP_FORMAT);

		try {
			bezoekersService.controleerBezoek(convertedId, converterdDateTime);
			return HttpStatus.OK;
		} catch(Exception | UnkownVisitorException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
