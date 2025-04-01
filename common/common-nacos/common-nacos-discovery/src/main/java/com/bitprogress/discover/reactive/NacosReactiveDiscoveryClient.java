package com.bitprogress.discover.reactive;

import com.alibaba.nacos.api.exception.NacosException;
import com.bitprogress.discover.NacosServiceDiscovery;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

/**
 * @author <a href="mailto:echooy.mxq@gmail.com">echooymxq</a>
 **/
public class NacosReactiveDiscoveryClient implements ReactiveDiscoveryClient {

	private static final Logger log = LoggerFactory
			.getLogger(NacosReactiveDiscoveryClient.class);

	private NacosServiceDiscovery serviceDiscovery;

	public NacosReactiveDiscoveryClient(NacosServiceDiscovery nacosServiceDiscovery) {
		this.serviceDiscovery = nacosServiceDiscovery;
	}

	@Override
	public String description() {
		return "Spring Cloud Nacos Reactive Discovery Client";
	}

	@Override
	public Flux<ServiceInstance> getInstances(String serviceId) {

		return Mono.justOrEmpty(serviceId).flatMapMany(loadInstancesFromNacos())
				.subscribeOn(Schedulers.boundedElastic());
	}

	private Function<String, Publisher<ServiceInstance>> loadInstancesFromNacos() {
		return serviceId -> {
			try {
				return Flux.fromIterable(serviceDiscovery.getInstances(serviceId));
			}
			catch (NacosException e) {
				log.error("get service instance[{}] from nacos error!", serviceId, e);
				return Flux.empty();
			}
		};
	}

	@Override
	public Flux<String> getServices() {
		return Flux.defer(() -> {
			try {
				return Flux.fromIterable(serviceDiscovery.getServices());
			}
			catch (Exception e) {
				log.error("get services from nacos server fail,", e);
				return Flux.empty();
			}
		}).subscribeOn(Schedulers.boundedElastic());
	}

}
