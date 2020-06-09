package org.apache.flink.streaming.connectors.kafka;

import com.tuya.basic.mq.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author pucheung
 * @Date 2020-05-07
 * @Desc
 **/
public class FlinkTuyaLoadConfig {

	private static final Logger logger = LoggerFactory.getLogger(FlinkTuyaLoadConfig.class);

	private static final String PATH_1 = "/home/collect/.tuya_config";

	private static final String PATH_2 = "/home/hdfs/.tuya_config";
	//private static final String PATH = "/Users/pucheung/a.txt";

	/**
	 * 同可用区使用的路径
	 */
	public static final String ZK_PATH = "/kafka_available/";

	private static final String ZONE_KEY = "zone";

	private static final String ZK_KEY = "zk";

	private static final String PATH_KEY = "zone";

	private static final Properties config = new Properties();

	static {
		intiConfig();
	}

	public static void intiConfig() {

		String path = System.getenv(PATH_KEY);
		logger.info("zone.path : {}", path);
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			config.load(is);
			if (config.size() == 0)
				throw new Exception("can not found config");
		} catch (Exception e) {
			logger.error("load config error : {}", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}


	public static String getZone(){
		String zone=config.getProperty(ZONE_KEY);
		logger.info("zone:{}",zone);
		return config.getProperty(ZONE_KEY);
	}

	public static String getZk(){
		String zk=config.getProperty(ZK_KEY);
		logger.info("zk:{}",zk);
		return config.getProperty(ZK_KEY);
	}


	private static Object lock = new Object();

	private static boolean isInit = false;

	public static void initGlobalConfig(String serviceName) {

		if (!isInit) {
			synchronized (lock) {
				if (!isInit) {
					GlobalConfig.getInstance().setServiceName(serviceName);
					GlobalConfig.getInstance().setZkConnection(FlinkTuyaLoadConfig.getZk());
					GlobalConfig.getInstance().setZkRootPath(FlinkTuyaLoadConfig.ZK_PATH);
					GlobalConfig.getInstance().setZoneSupplier(FlinkTuyaLoadConfig::getZone);
					isInit=true;
				}
			}
		}

	}

}
