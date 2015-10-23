package it.dl.test.microservice.dropwizard.cli;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.cli.EnvironmentCommand;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobRunnerCommand<T extends Configuration> extends EnvironmentCommand<T> {
    private final Class<T> configurationClass;

    public JobRunnerCommand(Service<T> service) {
        super(service, "run-job", "Runs the job");
        this.configurationClass = service.getConfigurationClass();
    }

    /*
     * Since we don't subclass JobRunnerCommand, we need a concrete reference to the configuration
     * class.
     */
    @Override
    protected Class<T> getConfigurationClass() {
        return configurationClass;
    }

    @Override
    protected void run(Environment environment, Namespace namespace, T configuration) throws Exception {

        final Logger logger = LoggerFactory.getLogger(JobRunnerCommand.class);

        // TODO run something, it could be a batch
        System.out.println("[run] GO !!");

    }

}
