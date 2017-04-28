package com.alibaba.boot.dubbo.properties;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * &lt;dubbo:protocol/>
 * 
 * <pre>
 * 服务提供者协议配置：
 * 配置类：com.alibaba.dubbo.config.ProtocolConfig
 * 说明：如果需要支持多协议，可以声明多个private String 标签，并在<dubbo:service>中通过protocol属性指定使用的协议。
 * spring
 *   dubbo:
 *     protocol:
 *       id:
 *       name: dubbo
 *       port:
 *       host:
 *       threadpool: fixed
 *       threads: 100
 *       iothreads:
 *       accepts: 0
 *       payload: 88388608
 *       codec: dubbo
 *       serialization:
 *       accesslog:
 *       path:
 *       transporter:
 *       server:
 *       client:
 *       dispatcher:
 *       queues: 0
 *       charset:
 *       buffer: 8192
 *       heartbeat: 0
 *       telnet:
 *       register: true
 *       contextpath:
 * </pre>
 *
 * @author YL 2017年4月26日
 */
@ConfigurationProperties(prefix = "spring.dubbo.protocol")
public class ProtocolProperties {
	// 缺省值：dubbo。协议BeanId，可以在<dubbo:service
	// protocol="">中引用此ID，如果ID不填，缺省和name属性值一样，重复则在name后加序号。
	private String id;
	// 缺省值：dubbo。协议名称
	@NotNull
	private String name;
	// 服务端口：dubbo协议缺省端口为20880，rmi协议缺省端口为1099，http和hessian协议缺省端口为80
	// 如果配置为-1 或者 没有配置port，则会分配一个没有被占用的端口。Dubbo
	// 2.4.0+，分配的端口在协议缺省端口的基础上增长，确保端口段可控。
	private Integer port;
	// 自动查找本机IP。-服务主机名，多网卡选择或指定VIP及域名时使用，为空则自动查找本机IP，-建议不要配置，让Dubbo自动获取本机IP
	private String host;
	// 缺省值：fixed。线程池类型，可选：fixed/cached
	private String threadpool;
	// 缺省值：100。服务线程池大小(固定大小)
	private Integer threads;
	// cpu个数+1。io线程池大小(固定大小)
	private Integer iothreads;
	// 缺省值：0。服务提供方最大可接受连接数
	private Integer accepts;
	// 缺省值：88388608(=8M)。请求及响应数据包大小限制，单位：字节
	private Integer payload;
	// 缺省值：dubbo。协议编码方式
	private String codec;
	// 缺省值：dubbo协议缺省为hessian2，rmi协议缺省为java，http协议缺省为json。协议序列化方式，当协议支持多种序列化方式时使用，比如：dubbo协议的dubbo,hessian2,java,compactedjava，以及http协议的json等
	private String serialization;
	// 设为true，将向logger中输出访问日志，也可填写访问日志文件路径，直接把访问日志输出到指定文件
	private String accesslog;
	// 提供者上下文路径，为服务path的前缀
	private String path;
	// 缺省值：dubbo协议缺省为netty。协议的服务端和客户端实现类型，比如：dubbo协议的mina,netty等，可以分拆为server和client配置
	private String transporter;
	// 缺省值：dubbo协议缺省为netty，http协议缺省为servlet。协议的服务器端实现类型，比如：dubbo协议的mina,netty等，http协议的jetty,servlet等
	private String server;
	// 缺省值：dubbo协议缺省为netty。协议的客户端实现类型，比如：dubbo协议的mina,netty等
	private String client;
	// 缺省值：dubbo协议缺省为all。协议的消息派发方式，用于指定线程模型，比如：dubbo协议的all, direct, message,
	// execution, connection等
	private String dispatcher;
	// 缺省值：0。线程池队列大小，当线程池满时，排队等待执行的队列大小，建议不要设置，当线程程池时应立即失败，重试其它服务提供机器，而不是排队，除非有特殊需求
	private Integer queues;
	// 缺省值：UTF-8。序列化编码
	private String charset;
	// 缺省值：8192。网络读写缓冲区大小
	private Integer buffer;
	// 缺省值：0。心跳间隔，对于长连接，当物理层断开时，比如拔网线，TCP的FIN消息来不及发送，对方收不到断开事件，此时需要心跳来帮助检查连接是否已断开
	private Integer heartbeat;
	// 所支持的telnet命令，多个命令用逗号分隔
	private String telnet;
	// 缺省值：true。该协议的服务是否注册到注册中心
	private Boolean register;
	private String contextpath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getThreadpool() {
		return threadpool;
	}

	public void setThreadpool(String threadpool) {
		this.threadpool = threadpool;
	}

	public Integer getThreads() {
		return threads;
	}

	public void setThreads(Integer threads) {
		this.threads = threads;
	}

	public Integer getIothreads() {
		return iothreads;
	}

	public void setIothreads(Integer iothreads) {
		this.iothreads = iothreads;
	}

	public Integer getAccepts() {
		return accepts;
	}

	public void setAccepts(Integer accepts) {
		this.accepts = accepts;
	}

	public Integer getPayload() {
		return payload;
	}

	public void setPayload(Integer payload) {
		this.payload = payload;
	}

	public String getCodec() {
		return codec;
	}

	public void setCodec(String codec) {
		this.codec = codec;
	}

	public String getSerialization() {
		return serialization;
	}

	public void setSerialization(String serialization) {
		this.serialization = serialization;
	}

	public String getAccesslog() {
		return accesslog;
	}

	public void setAccesslog(String accesslog) {
		this.accesslog = accesslog;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public Integer getQueues() {
		return queues;
	}

	public void setQueues(Integer queues) {
		this.queues = queues;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Integer getBuffer() {
		return buffer;
	}

	public void setBuffer(Integer buffer) {
		this.buffer = buffer;
	}

	public Integer getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(Integer heartbeat) {
		this.heartbeat = heartbeat;
	}

	public String getTelnet() {
		return telnet;
	}

	public void setTelnet(String telnet) {
		this.telnet = telnet;
	}

	public Boolean isRegister() {
		return register;
	}

	public void setRegister(Boolean register) {
		this.register = register;
	}

	public String getContextpath() {
		return contextpath;
	}

	public void setContextpath(String contextpath) {
		this.contextpath = contextpath;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProtocolProperties [id=").append(id).append(", name=").append(name).append(", port=")
				.append(port).append(", host=").append(host).append(", threadpool=").append(threadpool)
				.append(", threads=").append(threads).append(", iothreads=").append(iothreads).append(", accepts=")
				.append(accepts).append(", payload=").append(payload).append(", codec=").append(codec)
				.append(", serialization=").append(serialization).append(", accesslog=").append(accesslog)
				.append(", path=").append(path).append(", transporter=").append(transporter).append(", server=")
				.append(server).append(", client=").append(client).append(", dispatcher=").append(dispatcher)
				.append(", queues=").append(queues).append(", charset=").append(charset).append(", buffer=")
				.append(buffer).append(", heartbeat=").append(heartbeat).append(", telnet=").append(telnet)
				.append(", register=").append(register).append(", contextpath=").append(contextpath).append("]");
		return builder.toString();
	}

}
