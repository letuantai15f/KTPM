package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.BasicConfigurator;

import data.Person;
import helper.XMLConvert;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;

public class Sender extends JFrame implements ActionListener {

	private static Session session;
	private static MessageProducer producer;
	private static Connection con;
	private JPanel contentPane;
	private JTextField txtmess;
	private JButton btnsend;
	private JTextArea txtcontent;
	private JTextField txtperson;

	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sender frame = new Sender();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		BasicConfigurator.configure();
		//config environment for JNDI
		Properties settings=new Properties();
		settings.setProperty(Context.INITIAL_CONTEXT_FACTORY, 
		"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		//create context
		Context ctx=new InitialContext(settings);
		//lookup JMS connection factory
		ConnectionFactory factory=
		(ConnectionFactory)ctx.lookup("ConnectionFactory");
		//lookup destination. (If not exist-->ActiveMQ create once)
		Destination destination=
		(Destination) ctx.lookup("dynamicQueues/thanthidet");
		//get connection using credential
		 con=factory.createConnection("admin","admin");
		//connect to MOM
		con.start();
		//create session
		 session=con.createSession(
		/*transaction*/false,
		/*ACK*/Session.AUTO_ACKNOWLEDGE
		);
		//create producer
		 producer = session.createProducer(destination);
		//create text message
		
		
	}

	/**
	 * Create the frame.
	 */
	public Sender() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sender");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(177, 11, 77, 21);
		contentPane.add(lblNewLabel);
		
		 txtcontent = new JTextArea();
		txtcontent.setEditable(false);
		txtcontent.setBounds(10, 40, 414, 94);
		contentPane.add(txtcontent);
		
		txtmess = new JTextField();
		txtmess.setBounds(10, 145, 414, 27);
		contentPane.add(txtmess);
		txtmess.setColumns(10);
		
		 btnsend = new JButton("Sender");
		btnsend.setBounds(165, 232, 89, 23);
		contentPane.add(btnsend);
		
		txtperson = new JTextField();
		txtperson.setBounds(10, 183, 414, 27);
		contentPane.add(txtperson);
		txtperson.setColumns(10);
		btnsend.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o=e.getSource();
		if(o.equals(btnsend)) {
			Message msg=null;
			try {
				msg = session.createTextMessage(txtmess.getText().toString());
				producer.send(msg);
				Person p=new Person(1001, txtperson.getText().toString(), new Date());
				String xml=new XMLConvert<Person>(p).object2XML(p);
				msg=session.createTextMessage(txtperson.getText().toString());
				producer.send(msg);
				//shutdown connection
				if(txtmess.equals("end")) {
					session.close();con.close();
					System.out.println("Finished...");
				}
				
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}
		
	}
}
