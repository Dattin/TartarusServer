package com.tiim.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Connection implements Runnable {

	private Socket socket;
	private ServerSocket server;
	private int port;
	private InputStream is;
	private boolean run = true;
	private boolean stop = false;
	RobotHandler robotHandler = new RobotHandler();

	public static void main(String[] args) {
		Connection connectionSocket = new Connection(9000, null);
		connectionSocket.run();
	}

	public void stop() {
		stop = true;
		while (run) {
			try {
				Thread.sleep(100);
				System.out.println("Waiting for socket to close");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isRunning() {
		return run;
	}

	private void close() {
		try {
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (!socket.isClosed())
			;
		while (!server.isClosed())
			;
		run = false;
		System.out.println("Stopped");
		ServerMain.ButtonReset();
	}

	public void run() {
		System.out.println("Listening to port: " + port);
		while (!isConnected()) {
			if (stop) {
				close();
				return;
			}
			try {
				//System.out.println("Hej");
				socket = server.accept();
			} catch (SocketTimeoutException e) {
			} catch (SocketException e) {
			} catch (IOException e) {
			}
		}
		System.out.println("Connected");
		readInfo();
		System.out.println("Done reading, closing");
		close();
	}

	private void readInfo() {
		try {
			is = socket.getInputStream();
			while (!stop) {
				// System.out.println(socket.isClosed());

				/*
				 * if (is.available() == 0) // Thread.sleep(100); continue;
				 */
				int in = is.read();
				// int inByte = is.read()
				boolean caps = false;//set caps lock to false as default
				char c = (char) in;
				System.out.println("C:" + c + " I:" + in + " ");

				if (in == 15) {//mouse move
					System.out.println("move le mouse");
					getCords();
				}

				else if (in == 21) {//caps lock toggler
					System.out.println("ALL CAPSSS");
					caps ^= true;//change the boolean
					robotHandler.toggleCaps(caps);
				}

				else if (in == 25) {//macro
					System.out.println("MACRO HARDER");
					macro();
				}

				else if (in == 1 || in == 2) {//mouse click
					System.out.println("EL CLICKO");
					robotHandler.Click(in);
				}

				else if (in == 0 || in == 10 || in > 31) {//backspace, enter, characters
					System.out.println("MISTER ROBOTO");
					robotHandler.Write(c);
				}

				if (in == -1 || in == 3) {
					System.out.println("Connection reset by client");
					//shglgkflhlyjuyitiuoServerMain.ButtonReset();
					break; // checkInt(in);
				}

			}
		} catch (IOException e) {
			System.out.println("It broke");
			e.printStackTrace();
		}
	}

//	public void caps(boolean caps) {//toggle the caps
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		toolkit.setLockingKeyState(KeyEvent.VK_CAPS_LOCK, caps);
//	}

	public void macro() {//macro
		try {
			int i = is.read();//read in the next char
			char release = ' ';

			for (; i != ';' && i != 25; i = is.read()) { //read in till next command or end of macro
				char c = (char) i;
				if (i == 9 || c == 16 || i == 17 || i == 18) { // holding a key
					release = c;
				}
				robotHandler.Write(c);
			}
			i = is.read();
			// fortsättning för 3 commands i macro, funkar inte
			for (; /*i != ';' &&*/i != 25; i = is.read()) {//read in till end of macro
				char c = (char) i;
				robotHandler.Write(c);
			}
			robotHandler.releaseMacro(release); //release the key
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getCords() {//mouse mover
		try {
			int x, y;
			int i = is.read();//read in the next char
			String s = "";

			for (; i != ':'; i = is.read()) {//get the change in x
				if (i >= 45 && i <= 57) { // 45: -, 57: 9
					s += String.valueOf((char) i);
				}
			}
			x = Integer.parseInt(s);
			System.out.println("s: " + s + " x: " + x);
			
			s = "";
			i = is.read();
			for (; i != 15; i = is.read()) {//get the change in y
				if (i >= 45 && i <= 57) {
					s += String.valueOf((char) i);
				}
			}
			y = Integer.parseInt(s);
			System.out.println("s: " + s + " y: " + y);
			robotHandler.Move(x, y);//apply the movement change
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		if (socket == null)
			return false;
		return socket.isConnected();
	}

	public Connection(int port, String keyboard) {
		this.port = port;
		this.run = true;
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(500);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("SHIT WONT WORK BOSS!");
		}
		socket = new Socket();
		if (port == 0)
			port = 9001;
	}
}
