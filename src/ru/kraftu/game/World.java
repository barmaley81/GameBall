package ru.kraftu.game;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class World {
	public static final int SPEED_FRAME = 1000;
	public static final int PAGE_SIZE = 10;
	private ArrayList<ObjectWorld> list = new ArrayList<ObjectWorld>();
	private ArrayList<ObjectWorld> list2 = new ArrayList<ObjectWorld>();
	private ArrayList<ObjectWorld> list3 = new ArrayList<ObjectWorld>();
	private ObjectWorld player;
	private ObjectWorld stone;
	private Frame frame;
	boolean gameWork;
	Random r = new Random();
	int score = 0;

	public void startGameLoop() throws InterruptedException, IOException {
		gameWork = true;
		frame = new Frame();
		InputStream in = System.in;
		initWorld();
		int frameCounter = 0;
		char input = '1';
		while (gameWork) {

			// Убираем муссор с экрана
			frame.clearConsol();

			boolean newChar = false;
			if (in.available() > 0) {
				System.out.println(String.format("available:%d", in.available()));
				char buff = (char) in.read();
				in.read();
				in.read();
				input = buff;
				newChar = true;
			}

			System.out.println(String.format("Frame:%d", frameCounter++));
			if (newChar)
				System.out.println("input:" + input);
			// Отчищаем наш кадр от мусора
			frame.clearFrame();

			// Тут мы вычисляем, кто где находиться, если нужно переставляем
			// объекты
			calculationWorld(newChar, input);

			// Рисуем наши объекты на карте
			drawWorld();
			
			// Выводим на экран, то что нарисовали
			frame.printFrame();
			

			// Ждем
			Thread.currentThread().sleep(SPEED_FRAME);
		}
		System.out.println("Score: " + score);
		System.out.println("Finish game");
	}

	public void initWorld() {
		// ObjectWorld object = new ObjectWorld('#', 0, 0, 0, 0, true);
		// list.add(object);
		// object = new ObjectWorld('#', 0, 1, 0, 0, true);
		// list.add(object);
		player = new ObjectWorld('*', 5, 5, 0, 0, false);
		list.add(player);
		for (int i = 0; i < PAGE_SIZE; i++) {
			for (int j = 0; j < PAGE_SIZE; j++) {
				if (i == 0 || i == PAGE_SIZE - 1) {

					list.add(new ObjectWorld('#', i, j, 0, 0, true));
					continue;
				}
				if (((i > 0 || i < PAGE_SIZE)) && ((j == 0 || j == PAGE_SIZE - 1))) {
					list.add(new ObjectWorld('#', i, j, 0, 0, true));
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			 stone=new ObjectWorld('s', r.nextInt(8) + 1, r.nextInt(8) + 1, 0, 0, false);
			list.add(stone);			
		}
	}

	public void calculationWorld(boolean newChar, char ch) {

		if (newChar) {
			switch (ch) {
			case 'w': {
				// player.setLocation(player.getX()-1, player.getY());
				player.setSpeed(-1, 0);
				break;
			}
			case 's': {
				// player.setLocation(player.getX()+1, player.getY());
				player.setSpeed(1, 0);
				break;
			}
			case 'a': {
				// player.setLocation(player.getX(), player.getY()-1);
				player.setSpeed(0, -1);
				break;
			}
			case 'd': {
				// player.setLocation(player.getX(), player.getY()+1);
				player.setSpeed(0, 1);
				break;
			}
			}
		}
		player.nextStep();

		for (ObjectWorld ball : list) {
			// ball.nextStep();

			// Проверка, что бы наш объект не вышел за пределы экрана
			// Если он вышел за пределы экрана, мы его вернем в позицию (0,0)
			// (Можно
			// направление поменять)
			/*
			 * if (boll.getX() < 0 || boll.getX() > PAGE_SIZE - 1 || boll.getY()
			 * < 0 || boll.getY() > PAGE_SIZE - 1) { boll.setLocation(0, 0); }
			 */

			if (ball.isWall()) {
				list2.add(ball);
			} else
				list3.add(ball);

		}

		for (ObjectWorld ob1 : list3) {

			for (ObjectWorld ob2 : list2) {
				if ((ob1.getX() == ob2.getX()) && (ob1.getY() == ob2.getY())) {
					// System.out.println("Game over");
					gameWork = false;
				}
			}
		}
		for (ObjectWorld ob1 : list3) {
			for (ObjectWorld ob2 : list3) {
				if (ob1 != ob2) {
					if ((ob1.getX() == ob2.getX()) && (ob1.getY() == ob2.getY())) {
						ob2.setLocation(r.nextInt(8) + 1, r.nextInt(8) + 1);
						score++;
					}
				}
			}
		}
	}

	public void drawWorld() {
		for (ObjectWorld boll : list) {
			frame.setChar(boll.getImage(), boll.getX(), boll.getY());
		}

	}
}