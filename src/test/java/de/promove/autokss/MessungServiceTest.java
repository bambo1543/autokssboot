package de.promove.autokss;

import de.promove.autokss.model.*;
import de.promove.autokss.service.MessungService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.ParseException;

public class MessungServiceTest {

    private Logger logger = LoggerFactory.getLogger(MessungServiceTest.class);

    private MessungService service = new MessungService();
    private Einsatzkonzentration ek0 = new Einsatzkonzentration("0", 5D, 8D, 6.5D);
    private Einsatzkonzentration ek1 = new Einsatzkonzentration("1", 7D, 8D, 7.5D);
    private Einsatzkonzentration ek2 = new Einsatzkonzentration("2", 9D, 10D, 9.5D);
    private Einsatzkonzentration ek3 = new Einsatzkonzentration("3", 5D, 10D, 7D);

    private Maschine dmc64V = new Maschine(ek1, 650D, 50D);
    private Maschine hermleC400U = new Maschine(ek1, 485D, 60D);
    private Maschine nef720 = new Maschine(ek0, 175D, 13D);
    private Maschine ctx310 = new Maschine(ek0, 175D, 15D);

    @Test
    public void testCalculateRefill() throws ParseException {
        testRefill("04.04.2022", dmc64V, 44D, 8.2D, 78D, 1.85D);
        testRefill("28.03.2022", dmc64V, 47D, 8.1D, 39D, -0.74D);
        testRefill("04.04.2022", ctx310, 11D, 8.6D, 46.67D, 0.34D);
    }

    private void testRefill(String datum, Maschine maschine, Double wasserstandCm, Double rm1, Double wasserNachgefuellt, Double oelNachgefuellt) throws ParseException {
        Messung m1 = new Messung(DateFormat.getDateInstance().parse(datum), null, maschine);
        m1.setWasserstandCm(wasserstandCm);
        m1.setRm1(rm1);

        service.calculateRefill(m1);

        logger.info("Wasser nachgefuellt: " + m1.getWasserNachgefuellt() + " vs " + wasserNachgefuellt);
        Assert.isTrue(Math.round(m1.getWasserNachgefuellt() * 100D) / 100D == wasserNachgefuellt);

        logger.info("Oel nachgefuellt: " + m1.getOelNachgefuellt() + " vs " + oelNachgefuellt);
        Assert.isTrue(Math.round(m1.getOelNachgefuellt() * 100D) / 100D == oelNachgefuellt);
    }


}
