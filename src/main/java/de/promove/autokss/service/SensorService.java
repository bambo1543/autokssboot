package de.promove.autokss.service;

import de.promove.autokss.model.Distance;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SensorService extends GenericService {

    @Getter
    private Map<String, String> macToIpMap = new HashMap<>();

    @Getter
    private Map<String, List<Distance>> distances = new HashMap<>();

}
