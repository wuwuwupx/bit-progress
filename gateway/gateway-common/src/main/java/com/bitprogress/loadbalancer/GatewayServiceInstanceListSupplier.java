package com.bitprogress.loadbalancer;

import com.bitprogress.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.bitprogress.constant.GatewayConstants.APP_VERSION;
import static com.bitprogress.constant.GatewayConstants.VERSION;
import static com.bitprogress.util.CollectionUtils.filterList;
import static org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory.PROPERTY_NAME;

/**
 * @author wpx
 */
public class GatewayServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    /**
     * Property that establishes the timeout for calls to service discovery.
     */
    public static final String SERVICE_DISCOVERY_TIMEOUT = "spring.cloud.loadbalancer.service-discovery.timeout";

    private static final Logger log = LoggerFactory.getLogger(GatewayServiceInstanceListSupplier.class);

    private Duration timeout = Duration.ofSeconds(30);

    private final String serviceId;

    private final Flux<List<ServiceInstance>> serviceInstances;

    public GatewayServiceInstanceListSupplier(DiscoveryClient delegate, Environment environment) {
        this.serviceId = environment.getProperty(PROPERTY_NAME);
        resolveTimeout(environment);
        this.serviceInstances = Flux.defer(() -> Flux.just(delegate.getInstances(serviceId)))
                .subscribeOn(Schedulers.boundedElastic()).timeout(timeout, Flux.defer(() -> {
                    logTimeout();
                    return Flux.just(new ArrayList<>());
                })).onErrorResume(error -> {
                    logException(error);
                    return Flux.just(new ArrayList<>());
                });
    }


    public GatewayServiceInstanceListSupplier(ReactiveDiscoveryClient delegate, Environment environment) {
        this.serviceId = environment.getProperty(PROPERTY_NAME);
        resolveTimeout(environment);
        this.serviceInstances = Flux
                .defer(() -> delegate.getInstances(serviceId).collectList().flux().timeout(timeout, Flux.defer(() -> {
                    logTimeout();
                    return Flux.just(new ArrayList<>());
                })).onErrorResume(error -> {
                    logException(error);
                    return Flux.just(new ArrayList<>());
                }));
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }


    /**
     * 主要重写方法
     */
    @Override
    public Flux<List<ServiceInstance>> get(Request request) {
        RequestDataContext context = (RequestDataContext) request.getContext();
        String version = context.getClientRequest().getHeaders().getFirst(APP_VERSION);
        if (StringUtils.isEmpty(version)) {
            return serviceInstances;
        }
        return serviceInstances.map(instances -> filterList(instances, instance ->
                version.equals(instance.getMetadata().get(VERSION))));
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return serviceInstances;
    }

    private void resolveTimeout(Environment environment) {
        String providedTimeout = environment.getProperty(SERVICE_DISCOVERY_TIMEOUT);
        if (providedTimeout != null) {
            timeout = DurationStyle.detectAndParse(providedTimeout);
        }
    }

    private void logTimeout() {
        if (log.isDebugEnabled()) {
            log.debug(String.format("Timeout occurred while retrieving instances for service %s."
                    + "The instances could not be retrieved during %s", serviceId, timeout));
        }
    }

    private void logException(Throwable error) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("Exception occurred while retrieving instances for service %s", serviceId), error);
        }
    }

}
