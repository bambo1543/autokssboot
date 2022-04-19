package de.promove.autokss.service;

import de.promove.autokss.model.Einsatzkonzentration;
import de.promove.autokss.model.Maschine;
import de.promove.autokss.model.Messung;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessungService {

    public void calculateRefill(Messung messung) {
        Maschine maschine = messung.getMaschine();

        Double tankVolumenLiter = maschine.getTankVolumenLiter();
        Double maxWasser = maschine.getWasserstandMaxCm();
        Double gemWasser = messung.getWasserstandCm();
        Double wasserIst = tankVolumenLiter * gemWasser / maxWasser;

        Einsatzkonzentration einsatzkonzentration = maschine.getEinsatzkonzentration();
        Double konzSoll = einsatzkonzentration.getSoll();
        Double konzIst = messung.getRm1();
        Double oelSoll = tankVolumenLiter * konzSoll / 100;

        Double oelIst = wasserIst * konzIst / 100;
//        Double oelDiff = Math.round(oelSoll - oelIst, 2);
        Double oelDiff = oelSoll - oelIst;
        messung.setOelNachgefuellt(oelDiff);
        Double wasserDiff = tankVolumenLiter - wasserIst;
        messung.setWasserNachgefuellt(wasserDiff);
    }

}
