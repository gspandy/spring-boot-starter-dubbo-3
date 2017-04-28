package com.alibaba.boot.dubbo;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ReferenceBean;

/**
 * DubboConsumerAutoConfiguration, use {@link Service#version} and {@link Service#timeout}
 * properties.
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(Service.class)
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class DubboConsumerAutoConfiguration {
    private final ConcurrentMap<String, ReferenceBean<?>> referenceConfigs =
            new ConcurrentHashMap<String, ReferenceBean<?>>();

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RegistryConfig registryConfig;

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName)
                    throws BeansException {
                Class<?> objClz = bean.getClass();
                if (org.springframework.aop.support.AopUtils.isAopProxy(bean)) {
                    objClz = org.springframework.aop.support.AopUtils.getTargetClass(bean);
                }

                try {
                    Field[] fields = objClz.getDeclaredFields();
                    for (Field field : fields) {
                        Reference reference = field.getAnnotation(Reference.class);
                        if (reference != null) {
                            if (!field.isAccessible()) {
                                field.setAccessible(true);
                            }
                            Object value = refer(field.getType(), reference);
                            if (value != null) {
                                field.set(bean, value);
                            }
                            field.setAccessible(false);
                        }
                    }
                } catch (Exception e) {
                    throw new BeanCreationException(beanName, e);
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName)
                    throws BeansException {
                return bean;
            }
        };
    }

    private Object refer(Class<?> referenceClass, Reference reference) {
        String interfaceName;
        if (!"".equals(reference.interfaceName())) {
            interfaceName = reference.interfaceName();
        } else if (!void.class.equals(reference.interfaceClass())) {
            interfaceName = reference.interfaceClass().getName();
        } else if (referenceClass.isInterface()) {
            interfaceName = referenceClass.getName();
        } else {
            throw new IllegalStateException(
                    "The @Reference undefined interfaceClass or interfaceName, and the property type "
                            + referenceClass.getName() + " is not a interface.");
        }
        String key = reference.group() + "/" + interfaceName + ":" + reference.version();
        ReferenceBean<?> referenceConfig = referenceConfigs.get(key);
        if (referenceConfig == null) {
            referenceConfig = new ReferenceBean<Object>(reference);
            if (void.class.equals(reference.interfaceClass())
                    && "".equals(reference.interfaceName()) && referenceClass.isInterface()) {
                referenceConfig.setInterface(referenceClass);
            }
            if (applicationContext != null) {
                referenceConfig.setApplicationContext(applicationContext);
                // 单注册中心
                referenceConfig.setRegistry(this.registryConfig);
                // 多注册中心
                // if (reference.registry() != null && reference.registry().length > 0) {
                // List<RegistryConfig> registryConfigs = new ArrayList<RegistryConfig>();
                // for (String registryId : reference.registry()) {
                // if (registryId != null && registryId.length() > 0) {
                // registryConfigs.add((RegistryConfig)applicationContext.getBean(registryId,
                // RegistryConfig.class));
                // }
                // }
                // referenceConfig.setRegistries(registryConfigs);
                // }
                // if (reference.consumer() != null && reference.consumer().length() > 0) {
                // referenceConfig.setConsumer((ConsumerConfig)applicationContext.getBean(reference.consumer(),
                // ConsumerConfig.class));
                // }
                // if (reference.monitor() != null && reference.monitor().length() > 0) {
                // referenceConfig.setMonitor((MonitorConfig)applicationContext.getBean(reference.monitor(),
                // MonitorConfig.class));
                // }
                referenceConfig.setApplication(this.applicationConfig);
                // if (reference.application() != null && reference.application().length() > 0) {
                // referenceConfig.setApplication((ApplicationConfig)applicationContext.getBean(reference.application(),
                // ApplicationConfig.class));
                // }
                // if (reference.module() != null && reference.module().length() > 0) {
                // referenceConfig.setModule((ModuleConfig)applicationContext.getBean(reference.module(),
                // ModuleConfig.class));
                // }
                // if (reference.consumer() != null && reference.consumer().length() > 0) {
                // referenceConfig.setConsumer((ConsumerConfig)applicationContext.getBean(reference.consumer(),
                // ConsumerConfig.class));
                // }
                try {
                    referenceConfig.afterPropertiesSet();
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }
            referenceConfigs.putIfAbsent(key, referenceConfig);
            referenceConfig = referenceConfigs.get(key);
        }
        return referenceConfig.get();
    }
}
