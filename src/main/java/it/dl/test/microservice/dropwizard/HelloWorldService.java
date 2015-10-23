package it.dl.test.microservice.dropwizard;

import com.netflix.config.*;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.netflix.hystrix.contrib.requestservlet.HystrixRequestContextServletFilter;
import com.netflix.hystrix.contrib.requestservlet.HystrixRequestLogViaResponseHeaderServletFilter;
import com.netflix.hystrix.contrib.yammermetricspublisher.HystrixYammerMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.wordnik.swagger.jaxrs.JaxrsApiReader;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import it.dl.test.microservice.dropwizard.api.SwaggerBundle;
import it.dl.test.microservice.dropwizard.cli.JobRunnerCommand;
import it.dl.test.microservice.dropwizard.configuration.CentralizedConfiguration;
import it.dl.test.microservice.dropwizard.health.TemplateHealthCheck;
import it.dl.test.microservice.dropwizard.resources.HelloWorldResource;
import it.dl.test.microservice.dropwizard.resources.PetResource;
import it.dl.test.microservice.dropwizard.resources.api.ApiListingResourceDefault;

public class HelloWorldService extends Service<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception {
        new HelloWorldService().run(args);
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        JaxrsApiReader.setFormatString("");
        bootstrap.setName("hello-world");

        //bundle
        bootstrap.addBundle(new SwaggerBundle());
        //command
        bootstrap.addCommand(new JobRunnerCommand<HelloWorldConfiguration>(this));

//        File propertiesFile = new File("file:/d:/tmp//test.properties");
//
//        // configuration from local properties file
//        ConcurrentMapConfiguration configFromPropertiesFile =
//                null;
//        try {
//            configFromPropertiesFile = new ConcurrentMapConfiguration(new PropertiesConfiguration(propertiesFile));
//        } catch (ConfigurationException e) {
//            e.printStackTrace();
//        }
//
////        // configuration from a dynamic source
////        PolledConfigurationSource source = createMyOwnSource();
////        AbstractPollingScheduler scheduler = createMyOwnScheduler();
////        DynamicConfiguration dynamicConfiguration =
////                new DynamicConfiguration(source, scheduler);
//
//        // create a hierarchy of configuration that makes
//        // 1) dynamic configuration source override system properties and,
//        // 2) system properties override properties file
//        ConcurrentCompositeConfiguration finalConfig = new ConcurrentCompositeConfiguration();
////        finalConfig.addDynamicConfiguration(dynamicConfiguration, "dynamicConfig");
//        finalConfig.addDynamicConfiguration(configFromPropertiesFile, "fileConfig");
//
//        ConfigurationManager.install(finalConfig);

        System.setProperty("archaius.dynamicPropertyFactory.registerConfigWithJMX", "true");

    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) {

        final String template = configuration.getTemplate();
        final String defaultName = configuration.getDefaultName();

//        addHystrixMetricsToMetrics();

        addHystrixMetricsPublishing(environment);

        addDynamicConfiguration(configuration);

        addHealtChecks(environment, template);

        addResources(environment, template, defaultName);

        addApi(environment);
    }

    private void addHealtChecks(Environment environment, String template) {
        environment.addHealthCheck(new TemplateHealthCheck(template));
    }

    private void addResources(Environment environment, String template, String defaultName) {
        environment.addResource(new HelloWorldResource(template, defaultName));
        environment.addResource(new PetResource());
    }

    private void addApi(Environment environment) {
        environment.addResource(new ApiListingResourceDefault());
    }

    private void addDynamicConfiguration(HelloWorldConfiguration configuration) {

        //TODO per ora se la config dinamica non viene caricata allora vincono i default ma non la config locale. Forse va bene così. Dovrebbe
        // essere che quella remota, a parte la prima volta se non riesce a caricare allora rimane come prima: come????

        PolledConfigurationSource source = new CentralizedConfiguration(configuration);
        AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(5000, 10000, true);
        DynamicConfiguration dynamicConfiguration = new DynamicConfiguration(source, scheduler);
        ConfigurationManager.install(dynamicConfiguration);
    }

    private void addHystrixMetricsPublishing(Environment environment) {
        environment.addFilter(HystrixRequestContextServletFilter.class, "/*");
        environment.addFilter(HystrixRequestLogViaResponseHeaderServletFilter.class, "/*");
        environment.addServlet(HystrixMetricsStreamServlet.class, "/hystrix.stream");
    }

    /**
     * Espone metriche dei comandi Hystrix sul MetricRegister di Yammer Metric, embedded in Dropwizard.
     */
    private void addHystrixMetricsToMetrics() {
        HystrixPlugins.getInstance().registerMetricsPublisher(new HystrixYammerMetricsPublisher());
    }

}
