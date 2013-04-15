package com.tiim.server;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RobotHandler {
	public static void main(String[] args) {
		int m = 66;
		char c = ' ';

		if (m == 0 || m == 10 || m > 31) { // 0 = backspace, 10 = enter, 31 < letters
			c = (char) m; //convert to char
			Write(c);
		}

		else if (m == 1 || m == 2) {
			Click(m);
		}
	}

	public void Move(int dX, int dY) { //Input: change in mouse location
		try {
			Robot r = new Robot();
			PointerInfo point = MouseInfo.getPointerInfo();
			Point current = point.getLocation(); //Get the current mouse location
			int x = (int) current.getX();
			int y = (int) current.getY();
			r.mouseMove(x + dX, y + dY); //Apply the change
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	public static void Click(int i) {
		Robot r;
		try {
			r = new Robot();

			if (i == 1) { // Left click
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
			}

			else if (i == 2) { // Right click
				r.mousePress(InputEvent.BUTTON3_MASK);
				r.mouseRelease(InputEvent.BUTTON3_MASK);
			}
		} catch (AWTException e) {
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
			e.printStackTrace();
		}
	}

	public static void Special(char c) {
		try {
			Robot r = new Robot();

			switch (c) {
			case (char) 0: //BACKSPACE
				r.keyPress(8);
				break;

			case (char) 9: //TAB
				r.keyPress(c);
				break;

			case (char) 10: //ENTER
				r.keyPress(c);
				break;

			case (char) 16://SHIFT
				r.keyPress(c);
				break;

			case (char) 17://CTRL
				r.keyPress(c);
				break;

			case (char) 18://ALT
				r.keyPress(c);
				break;

			case (char) 19://F4
				r.keyPress(KeyEvent.VK_F4);
				break;

			case (char) 20://F5
				r.keyPress(KeyEvent.VK_F5);
				break;

			case (char) 127://DELETE
				r.keyPress(c);
				break;

			// case (char) 16){ //SHIFT
			// r.keyPress(c);
			// }

			case ' '://SPACE
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

			// case ' ': //[Ungefär =]
			// r.keyPress(KeyEvent.VK_ALT);
			// r.keyPress(KeyEvent.VK_NUMPAD2);
			// r.keyPress(KeyEvent.VK_NUMPAD4);
			// r.keyPress(KeyEvent.VK_NUMPAD7);
			// r.keyRelease(KeyEvent.VK_ALT);
			// break;
			default:
				System.out.println("No Understand");
				break;
			}

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void releaseMacro(char c) {//release the key
		try {
			Robot r = new Robot();
			r.keyRelease(c);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void toggleCaps(boolean caps) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		toolkit.setLockingKeyState(KeyEvent.VK_CAPS_LOCK, caps);
	}
}
