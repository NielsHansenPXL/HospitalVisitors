package be.pxl.ja2.examen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BezoekersApplication {

	public static void main(String[] args) {
		SpringApplication.run(BezoekersApplication.class, args);
	}

}
