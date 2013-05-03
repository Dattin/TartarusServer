//package com.tiim.server;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class RobotHandler {
	static int x = 0, y = 0, height = 0, width = 0;
	static boolean zoomedMode = false;

	public static BufferedImage takeScreen() {
		Robot r;
		BufferedImage image = null;
		BufferedImage pointer = null;
		Point mousePos = null;

		try {
			int w, h;
			if (zoomedMode) {
				w = width / 2;
				h = height / 2;
			}
			else {
				w = width;
				h = height;
			}

			r = new Robot();
			// Future inputs from sendScreen when not in fullscreen
			x = 0;
			y=0;
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			mousePos = MouseInfo.getPointerInfo().getLocation();

			// X
			if (w >= screenSize.width) {
				x = 0;
				w = screenSize.width;
			} else if (mousePos.x - w / 2 < 0)
				x = 0;
			else if (mousePos.x + w / 2 > screenSize.width)
				x = screenSize.width - w;
			else
				x = mousePos.x - w / 2;

			// Y
			if (h >= screenSize.height) {
				y = 0;
				h = screenSize.height;
			} else if (mousePos.y - h / 2 < 0)
				y = 0;
			else if (mousePos.y + h / 2 > screenSize.height)
				y = screenSize.height - h;
			else
				y = mousePos.y - h / 2;

			Rectangle screenRectangle = new Rectangle(x,y, w, h);
			image = r.createScreenCapture(screenRectangle);

		} catch (AWTException e) {
			ServerMain.ePrint(e.getMessage());
			e.printStackTrace();
		    }

			try {
				InputStream yo = RobotHandler.class.getClassLoader().getResourceAsStream("images/pointer.png");
				pointer = (ImageIO.read(yo));
			} catch (IOException e) {
				ServerMain.ePrint(e.getMessage());
				e.printStackTrace();
			}

			Graphics2D g = image.createGraphics();
			g.drawImage(pointer, mousePos.x - x, mousePos.y - y, null);


		System.out.println(image);
		return image;
	}

	public static BufferedImage takeScreen2(){

		Robot r;
		BufferedImage image2 = null;
		BufferedImage pointer = null;
		Point mousePos = null;
//		int x = 0,y = 0, height = 0,width = 0;

		try {
			r = new Robot();
			x = 0;//future inputs from sendScreen when not fullscreen
			y=0;
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//			width = screenSize.width;
//			height = screenSize.height;
//			width = 100;
//			height = 100;
			width = screenSize.width/3;
			height = screenSize.height/3;
			mousePos = MouseInfo.getPointerInfo().getLocation();

			// X
			if (width >= screenSize.width) {
				x = 0;
				width = screenSize.width;
			} else if (mousePos.x - width / 2 < 0)
				x = 0;
			else if (mousePos.x + width / 2 > screenSize.width)
				x = screenSize.width - width;
			else
				x = mousePos.x - width / 2;

			// Y
			if (height >= screenSize.height) {
				y = 0;
				height = screenSize.height;
			} else if (mousePos.y - height / 2 < 0)
				y = 0;
			else if (mousePos.y + height / 2 > screenSize.height)
				y = screenSize.height - height;
			else
				y = mousePos.y - height / 2;

			Rectangle screenRectangle = new Rectangle(x,y,width, height);
			image2 = r.createScreenCapture(screenRectangle);

//			InputStream is = getClass().getResourceAsStream("images/pointer.png");
//			InputStream is = RobotHandler.class.getResourceAsStream("images/pointer.png");

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			ServerMain.ePrint(e.getMessage());
			e.printStackTrace();
			}

			try {
				InputStream yo = RobotHandler.class.getClassLoader().getResourceAsStream("images/pointer.png");
				pointer = (ImageIO.read(yo));
			} catch (IOException e) {
				ServerMain.ePrint(e.getMessage());
				e.printStackTrace();
			}

			Graphics2D g = image2.createGraphics();
			g.drawImage(pointer, mousePos.x - x, mousePos.y - y, null);


		System.out.println(image2);
		return image2;
}

	//Input: change in mouse location
	public void Move(int dX, int dY) {
		try {
			Robot r = new Robot();
			PointerInfo point = MouseInfo.getPointerInfo();
			//Get the current mouse location
			Point current = point.getLocation();
			int x = (int) current.getX();
			int y = (int) current.getY();
			//Apply the change
			r.mouseMove(x + dX, y + dY);
		} catch (AWTException e) {
			ServerMain.ePrint(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void Click(int i) {
		Robot r;
		try {
			r = new Robot();

			// Left click
			if (i == 1) {
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
			}

			// Right click
			else if (i == 2) {
				r.mousePress(InputEvent.BUTTON3_MASK);
				r.mouseRelease(InputEvent.BUTTON3_MASK);
			}

			else if (i == 13) {
				r.mousePress(InputEvent.BUTTON1_MASK);
			}

			else if (i == 14) {
				r.mouseRelease(InputEvent.BUTTON1_MASK);
			}
		} catch (AWTException e) {
			ServerMain.ePrint(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void Write(char c) {
		try {
			Robot r = new Robot();

			if (c >= 'A' && c <= 'Z') {
				r.keyPress(KeyEvent.VK_SHIFT);
				r.keyPress(c);
				r.keyRelease(KeyEvent.VK_SHIFT);
			}

			else if (c >= 'a' && c <= 'z') {
				c = Character.toUpperCase(c);
				r.keyPress(c);
			}

			else if (c >= '0' && c <= '9') {
				r.keyPress(c);
			}

			else {
				Special(c);
			}

		} catch (AWTException e) {
			ServerMain.ePrint(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void Special(char c) {
		try {
			Robot r = new Robot();

			switch (c) {
			// BACKSPACE
			case (char) 8:
				r.keyPress(8);
				break;

			// TAB
			case (char) 9:
				r.keyPress(c);
				break;

			// ENTER
			case (char) 10:
				r.keyPress(c);
				break;

			// SHIFT
			case (char) 16:
				r.keyPress(c);
				break;

			// CTRL
			case (char) 17:
				r.keyPress(c);
				break;

			// ALT
			case (char) 18:
				r.keyPress(c);
				break;

			// F4
			case (char) 19:
				r.keyPress(KeyEvent.VK_F4);
				break;

			// F5
			case (char) 20:
				r.keyPress(KeyEvent.VK_F5);
				break;

			// Windows
			case (char) 22:
				r.keyPress(KeyEvent.VK_WINDOWS);
//				r.keyRelease(KeyEvent.VK_WINDOWS);
				break;

			// left arrow
			case (char) 23:
				r.keyPress(KeyEvent.VK_LEFT);
				break;

			// up arrow
			case (char) 24:
				r.keyPress(KeyEvent.VK_UP);
				break;

			// right arrow
			case (char) 26:
				r.keyPress(KeyEvent.VK_RIGHT);
				break;

			// down arrow
			case (char) 27:
				r.keyPress(KeyEvent.VK_DOWN);
				break;

			// DELETE
			case (char) 127:
				r.keyPress(c);
				break;

			// SPACE
			case ' ':
				r.keyPress(c);
				break;

			case '!':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '"':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '#':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '$':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '%':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '&':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD8);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case (char) 39: //'
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '(':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD0);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case ')':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '*':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '+':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case ',':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '-':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '.':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '/':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case ':':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD8);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case ';':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '<':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD0);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '=':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '>':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '?':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '@':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '[':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case (char) 92: // \
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case ']':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '^':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '_':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '`':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '{':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '|':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '}':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '~':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'Ç':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyPress(KeyEvent.VK_NUMPAD8);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ü':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'é':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD0);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'â':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ä':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyRelease(KeyEvent.VK_ALT);

				break;

			case 'à':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_ALT);

				break;

			case 'å':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ç':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ê':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ë':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'è':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD8);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ï':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'î':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD0);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ì':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'Ä':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'Å':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'É':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'æ':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'Æ':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ô':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ö':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD8);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ò':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'û':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD0);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ù':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ÿ':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'Ö':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'Ü':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ø':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyRelease(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '£':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'Ø':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '×':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD8);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ƒ':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'á':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD0);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'í':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ó':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ú':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ñ':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'Ñ':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'ª':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyRelease(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case 'º':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '¿':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD8);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '®':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyPress(KeyEvent.VK_NUMPAD9);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '¬':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyPress(KeyEvent.VK_NUMPAD0);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '½':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '¼':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '¡':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyPress(KeyEvent.VK_NUMPAD3);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '«':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '»':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD1);
				r.keyPress(KeyEvent.VK_NUMPAD7);
				r.keyPress(KeyEvent.VK_NUMPAD5);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			case '÷':
				r.keyPress(KeyEvent.VK_ALT);
				r.keyPress(KeyEvent.VK_NUMPAD2);
				r.keyPress(KeyEvent.VK_NUMPAD4);
				r.keyPress(KeyEvent.VK_NUMPAD6);
				r.keyRelease(KeyEvent.VK_ALT);
				break;

			default:
				ServerMain.print("Unknown input. Wat do?");
				break;
			}

		} catch (AWTException e) {
			ServerMain.ePrint(e.getMessage());
			e.printStackTrace();
		}
	}

	// Key release
	public void releaseMacro(char c) {
		try {
			Robot r = new Robot();
			r.keyRelease(c);
		} catch (AWTException e) {
			ServerMain.ePrint(e.getMessage());
			e.printStackTrace();
		}
	}
}
