package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.BasicConfigurator;

import data.Person;
import helper.XMLConvert;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Properties;

import javax.swing.JTextArea;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JTextField;

public class Receiver extends JFrame implements ActionListener {

	private static Connection con;
	private static MessageConsumer receiver;
	private static Session session;
	private static Destination destination;
	private JPanel contentPane;
	private JTextField txtmess;
	private JTextArea txtcontent;
	private JButton btnsend;

	/**
	 * Launch the application.
	 * @throws JMSException 
	 * @throws NamingException 
	 */
	public static void main(String[] args) throws JMSException, NamingException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Receiver frame = new Receiver();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// thiết lập môi trường cho JMS
				
				

	
	}

	/**
	 * Create the frame.
	 * @throws JMSException 
	 * @throws NamingException 
	 */
	public Receiver() throws JMSException, NamingException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("recevicer");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(166, 11, 99, 32);
		contentPane.add(lblNewLabel);
		
		 txtcontent = new JTextArea();
		 txtcontent.setFont(new Font("Monospaced", Font.PLAIN, 15));
		txtcontent.setBounds(10, 39, 414, 114);
		contentPane.add(txtcontent);
		
		 btnsend = new JButton("Send");
		btnsend.setBounds(176, 227, 89, 23);
		contentPane.add(btnsend);
		
		txtmess = new JTextField();
		txtmess.setBounds(10, 161, 414, 32);
		contentPane.add(txtmess);
		txtmess.setColumns(10);
		btnsend.addActionListener(this);
		
		
		BasicConfigurator.configure();
		// thiết lập môi trường cho JJNDI
		Properties settings = new Properties();
		settings.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		// tạo context
		Context ctx = new InitialContext(settings);
		// lookup JMS connection factory
		Object obj = ctx.lookup("ConnectionFactory");
		ConnectionFactory factory = (ConnectionFactory) obj;
		// lookup destination
		 destination = (Destination) ctx.lookup("dynamicQueues/thanthidet");
		// tạo connection
		 con = factory.createConnection("admin", "admin");
		// nối đến MOM
		con.start();
		// tạo session
		 session = con.createSession(/* transaction */false, /* ACK */Session.CLIENT_ACKNOWLEDGE);
		// tạo consumer
		 
		// blocked-method for receiving message - sync
		// receiver.receive();
		// Cho receiver lắng nghe trên queue, chừng có message thì notify - async
		System.out.println("Tý was listened on queue...");
		receiver = session.createConsumer(destination);
receiver.setMessageListener(new MessageListener() {
			
			// có message đến queue, phương thức này được thực thi
			public void onMessage(Message msg) {// msg là message nhận được
				try {
					if (msg instanceof TextMessage) {
						TextMessage tm = (TextMessage) msg;
						String txt = tm.getText();
						
						txtcontent.append(txt +"\n");
						msg.acknowledge();// gửi tín hiệu ack
					} else if (msg instanceof ObjectMessage) {
						ObjectMessage om = (ObjectMessage) msg;
						System.out.println(om);
					}
					// others message type....
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

//		Object o=e.getSource();
//		if(o.equals(btnsend)) {
//			Message msg=null;
//			try {
//				msg = session.createTextMessage(txtmess.getText().toString());
//				producer.send(msg);
//				Person p=new Person(1001, txtperson.getText().toString(), new Date());
//				String xml=new XMLConvert<Person>(p).object2XML(p);
//				msg=session.createTextMessage(txtperson.getText().toString());
//				producer.send(msg);
//				//shutdown connection
//				if(txtmess.equals("end")) {
//					session.close();con.close();
//					System.out.println("Finished...");
//				}
//				
//			} catch (JMSException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//			
//			
//		}
//		
//	}
	}

}
