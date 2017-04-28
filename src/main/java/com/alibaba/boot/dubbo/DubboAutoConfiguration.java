package com.alibaba.boot.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.boot.dubbo.properties.ApplicationProperties;
import com.alibaba.boot.dubbo.properties.ProtocolProperties;
import com.alibaba.boot.dubbo.properties.RegistryProperties;
import com.alibaba.boot.dubbo.util.DubboUtils;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/**
 * Dubbo common configuration
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties({ApplicationProperties.class, ProtocolProperties.class,
        RegistryProperties.class})
public class DubboAutoConfiguration {
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private ProtocolProperties protocolProperties;
    @Autowired
    private RegistryProperties registryProperties;

    @Bean
    @ConditionalOnMissingBean
    public ApplicationConfig dubboApplicationConfig() {
        ApplicationConfig cfg = new ApplicationConfig();
        // NotNULL
        cfg.setName(this.applicationProperties.getName());
        if (DubboUtils.isNotBlank(this.applicationProperties.getVersion()))
            cfg.setVersion(this.applicationProperties.getVersion());
        if (DubboUtils.isNotBlank(this.applicationProperties.getOwner()))
            cfg.setOwner(this.applicationProperties.getOwner());
        if (DubboUtils.isNotBlank(this.applicationProperties.getOrganization()))
            cfg.setOrganization(this.applicationProperties.getOrganization());
        if (DubboUtils.isNotBlank(this.applicationProperties.getArchitecture()))
            cfg.setArchitecture(this.applicationProperties.getArchitecture());
        if (DubboUtils.isNotBlank(this.applicationProperties.getEnvironment()))
            cfg.setEnvironment(this.applicationProperties.getEnvironment());
        if (DubboUtils.isNotBlank(this.applicationProperties.getCompiler()))
            cfg.setCompiler(this.applicationProperties.getCompiler());
        if (DubboUtils.isNotBlank(this.applicationProperties.getLogger()))
            cfg.setLogger(this.applicationProperties.getLogger());
        return cfg;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProtocolConfig dubboProtocolConfig() {
        ProtocolConfig cfg = new ProtocolConfig();
        if (DubboUtils.isNotBlank(this.protocolProperties.getId()))
            cfg.setId(this.protocolProperties.getId());
        // NotNULL
        cfg.setName(this.protocolProperties.getName());
        if (this.protocolProperties.getPort() != null)
            cfg.setPort(this.protocolProperties.getPort());
        if (DubboUtils.isNotBlank(this.protocolProperties.getHost()))
            this.protocolProperties.getHost();
        if (DubboUtils.isNotBlank(this.protocolProperties.getThreadpool()))
            cfg.setThreadpool(this.protocolProperties.getThreadpool());
        if (this.protocolProperties.getThreads() != null)
            cfg.setThreads(this.protocolProperties.getThreads());
        if (this.protocolProperties.getIothreads() != null)
            cfg.setIothreads(this.protocolProperties.getIothreads());
        if (this.protocolProperties.getAccepts() != null)
            cfg.setAccepts(this.protocolProperties.getAccepts());
        if (this.protocolProperties.getPayload() != null)
            cfg.setPayload(this.protocolProperties.getPayload());
        if (DubboUtils.isNotBlank(this.protocolProperties.getCodec()))
            cfg.setCodec(this.protocolProperties.getCodec());
        if (DubboUtils.isNotBlank(this.protocolProperties.getSerialization()))
            cfg.setSerialization(this.protocolProperties.getSerialization());
        if (DubboUtils.isNotBlank(this.protocolProperties.getAccesslog()))
            cfg.setAccesslog(this.protocolProperties.getAccesslog());
        // if (this.protocolProperties.getPath() != null)
        // cfg.setPath(this.protocolProperties.getPath());
        if (DubboUtils.isNotBlank(this.protocolProperties.getTransporter()))
            cfg.setTransporter(this.protocolProperties.getTransporter());
        if (DubboUtils.isNotBlank(this.protocolProperties.getServer()))
            cfg.setServer(this.protocolProperties.getServer());
        if (DubboUtils.isNotBlank(this.protocolProperties.getClient()))
            cfg.setClient(this.protocolProperties.getClient());
        if (DubboUtils.isNotBlank(this.protocolProperties.getDispatcher()))
            cfg.setDispatcher(this.protocolProperties.getDispatcher());
        if (this.protocolProperties.getQueues() != null)
            cfg.setQueues(this.protocolProperties.getQueues());
        if (DubboUtils.isNotBlank(this.protocolProperties.getCharset()))
            cfg.setCharset(this.protocolProperties.getCharset());
        if (this.protocolProperties.getBuffer() != null)
            cfg.setBuffer(this.protocolProperties.getBuffer());
        if (this.protocolProperties.getHeartbeat() != null)
            cfg.setHeartbeat(this.protocolProperties.getHeartbeat());
        if (DubboUtils.isNotBlank(this.protocolProperties.getTelnet()))
            cfg.setTelnet(this.protocolProperties.getTelnet());
        if (this.protocolProperties.isRegister() != null)
            cfg.setRegister(this.protocolProperties.isRegister());
        if (DubboUtils.isNotBlank(this.protocolProperties.getContextpath()))
            cfg.setContextpath(this.protocolProperties.getContextpath());
        return cfg;
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistryConfig dubboRegistryConfig() {
        RegistryConfig cfg = new RegistryConfig();
        if (DubboUtils.isNotBlank(this.registryProperties.getId()))
            cfg.setId(this.registryProperties.getId());
        cfg.setAddress(this.registryProperties.getAddress());
        if (DubboUtils.isNotBlank(this.registryProperties.getProtocol()))
            cfg.setProtocol(this.registryProperties.getProtocol());
        if (this.registryProperties.getPort() != null)
            cfg.setPort(this.registryProperties.getPort());
        if (DubboUtils.isNotBlank(this.registryProperties.getUsername()))
            cfg.setUsername(this.registryProperties.getUsername());
        if (DubboUtils.isNotBlank(this.registryProperties.getPassword()))
            cfg.setPassword(this.registryProperties.getPassword());
        // if (this.registryProperties.getTransport() != null)
        // cfg.setTransport(this.registryProperties.getTransport());
        if (this.registryProperties.getTimeout() != null)
            cfg.setTimeout(this.registryProperties.getTimeout());
        if (this.registryProperties.getSession() != null)
            cfg.setSession(this.registryProperties.getSession());
        if (DubboUtils.isNotBlank(this.registryProperties.getFile()))
            cfg.setFile(this.registryProperties.getFile());
        // if (this.registryProperties.getWait() != null)
        // cfg.setWait(this.registryProperties.getWait());
        if (this.registryProperties.isCheck() != null)
            cfg.setCheck(this.registryProperties.isCheck());
        if (this.registryProperties.isRegister() != null)
            cfg.setRegister(this.registryProperties.isRegister());
        if (this.registryProperties.isSubscribe() != null)
            cfg.setSubscribe(this.registryProperties.isSubscribe());
        if (this.registryProperties.isDynamic() != null)
            cfg.setDynamic(this.registryProperties.isDynamic());
        return cfg;
    }

}
