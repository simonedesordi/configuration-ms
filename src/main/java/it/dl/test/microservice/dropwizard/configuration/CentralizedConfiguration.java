package it.dl.test.microservice.dropwizard.configuration;

import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import it.dl.test.microservice.dropwizard.HelloWorldConfiguration;

import java.util.HashMap;
import java.util.Map;

public class CentralizedConfiguration implements PolledConfigurationSource {

    private HelloWorldConfiguration configuration;

    public CentralizedConfiguration(HelloWorldConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public PollResult poll(boolean first, Object checkPoint) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        System.out.println("[poll] ricarico configurazione centralizzata");
        retrieveConfiguration(map);

        return PollResult.createFull(map);
    }

    private void retrieveConfiguration(Map<String, Object> map) {
        //TODO caricare config da fonte custom, magari dal servizio stesso /pet
        map.put("name", "Centralized");
    }
}
