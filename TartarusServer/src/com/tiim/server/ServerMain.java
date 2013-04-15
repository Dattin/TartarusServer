package com.tiim.server;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ServerMain extends JApplet implements ActionListener {

	// Variabler f�r Ip och Portnummrernarnar

	static Connection socket;

	String IP_STRING = GetLocallIP();
	String PUB_IP_STRING ="Fetching Ip...";
	
	static int PORT_INPUT = 0;

	// Panels f�r att det ska se fint ut
	JPanel statusPane = new JPanel();
	JPanel ipPane = new JPanel();

	// Labels f�r att kunna skriva ut saker
	static JLabel CONNECTION_STATUS = new JLabel("Offline");
	static JLabel PORT_LABEL = new JLabel("Port (digits only): ");
	JLabel IP_LABEL = new JLabel("IP: ");
	JLabel PUB_IP_LABEL = new JLabel("Public IP: ");
	JLabel IP_TEXT = new JLabel(IP_STRING);
	JLabel PUB_IP_TEXT = new JLabel(PUB_IP_STRING);

	// Textfield f�r att kunna skriva in Portnummernerrnana
	JTextField PORT_TEXT_IN = new JTextField("9001");

	// Bool f�r att kolla ifall Connectad eller Ej
	static boolean LABEL_CONNECT_TOGGLE = false;
	static boolean DID_WE_DO_IT = false;
	static JButton CONNECT_BUTTON = new JButton("Connect");

	public void init() { // Metod f�r att Initiera hela Main programmet, inte en
		// Konstruktor.

		LABEL_CONNECT_TOGGLE = false;
		DID_WE_DO_IT = false;

		// Container C f�r att sl�nga in allting i
		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		// Fixa layouten
		ipPane.setLayout(new GridLayout(3, 2));
		ipPane.add(IP_LABEL);
		ipPane.add(IP_TEXT);
		ipPane.add(PUB_IP_LABEL);
		ipPane.add(PUB_IP_TEXT);
		ipPane.add(PORT_LABEL);
		ipPane.add(PORT_TEXT_IN);

		statusPane.setLayout(new GridLayout(1, 3));
		statusPane.add(CONNECTION_STATUS);

		c.add(statusPane, BorderLayout.SOUTH);
		c.add(CONNECT_BUTTON, BorderLayout.CENTER);
		c.add(ipPane, BorderLayout.NORTH);

		// Fixa Actionlistener
		CONNECT_BUTTON.addActionListener(this);
		GetPublicIP();

		/*
		 * PUB_IP_STRING=GetPublicIP(); PUB_IP_TEXT.setText(PUB_IP_STRING);
		 */

	}

	public static void main(String[] args) {
		// Main metod..
		ServerMain mainInstance = new ServerMain();
		mainInstance.init();

		// G�r ett f�nster
		JFrame APPLET_WINDOW = new JFrame();
		Container c = APPLET_WINDOW.getContentPane();
		// APPLET_WINDOW.setTitle("Loltja");
		c.add(mainInstance);

		// Kustomiserar f�nstret och ritar ut det
		APPLET_WINDOW.setSize(800, 600);
		APPLET_WINDOW.setVisible(true);
		APPLET_WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Awesome server stuff here
		// Make server, connect
	}

	@Override
	public void actionPerformed(ActionEvent ae) { // Shit n�got h�nde

		// h�r ska konditionsfunktionaliteten vara n�r man trycker p� button
		// KOlla om det var fr�n BUTTON
		if (ae.getSource() == CONNECT_BUTTON) {
			// YEs IT WAS, ta portnummret
			String RAW_PORT_INPUT = PORT_TEXT_IN.getText();

			try { // Pr�va om man kan ta om Portnummrerna till Ints
				PORT_INPUT = Integer.parseInt(RAW_PORT_INPUT);
			}

			catch (NumberFormatException e) {

				PORT_LABEL.setText("write a number");
				System.out.println("ERROR: INCORRECT INPUT");

			}

			if (!LABEL_CONNECT_TOGGLE) { // H�r �r n�r man Vill Connecta. //
											// testttest
				StartThisShitYao();
			}

			else {
				CONNECTION_STATUS.setText("Disconnecting");
				DID_WE_DO_IT = true;
				socket.stop();

			}
		}
	}

	public static void StartThisShitYao() {
		PORT_LABEL.setText("Using port: ");// +Integer.toString(PORT_INPUT));

		CONNECT_BUTTON.setText("Disconnect"); // �ndra Knapp till
		// Disconnect knapp
		LABEL_CONNECT_TOGGLE = !LABEL_CONNECT_TOGGLE; // Toggla f�r att
		// h�lla koll
		socket = new Connection(PORT_INPUT, null);
		new Thread(socket).start();

		CONNECTION_STATUS.setText("Waiting for Connection..");

		new Thread(new Runnable() {
			public void run() {
				while (socket.isRunning()) {
					while (!socket.isConnected()) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					CONNECTION_STATUS.setText("Online, waiting for commands"); // �ndra
					// status
					// till
					// Online
					while (socket.isConnected()) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						// CONNECTION_STATUS.setText("Online, waiting for commands");
						// // �ndra status till Online
					}
				}
			}
		}).start();

	}

	public static void ButtonReset() {

		PORT_LABEL.setText("Port:");
		CONNECTION_STATUS.setText("Disconnected By Client");
		CONNECT_BUTTON.setText("Re-connect"); // �ndra Knapp till Connectknapp
		LABEL_CONNECT_TOGGLE = !LABEL_CONNECT_TOGGLE; // Toggla f�r att

		if (!DID_WE_DO_IT)
			StartThisShitYao();
		else {
			CONNECTION_STATUS.setText("Disconnected By You");
		}
		DID_WE_DO_IT = false;
	}

	public String GetLocallIP() {
		String ip = "123.456.789.12";

		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;
	}

	public void GetPublicIP() {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				String inputLine = "";
				try {

					URL url = new URL("http://checkip.amazonaws.com/");

					BufferedReader in = new BufferedReader(
							new InputStreamReader(url.openStream()));
					inputLine = in.readLine();
					System.out.println(inputLine);
					PUB_IP_TEXT.setText(inputLine);
					

					in.close();
				} catch (IOException e) {
					System.out.println("Internet broke");
				}
			}
		}).start();
	
	}
}