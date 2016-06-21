package cn.eakay.control.parking.tcpserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * 
 * @描述: 启动Dubbo服务用的MainClass.
 * @作者: WuShuicheng .
 * @创建时间: 2013-11-5,下午9:47:55 .
 * @版本: 1.0 .
 */
public class SpringProvider {

	private static final Log log = LogFactory.getLog(SpringProvider.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:spring/spring-context.xml");
			context.start();
		} catch (Exception e) {
			log.error("== DubboProvider context start error:", e);
		}
		
	}

}