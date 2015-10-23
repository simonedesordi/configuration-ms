package it.dl.test.microservice.dropwizard.resources;

import com.google.common.collect.ImmutableSet;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.examples.demo.HystrixCommandDemo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import it.dl.test.microservice.dropwizard.core.Pet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Path("/pet")
@Api("/pet")
@Produces(MediaType.APPLICATION_JSON)
public class PetResource {

    @GET
    @ApiOperation("Retrieve a list of pets")
    public Set<Pet> list() {

        try {

            new HystrixCommandDemo().executeSimulatedUserRequestForOrderConfirmationAndCreditCardPayment();
//            HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString();

            System.out.println("Request => " + HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString());

//            for (HystrixCommandMetrics commandMetrics : HystrixCommandMetrics.getInstances()) {
//                HystrixCommandKey key = commandMetrics.getCommandKey();
//                HystrixCircuitBreaker circuitBreaker = HystrixCircuitBreaker.Factory.getInstance(key);
//
//                System.out.println("Key => " + key.name());
//                System.out.println("Group => " + commandMetrics.getCommandGroup().name());
//
//                // rolling counters
//                System.out.println("rollingCountCollapsedRequests " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.COLLAPSED));
//                System.out.println("rollingCountExceptionsThrown " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.EXCEPTION_THROWN));
//                System.out.println("rollingCountFailure " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.FAILURE));
//                System.out.println("rollingCountFallbackFailure " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.FALLBACK_FAILURE));
//                System.out.println("rollingCountFallbackRejection " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.FALLBACK_REJECTION));
//                System.out.println("rollingCountFallbackSuccess " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.FALLBACK_SUCCESS));
//                System.out.println("rollingCountResponsesFromCache " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.RESPONSE_FROM_CACHE));
//                System.out.println("rollingCountSemaphoreRejected " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.SEMAPHORE_REJECTED));
//                System.out.println("rollingCountShortCircuited " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.SHORT_CIRCUITED));
//                System.out.println("rollingCountSuccess " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.SUCCESS));
//                System.out.println("rollingCountThreadPoolRejected " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.THREAD_POOL_REJECTED));
//                System.out.println("rollingCountTimeout " + commandMetrics.getRollingCount(HystrixRollingNumberEvent.TIMEOUT));
//
//                System.out.println("currentConcurrentExecutionCount " + commandMetrics.getCurrentConcurrentExecutionCount());
//
//                // latency percentiles
//                System.out.println("latencyExecute_mean " + commandMetrics.getExecutionTimeMean());
//                System.out.println("0 " + commandMetrics.getExecutionTimePercentile(0));
//                System.out.println("25 " + commandMetrics.getExecutionTimePercentile(25));
//                System.out.println("50 " + commandMetrics.getExecutionTimePercentile(50));
//                System.out.println("75 " + commandMetrics.getExecutionTimePercentile(75));
//                System.out.println("90 " + commandMetrics.getExecutionTimePercentile(90));
//                System.out.println("95 " + commandMetrics.getExecutionTimePercentile(95));
//                System.out.println("99 " + commandMetrics.getExecutionTimePercentile(99));
//                System.out.println("99.5 " + commandMetrics.getExecutionTimePercentile(99.5));
//                System.out.println("100 " + commandMetrics.getExecutionTimePercentile(100));
//
//            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ImmutableSet.of(
                new Pet("Dog", 4),
                new Pet("Cat", 4),
                new Pet("Chicken", 2)
        );
    }
}
