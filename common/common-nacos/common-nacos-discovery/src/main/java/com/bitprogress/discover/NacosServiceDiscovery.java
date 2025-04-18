package com.bitprogress.discover;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceInstance;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.bitprogress.property.ServerVersionProperties;
import com.bitprogress.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bitprogress.constant.NacosConstant.VERSION;

/**
 * 获取Nacos服务
 */
@AllArgsConstructor
public class NacosServiceDiscovery {

	private final ServerVersionProperties serverVersionProperties;
	private final NacosDiscoveryProperties discoveryProperties;

	private final NacosServiceManager nacosServiceManager;

	/**
	 * Return all instances for the given service.
	 * @param serviceId id of service
	 * @return list of instances
	 * @throws NacosException nacosException
	 */
	public List<ServiceInstance> getInstances(String serviceId) throws NacosException {
		String group = discoveryProperties.getGroup();
		List<Instance> instances = nacosServiceManager.getNamingService()
				.selectInstances(serviceId, group, true);
		return hostToServiceInstanceList(instances, serviceId);
	}

	/**
	 * Return the names of all services.
	 * @return list of service names
	 * @throws NacosException nacosException
	 */
	public List<String> getServices() throws NacosException {
		String group = discoveryProperties.getGroup();
		ListView<String> services = nacosServiceManager.getNamingService()
				.getServicesOfServer(1, Integer.MAX_VALUE, group);
		return services.getData();
	}

	public List<ServiceInstance> hostToServiceInstanceList(List<Instance> instances, String serviceId) {
		String targetVersion = serverVersionProperties.getServerVersionByServiceId(serviceId);
		List<ServiceInstance> result = new ArrayList<>(instances.size());
		for (Instance instance : instances) {
			ServiceInstance serviceInstance = hostToServiceInstance(instance, serviceId, targetVersion);
			if (serviceInstance != null) {
				result.add(serviceInstance);
			}
		}
		return result;
	}

	public static ServiceInstance hostToServiceInstance(Instance instance, String serviceId, String targetVersion) {
		if (instance == null || !instance.isEnabled() || !instance.isHealthy()) {
			return null;
		}
		// 如果指定了服务版本，则需要匹配对应版本的服务
		Map<String, String> instanceMetadata = instance.getMetadata();
		if (StringUtils.isNotEmpty(targetVersion)) {
			String version = instanceMetadata.get(VERSION);
			if (!StringUtils.equals(version, targetVersion)) {
				return null;
			}
		}

		NacosServiceInstance nacosServiceInstance = new NacosServiceInstance();
		nacosServiceInstance.setHost(instance.getIp());
		nacosServiceInstance.setPort(instance.getPort());
		nacosServiceInstance.setServiceId(serviceId);

		Map<String, String> metadata = new HashMap<>();
		metadata.put("nacos.instanceId", instance.getInstanceId());
		metadata.put("nacos.weight", instance.getWeight() + "");
		metadata.put("nacos.healthy", instance.isHealthy() + "");
		metadata.put("nacos.cluster", instance.getClusterName());
		metadata.putAll(instanceMetadata);
		nacosServiceInstance.setMetadata(metadata);

		if (metadata.containsKey("secure")) {
			boolean secure = Boolean.parseBoolean(metadata.get("secure"));
			nacosServiceInstance.setSecure(secure);
		}
		return nacosServiceInstance;
	}

}
