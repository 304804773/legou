//
//import java.io.IOException;
//
//import javax.jms.Connection;
//import javax.jms.ConnectionFactory;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.MessageConsumer;
//import javax.jms.MessageListener;
//import javax.jms.MessageProducer;
//import javax.jms.Queue;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.junit.Test;
//
//public class ActiveMqTest {
//	@Test
//	public void SendMessage() {
//
//		try {
//			//根据javax.jms规范来创建activemq实现，tcp网络通信协议
//			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
//			//根据javax.jms规范来创建activemq包下的连接
//			Connection connection = connectionFactory.createConnection();
//			// 启动连接
//			connection.start();
//			//根据javax.jms规范来创建activemq包下的会话，false为不启动事物，设置为自动确认
//			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//			// 创建点对点，设置一个名字
//			Queue queue = session.createQueue("lala");
//			// 创建消息生产者目的地
//			MessageProducer messageProducer = session.createProducer(queue);
//			// 创建文本信息
//			TextMessage createTextMessage = session.createTextMessage("QWE");
//			//消息生产者将消息 发送到queue
//			messageProducer.send(createTextMessage);
//			// 关闭连接
//			messageProducer.close();
//			session.close();
//			connection.close();
//		} catch (JMSException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//	@Test
//	public void getMessage() {
//
//		try {
//			//根据javax.jms规范来创建activemq实现，tcp网络通信协议
//			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
//			//根据javax.jms规范来创建activemq包下的连接
//			Connection connection = connectionFactory.createConnection();
//			//启动连接
//			connection.start();
//			//根据javax.jms规范来创建activemq包下的会话，false为不启动事物，设置为自动确认
//			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//			//创建点对点消息，从lala那得到
//			Queue queue = session.createQueue("lala");
//			//创建消息消费者
//			MessageConsumer wahh = session.createConsumer(queue);
//			// 创建监听器,匿名内部类
//			wahh.setMessageListener(new MessageListener() {
//
//				@Override
//				public void onMessage(Message message) {
//					// 创建文本信息,向下转型
//					TextMessage textMessage = (TextMessage) message;		
//					try {
//						//得到消息内容的文本值，转换成字符串格式
//						String string=textMessage.getText();
//						System.out.println(string);
//						//如果字符串不是空的话
//						if(!string.equals("")) {
//							//输出
//							System.out.println("1232131");
//						}
//						//阻塞线程
//						System.in.read();
//						//关闭连接
//						wahh.close();
//						session.close();
//						connection.close();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} 
//				}
//			});
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//}
