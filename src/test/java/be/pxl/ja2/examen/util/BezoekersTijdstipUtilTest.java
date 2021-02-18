package be.pxl.ja2.examen.util;

import be.pxl.ja2.examen.util.exception.OngeldigTijdstipException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class BezoekersTijdstipUtilTest {
    @Test
    public void controleerBezoekerTeVroegTest() throws OngeldigTijdstipException {
        LocalTime teVroeg = LocalTime.of(6,30);
        Assertions.assertThrows(OngeldigTijdstipException.class, () -> BezoekerstijdstipUtil.controleerBezoekerstijdstip(teVroeg));
    }

    @Test
    public void controleerBezoekerTeLaatTest() throws OngeldigTijdstipException {
        LocalTime teVroeg = LocalTime.of(22,30);
        Assertions.assertThrows(OngeldigTijdstipException.class, () -> BezoekerstijdstipUtil.controleerBezoekerstijdstip(teVroeg));
    }

    @Test
    public void controleerBezoekerBinnenBezoekuren() throws OngeldigTijdstipException {
        LocalTime teVroeg = LocalTime.of(8,30);
        Assertions.assertDoesNotThrow(() -> BezoekerstijdstipUtil.controleerBezoekerstijdstip(teVroeg));
    }

    @Test
    public void controleerAanmeldingstijdstipJuist() throws OngeldigTijdstipException{
        LocalDateTime aanmeldingsTijdstip = LocalDateTime.of(2020, Month.JUNE, 16, 14, 30,0);
        LocalDateTime registratieTijdstip = LocalDateTime.of(2020, Month.JUNE, 16, 14, 28,0);
        Assertions.assertDoesNotThrow(() -> BezoekerstijdstipUtil.controleerAanmeldingstijdstip(aanmeldingsTijdstip, registratieTijdstip.toLocalTime()));
    }

    @Test
    public void controleerAanmeldingstijdstipMeerDanKwartierTeVroeg() throws OngeldigTijdstipException{
        LocalDateTime aanmeldingsTijdstip = LocalDateTime.of(2020, Month.JUNE, 16, 14, 14,0);
        LocalDateTime registratieTijdstip = LocalDateTime.of(2020, Month.JUNE, 16, 14, 30,0);
        Assertions.assertThrows(OngeldigTijdstipException.class, () -> BezoekerstijdstipUtil.controleerAanmeldingstijdstip(aanmeldingsTijdstip, registratieTijdstip.toLocalTime()));
    }

    @Test
    public void controleerAanmeldingstijdstipMeerDanKwartierTeLaat() throws OngeldigTijdstipException{
        LocalDateTime aanmeldingsTijdstip = LocalDateTime.of(2020, Month.JUNE, 16, 14, 46,0);
        LocalDateTime registratieTijdstip = LocalDateTime.of(2020, Month.JUNE, 16, 14, 30,0);
        Assertions.assertThrows(OngeldigTijdstipException.class, () -> BezoekerstijdstipUtil.controleerAanmeldingstijdstip(aanmeldingsTijdstip, registratieTijdstip.toLocalTime()));
    }

}
