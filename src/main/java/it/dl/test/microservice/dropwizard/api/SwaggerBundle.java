package it.dl.test.microservice.dropwizard.api;

import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;

public class SwaggerBundle extends AssetsBundle {

    public static final String DEFAULT_PATH = "/rest-ui";

    public SwaggerBundle() {
        super(DEFAULT_PATH, DEFAULT_PATH);
    }

}
