package com.alibaba.boot.dubbo;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ServiceBean;

/**
 * DubboProviderAutoConfiguration
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(Service.class)
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class DubboProviderAutoConfiguration {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private ProtocolConfig protocolConfig;
    @Autowired
    private RegistryConfig registryConfig;

    @PostConstruct
    public void init() throws Exception {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Service.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            this.initProviderBean(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 注册服务及暴露：http://dubbo.io/User+Guide-zh.htm#UserGuide-zh-API%E9%85%8D%E7%BD%AE
     */
    public void initProviderBean(String beanName, Object bean) throws Exception {
        Service service = this.applicationContext.findAnnotationOnBean(beanName, Service.class);
        if (service != null) {
            ServiceBean<Object> serviceConfig = new ServiceBean<Object>(service);
            if (void.class.equals(service.interfaceClass()) && "".equals(service.interfaceName())) {
                if (bean.getClass().getInterfaces().length > 0) {
                    serviceConfig.setInterface(bean.getClass().getInterfaces()[0]);
                } else {
                    throw new IllegalStateException("Failed to export remote service class "
                            + bean.getClass().getName()
                            + ", cause: The @Service undefined interfaceClass or interfaceName, and the service class unimplemented any interfaces.");
                }
            }
            if (this.applicationContext != null) {
                serviceConfig.setApplicationContext(this.applicationContext);
                // 单注册中心
                serviceConfig.setRegistry(this.registryConfig);
                // 多注册中心
                // if (service.registry() != null && service.registry().length > 0) {
                // List<RegistryConfig> registryConfigs = new ArrayList<RegistryConfig>();
                // for (String registryId : service.registry()) {
                // if (registryId != null && registryId.length() > 0) {
                // registryConfigs.add((RegistryConfig)applicationContext.getBean(registryId,
                // RegistryConfig.class));
                // }
                // }
                // serviceConfig.setRegistries(registryConfigs);
                // }

                serviceConfig.setApplication(this.applicationConfig);
                // if (service.provider() != null && service.provider().length() > 0) {
                // serviceConfig.setProvider((ProviderConfig)applicationContext.getBean(service.provider(),ProviderConfig.class));
                // }
                // if (service.monitor() != null && service.monitor().length() > 0) {
                // serviceConfig.setMonitor((MonitorConfig)applicationContext.getBean(service.monitor(),
                // MonitorConfig.class));
                // }
                // if (service.module() != null && service.module().length() > 0) {
                // serviceConfig.setModule((ModuleConfig)applicationContext.getBean(service.module(),
                // ModuleConfig.class));
                // }

                // 单协议
                serviceConfig.setProtocol(this.protocolConfig);
                // 多协议
                // if (service.protocol() != null && service.protocol().length > 0) {
                // List<ProtocolConfig> protocolConfigs = new ArrayList<ProtocolConfig>();
                // for (String protocolId : service.registry()) {
                // if (protocolId != null && protocolId.length() > 0) {
                // protocolConfigs.add((ProtocolConfig)applicationContext.getBean(protocolId,
                // ProtocolConfig.class));
                // }
                // }
                // serviceConfig.setProtocols(protocolConfigs);
                // }
                try {
                    serviceConfig.afterPropertiesSet();
                } catch (RuntimeException e) {
                    throw (RuntimeException) e;
                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }
            serviceConfig.setRef(bean);
            // 注册服务及暴露
            serviceConfig.export();
        }
    }

}
