//package com.tiim.server;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.awt.event.KeyEvent;
import images.Scalr;
import images.Scalr.Method;
import javax.imageio.ImageIO;

public class Connection implements Runnable {

	private Socket socket;
	private ServerSocket server;
	private int port;
	private static final String pass = ServerMain.RAW_PASS_INPUT;
	private InputStream is;
	private boolean run = true;
	private boolean stop = false;
	private boolean accepted = true;
	RobotHandler robotHandler = new RobotHandler();

	public void stop() {
		ServerMain.print("Stopping server, closing socket");
		stop = true;
		while (run) {
			try {
				Thread.sleep(100);
				ServerMain.print("Waiting for socket to close");
//				socket.close();
				server.close();
			} catch (InterruptedException e) {
				ServerMain.ePrint(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				ServerMain.ePrint(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public boolean isRunning() {
		return run;
	}

	private void close() {
		try {
			ServerMain.print("Try closing socket and server");
			socket.close();
			server.close();
		} catch (IOException e) {
			ServerMain.ePrint(""+e.getMessage());
			e.printStackTrace();
		}
		while (!socket.isClosed())
			;
		while (!server.isClosed())
			;
		run = false;
		accepted = true;
		ServerMain.print("Server stopped");
		ServerMain.ButtonReset();
	}

	public void run() {
		ServerMain.print("Listening to port: " + port);
		while (!isConnected()) {
			if (stop) {
				close();
				return;
			}
			try {
				socket = server.accept();
			} catch (SocketTimeoutException e) {
				// Don't remove these comments. Ridiculous amounts of output because half-assed coding.
//				ServerMain.ePrint(e.getMessage());
//				e.printStackTrace();
			} catch (SocketException e) {
				ServerMain.ePrint(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				ServerMain.ePrint(e.getMessage());
				e.printStackTrace();
			}

		}
		ServerMain.print("Initiating handshake");
		harlemshake();
		ServerMain.print("Done reading, closing connection");
		close();
	}

	private void harlemshake() {
		if (ServerMain.NEED_PASSWORD) {
			ServerMain.print("Need password from client");
			accepted = false;
			String p = "";
			String resX = "";
			String resY = "";
			if (!accepted) {
				try {
					ServerMain.print("Sending password request");
					socket.getOutputStream().write(7);
					is = socket.getInputStream();
					int i = is.read();
					ServerMain.print("Reading password from client");
					for (; i != 7; i = is.read()) {
						p += String.valueOf((char) i);
					}

					ServerMain.print("Confirm password validity, password received is: "+p);
					if (pass.equals(p)) {
						checkRes();
						ServerMain.print("Width: "+RobotHandler.width+"\tHeight: "+RobotHandler.height+
							"\nSending ACK");
						socket.getOutputStream().write(7);
						accepted = true;
					}

					else {
						ServerMain.print("Password check failed, sending NACK");
						socket.getOutputStream().write(11);
					}

				} catch (IOException e) {
					ServerMain.ePrint("IO fail: "+e.getMessage());
					e.printStackTrace();
				}
			}
		}

		else if (!ServerMain.NEED_PASSWORD) {
			try {
				ServerMain.print("No password needed, sending ACK");
				socket.getOutputStream().write(11);
				checkRes();
				ServerMain.print("Width: "+RobotHandler.width+"\tHeight: "+RobotHandler.height);
				accepted = true;

			} catch (IOException e) {
				ServerMain.ePrint("IO fail: "+e.getMessage());
				e.printStackTrace();
			}
		}

		else {
			ServerMain.print("Unknown action, aborting.\nWhat did you do?");
		}

		try {
			socket.getOutputStream().write(7);
		} catch (IOException e) {
			ServerMain.ePrint(e.getMessage());
			e.printStackTrace();
		}
		readInfo();
	}

	private void checkRes() {
		try {
			is = socket.getInputStream();
			String resX = "";
			String resY = "";
			int i = is.read();
			ServerMain.print("Acquire client screen resolution");
			for (; i != 'x'; i = is.read())
				resX += String.valueOf((char) i);
			i = is.read();
			for (; i != 'y'; i = is.read())
				resY += String.valueOf((char) i);
			RobotHandler.width = Integer.valueOf(resX);
			RobotHandler.height = Integer.valueOf(resY);
		} catch (IOException e) {
			ServerMain.ePrint(e.getMessage());
		}
	}

	private void readInfo() {
		if (accepted) {
			ServerMain.print("Connected");
			try {
				is = socket.getInputStream();
				while (!stop) {
					int in = is.read();
					char c = (char) in;

					// Move mouse
					if (in == 15) {
						getCords();
					}

					else if(in == 6){
						sendScreen();
					}

					else if(in == 12){
						sendScreen2();
					}

					// Macro
					else if (in == 25) {
						ServerMain.print("Run macro");
						macro();
					}

					//Mouse click
					else if (in == 1 || in == 2 || in == 13 || in == 14) {
						robotHandler.Click(in);
					}

					// Backspace, enter, characters
					else if (in == 8 || in == 10 || in == 20 || in == 23 || in == 26) {
						robotHandler.Write(c);
					}
					else if( in > 31){
//						robotHandler.Write(deCrypt(in));
						robotHandler.Write(c);
					}
					if (in == -1 || in == 3) {
						ServerMain.print("Connection reset by client");
						break;
					}

				}
			} catch (IOException e) {
				ServerMain.ePrint("IO fail: "+e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// Macro method
	public void macro() {
		try {
			// Read in next char
			int i = is.read();
			char release = ' ';

			// Read in next char until end of macro
			for (; i != ';' && i != 25; i = is.read()) {
				char c = (char) i;
				// Key hold
				if (i == 9 || c == 16 || i == 17 || i == 18) {
					release = c;
				}
				else if (i == 22) {
					release = KeyEvent.VK_WINDOWS;
				}
				robotHandler.Write(c);
			}
			i = is.read();
			// Continuation of 3-command macros, supposedly doesn't work
			// Read in next char until end of macro
			for (; /*i != ';' &&*/i != 25; i = is.read()) {
				char c = (char) i;
				robotHandler.Write(c);
			}
			// Key release
			robotHandler.releaseMacro(release);
		} catch (IOException e) {
			ServerMain.ePrint(e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendScreen(){

		try {
			ServerMain.print("Sending image");
			BufferedImage image = RobotHandler.takeScreen();
			OutputStream os = socket.getOutputStream();
			os.flush();
			ImageIO.write(image, "png", os);
			os.flush();
			os.write(new byte[] { 0, -1, 0 });
			ServerMain.print("Image sent");
		} catch (IOException e) {
			ServerMain.ePrint("IO fail: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendScreen2(){
			try {

				ServerMain.print("Sending image");
	//			BufferedImage image = ImageIO.read(new File("C:/Users/My Computer/Desktop/tiim.png"));
				BufferedImage image2 = RobotHandler.takeScreen2();
				OutputStream ios = socket.getOutputStream();
				ios.flush();

				//ImageIO.write(Scalr.resize(image2, Method.BALANCED,500), "png", ios);
				ImageIO.write(Scalr.resize(image2, Method.SPEED,800), "png", ios);
				//ImageIO.write(image, "png", os);
				ios.flush();
				ios.write(new byte[] { 0, -1, 0 });

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	// Move mouse
	public void getCords() {
		try {
			int x, y;
			// Read in next char
			int i = is.read();
			String s = "";

			// Get X-coord delta
			for (; i != ':'; i = is.read()) {
				// 45: -, 57: 9. Wat? No comprende.
				if (i >= 45 && i <= 57) {
					s += String.valueOf((char) i);
				}
			}
			x = Integer.parseInt(s);

			s = "";
			i = is.read();
			// Get Y-coord delta
			for (; i != 15; i = is.read()) {
				if (i >= 45 && i <= 57) {
					s += String.valueOf((char) i);
				}
			}
			y = Integer.parseInt(s);
			// Apply mouse movement delta
			robotHandler.Move(x, y);
		} catch (IOException e) {
			ServerMain.ePrint(e.getMessage());
			e.printStackTrace();
		}
	}

	// Currently not supported
/*	private char deCrypt(int in) {
		in = in-31;
		long out = ((long)in*151)%225;
		out = out+31;
		return (char)out;
	}*/

	public boolean isConnected() {
		if (socket == null)
			return false;
		return socket.isConnected();
	}

	public Connection(int port) {
		this.port = port;
		this.run = true;
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(500);
		} catch (IOException e) {
			ServerMain.ePrint("Port error: "+e.getMessage());
			e.printStackTrace();
		}
		socket = new Socket();
		if (port == 0)
			port = 9001;
	}
}
