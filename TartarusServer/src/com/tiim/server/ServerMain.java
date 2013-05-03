//package com.tiim.server;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.*;
import javax.swing.*;
import java.util.Random;

@SuppressWarnings("serial")
public class ServerMain extends JApplet implements ActionListener {

	// Varibles for IP and port, as well as some other stuff

	static Connection socket;

	String IP_STRING = GetLocallIP();
	String PUB_IP_STRING ="Fetching Ip...";

	static int PORT_INPUT = 0;

	// JPanels
	JPanel statusPane = new JPanel();
	JPanel ipPane = new JPanel();

	// JFrames
	static JFrame APPLET_WINDOW;
	static JFrame EXTRA_WINDOW;

	static JScrollPane scroller;

	// JLabels
	static JLabel CONNECTION_STATUS = new JLabel("Offline");
	static JLabel PORT_LABEL = new JLabel("Port (digits only): ");
	static JLabel PASSWORD_LABEL = new JLabel("Password (optional): ");
	JLabel IP_LABEL = new JLabel("Private IP: ");
	JLabel PUB_IP_LABEL = new JLabel("Public IP: ");
	JLabel IP_TEXT = new JLabel(IP_STRING);
	JLabel PUB_IP_TEXT = new JLabel(PUB_IP_STRING);

	// JTextFields for Port and Pass input
	static JTextField PORT_TEXT_IN = new JTextField("9001");
	static JTextField PASS_TEXT_IN = new JTextField("");
	String RAW_PORT_INPUT;
	protected static String RAW_PASS_INPUT;
	static JTextArea console = new JTextArea("Console output goes here.");

	// Booleans for checking connection and showing console output
	static boolean LABEL_CONNECT_TOGGLE = false;
	public static boolean NEED_PASSWORD = false;
	static boolean DID_WE_DO_IT = false;
	static boolean SHOW_CONSOLE = false;
	static JButton CONNECT_BUTTON = new JButton("Start");
	static JButton GENERATE_PINCODE = new JButton("PIN");
	static JCheckBox CONSOLE_SHOW = new JCheckBox("Console");

	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	static int sHeight = gd.getDisplayMode().getHeight();
	static int sWidth = gd.getDisplayMode().getWidth();

	public void init() {
		// Initiation method
		// Kind of like a constructor, and yet not.

		LABEL_CONNECT_TOGGLE = false;
		DID_WE_DO_IT = false;

		// Container to contain stuff
		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		// Sets layout
		ipPane.setLayout(new GridLayout(4, 2));
		ipPane.add(IP_LABEL);
		ipPane.add(IP_TEXT);
		ipPane.add(PUB_IP_LABEL);
		ipPane.add(PUB_IP_TEXT);
		ipPane.add(PORT_LABEL);
		ipPane.add(PORT_TEXT_IN);
		ipPane.add(PASSWORD_LABEL);
		ipPane.add(PASS_TEXT_IN);

		statusPane.setLayout(new BorderLayout());
		statusPane.add(CONNECTION_STATUS, BorderLayout.WEST);
		statusPane.add(CONSOLE_SHOW, BorderLayout.EAST);

		c.add(statusPane, BorderLayout.SOUTH);
		c.add(CONNECT_BUTTON, BorderLayout.CENTER);
		c.add(GENERATE_PINCODE, BorderLayout.EAST);
		c.add(ipPane, BorderLayout.NORTH);

		// ActionListener for all user actions (server-side, that is)
		CONNECT_BUTTON.addActionListener(this);
		GENERATE_PINCODE.addActionListener(this);
		CONSOLE_SHOW.addActionListener(this);
		GetPublicIP();
	}

	public static void main(String[] args) {
		// Main method
		ServerMain mainInstance = new ServerMain();
		mainInstance.init();

		// Create window
		APPLET_WINDOW = new JFrame();
		Container c = APPLET_WINDOW.getContentPane();
		APPLET_WINDOW.setTitle("Ye Olde Tartarus Server");
		c.add(mainInstance);

		// Customize window and paint it up
		Dimension MAIN_WINDOW = new Dimension(270,170);
		APPLET_WINDOW.setSize(MAIN_WINDOW);
		APPLET_WINDOW.setResizable(false);
		APPLET_WINDOW.setVisible(true);
		APPLET_WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		EXTRA_WINDOW = new JFrame();
		// Hidden scroll that constantly scrolls the console down to the bottom
		scroller = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());
	        }
	    });

		EXTRA_WINDOW.getContentPane().add(scroller);
		console.setEditable(false);
		console.setLineWrap(true);
		EXTRA_WINDOW.setUndecorated(false);
		EXTRA_WINDOW.setResizable(true);
		EXTRA_WINDOW.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

//		Dimension CONSOLE_WINDOW = new Dimension(500, (sHeight-30));
		Dimension CONSOLE_WINDOW = new Dimension(500, 600);
		EXTRA_WINDOW.setTitle("Ye Olde Extra Window");
		EXTRA_WINDOW.setSize(CONSOLE_WINDOW);
		EXTRA_WINDOW.setVisible(SHOW_CONSOLE);
		EXTRA_WINDOW.setLocation((sWidth-CONSOLE_WINDOW.width), 0);
		console.setBackground(Color.GRAY);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		// Conditions to fulfill when an action has occurred
		if (ae.getSource() == CONNECT_BUTTON) { // was it the connect button?
			RAW_PORT_INPUT = PORT_TEXT_IN.getText();
			RAW_PASS_INPUT = PASS_TEXT_IN.getText();

			try {
				// Try to parse textfield string to port integer
				if(!LABEL_CONNECT_TOGGLE){
					GENERATE_PINCODE.setEnabled(false);
					PORT_INPUT = Integer.parseInt(RAW_PORT_INPUT);
					PORT_TEXT_IN.setEditable(false);
					PASS_TEXT_IN.setEditable(false);
					print("Disabling inputs and parsing password");
					if (!RAW_PASS_INPUT.equals("")) {
						print("Password needed, \""+RAW_PASS_INPUT+"\"");
						NEED_PASSWORD = true;
					}
					print("Starting server");
					StartThisShitYao();
				}
				else {
					print("\nDisconnecting server");
					CONNECTION_STATUS.setText("Disconnecting");
					DID_WE_DO_IT = true;
					socket.stop();
				}
			}

			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Input integers.");
				ePrint("Incorrect port input. "+e.getMessage());
				e.printStackTrace();
			}
		}

		if (ae.getSource() == GENERATE_PINCODE) {
			print("Generate pin code");
			NEED_PASSWORD = true;
			String p = pinGenerator();
			PASS_TEXT_IN.setText(p);
			RAW_PASS_INPUT = p;
			print("Pin code generated: "+p);
		}

		if (ae.getSource() == CONSOLE_SHOW) {
			SHOW_CONSOLE = !SHOW_CONSOLE;
			EXTRA_WINDOW.setVisible(SHOW_CONSOLE);
		}
	}

	public static void StartThisShitYao() {
		PORT_LABEL.setText("Using port: ");

		// Change connect button to disconnect button
		CONNECT_BUTTON.setText("Disconnect");
		// Toggle connection state boolean
		LABEL_CONNECT_TOGGLE = !LABEL_CONNECT_TOGGLE;
		socket = new Connection(PORT_INPUT);
		print("Server started on port: "+PORT_INPUT);
		new Thread(socket).start();

		CONNECTION_STATUS.setText("Waiting for Connection...");

		new Thread(new Runnable() {
			public void run() {
				while (socket.isRunning()) {
					while (!socket.isConnected()) {
						try {
							// This is pretty often for an idle server. Just sayin'.
							Thread.sleep(10);
						} catch (InterruptedException e) {
							ePrint(e.getMessage());
							e.printStackTrace();
						}
					}
					// Change status to online
					CONNECTION_STATUS.setText("Receiving");
					while (socket.isConnected()) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							ePrint(e.getMessage());
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

	}

	public static void ButtonReset() {
		print("Server reset and disabled functions enabled");
		PORT_LABEL.setText("Port:");
		CONNECTION_STATUS.setText("Disconnected By Client");
		// Change disconnect button to re-connect button
		CONNECT_BUTTON.setText("Start");
		PORT_TEXT_IN.setEditable(true);
		GENERATE_PINCODE.setEnabled(true);
		if(RAW_PASS_INPUT.equals(""))
			NEED_PASSWORD = false;
		PASS_TEXT_IN.setEditable(true);
		// Toggle connection state boolean
		LABEL_CONNECT_TOGGLE = !LABEL_CONNECT_TOGGLE;

		if (!DID_WE_DO_IT) {
			print("Client disconnected, restarting server function");
			StartThisShitYao();
		}
		else {
			CONNECTION_STATUS.setText("Disconnected By You");
		}
		DID_WE_DO_IT = false;
	}

	public String GetLocallIP() {
		String ip = "Can't get local IP address";

		try {
			Socket s = new Socket("www.kth.se", 80);
			ip = s.getLocalAddress().getHostAddress();
			s.close();
		} catch (UnknownHostException e) {
			ePrint(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
		 	ePrint(e.getMessage());
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

					BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
					inputLine = in.readLine();
					System.out.println(inputLine);
					PUB_IP_TEXT.setText(inputLine);


					in.close();
				} catch (IOException e) {
					ePrint("Internet broke");
					e.printStackTrace();
				}
			}
		}).start();

	}

	public static String pinGenerator() {
		Random rng = new Random();
		int first = rng.nextInt(10);
		int second = rng.nextInt(10);
		int third = rng.nextInt(10);
		int fourth = rng.nextInt(10);
		String pincode = first+""+second+""+third+""+fourth;
		return pincode;
	}

	public static void print(String s) {
		if(SHOW_CONSOLE)
			console.append("\n"+s);
		System.out.println(s);
	}
	public static void ePrint(String e) {
		if(SHOW_CONSOLE)
			console.append("\n"+e);
		System.err.println(e);
	}
}
