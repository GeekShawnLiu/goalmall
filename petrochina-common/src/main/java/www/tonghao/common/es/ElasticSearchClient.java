package www.tonghao.common.es;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticSearchClient {

	private String hostName;
	
	private int port;
	
	private String clusterName;
	
	private boolean enable;

	TransportClient client = null;

	@SuppressWarnings("resource")
	public ElasticSearchClient(String hostName, int port, String clusterName) {
		this.hostName = hostName;
		this.port = port;
		this.clusterName = clusterName;
		try {
			// 设置集群名称
			Settings settings = Settings.builder().put("cluster.name", clusterName).build();
			// 创建client
			client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName(hostName), port));
		} catch (Exception e) {
			client.close();
			e.printStackTrace();
		}
	}

	public TransportClient getConnection() {
		if (client == null) {
			synchronized (ElasticSearchClient.class) {
				if (client == null) {
					new ElasticSearchClient(hostName, port, clusterName);
				}
			}
		}
		return client;
	}

	public static void close(TransportClient client) {
		if (client != null)
			client.close();
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}