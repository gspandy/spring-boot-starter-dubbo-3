package com.alibaba.boot.dubbo.properties;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * &lt;dubbo:registry/>
 * 
 * <pre>
 * 注册中心配置：
 * 配置类：com.alibaba.dubbo.config.RegistryConfig
 * 说明：如果有多个不同的注册中心，可以声明多个private String 标签，并在<dubbo:service>或<dubbo:reference>的registry属性指定使用的注册中心。
 * spring:
 *   dubbo:
 *     registry:
 *       id:
 *       address:
 *       protocol:
 *       port:
 *       username:
 *       password:
 *       transport:
 *       timeout: 5000
 *       session: 60000
 *       file:
 *       wait: 0
 *       check: true
 *       register: true
 *       subscribe: true
 *       dynamic: true
 * </pre>
 * 
 * @author YL 2017年4月26日
 */
@ConfigurationProperties(prefix = "spring.dubbo.registry")
public class RegistryProperties {
	// 注册中心引用BeanId，可以在<dubbo:service
	// registry="">或<dubbo:reference registry="">中引用此ID
	private String id;
	// 注册中心服务器地址，如果地址没有端口缺省为9090，同一集群内的多个地址用逗号分隔，如：ip:port,ip:port，不同集群的注册中心，请配置多个private
	// String 标签
	@NotNull
	private String address;
	// 缺省值：dubbo。注同中心地址协议，支持dubbo, http, local三种协议，分别表示，dubbo地址，http地址，本地注册中心
	private String protocol;
	// 缺省值：9090。注册中心缺省端口，当address没有带端口时使用此端口做为缺省值
	private Integer port;
	// 登录注册中心用户名，如果注册中心不需要验证可不填
	private String username;
	// 登录注册中心密码，如果注册中心不需要验证可不填
	private String password;
	// 缺省值：netty。网络传输方式，可选mina,netty
	private String transport;
	// 缺省值：5000。注册中心请求超时时间(毫秒)
	private Integer timeout;
	// 缺省值：60000。注册中心会话超时时间(毫秒)，用于检测提供者非正常断线后的脏数据，比如用心跳检测的实现，此时间就是心跳间隔，不同注册中心实现不一样
	private Integer session;
	// 使用文件缓存注册中心地址列表及服务提供者列表，应用重启时将基于此文件恢复，注意：两个注册中心不能使用同一文件存储
	private String file;
	// 缺省值：0。停止时等待通知完成时间(毫秒)
	private Integer wait;
	// 缺省值：true.注册中心不存在时，是否报错
	private Boolean check;
	// 缺省值：true。是否向此注册中心注册服务，如果设为false，将只订阅，不注册
	private Boolean register;
	// 缺省值：true。是否向此注册中心订阅服务，如果设为false，将只注册，不订阅
	private Boolean subscribe;
	// 缺省值：true。服务是否动态注册，如果设为false，注册后将显示后disable状态，需人工启用，并且服务提供者停止时，也不会自动取消册，需人工禁用
	private Boolean dynamic;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getSession() {
		return session;
	}

	public void setSession(Integer session) {
		this.session = session;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Integer getWait() {
		return wait;
	}

	public void setWait(Integer wait) {
		this.wait = wait;
	}

	public Boolean isCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public Boolean isRegister() {
		return register;
	}

	public void setRegister(Boolean register) {
		this.register = register;
	}

	public Boolean isSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}

	public Boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(Boolean dynamic) {
		this.dynamic = dynamic;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistryProperties [id=").append(id).append(", address=").append(address).append(", protocol=")
				.append(protocol).append(", port=").append(port).append(", username=").append(username)
				.append(", password=").append(password).append(", transport=").append(transport).append(", timeout=")
				.append(timeout).append(", session=").append(session).append(", file=").append(file).append(", wait=")
				.append(wait).append(", check=").append(check).append(", register=").append(register)
				.append(", subscribe=").append(subscribe).append(", dynamic=").append(dynamic).append("]");
		return builder.toString();
	}

}
