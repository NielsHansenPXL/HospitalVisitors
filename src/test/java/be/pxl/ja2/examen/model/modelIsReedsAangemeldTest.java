package be.pxl.ja2.examen.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class modelIsReedsAangemeldTest {
    @Test
    public void ReedsAangemeldeBezoekerKanNietOpnieuwAanmelden(){
        LocalDateTime bezoekerDatum = LocalDateTime.of(2020,6,16,13,30);
        Bezoeker bezoeker = new Bezoeker();
        bezoeker.setAanmelding(bezoekerDatum);
        Assertions.assertTrue(bezoeker.isReedsAangemeld(bezoekerDatum.toLocalDate()));
    }
}
