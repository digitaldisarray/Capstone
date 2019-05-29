/*
 * This class represents a button task that starts the game and resets zombie kills
 * Author: The Mustangs
 * Last edited: 5/22/2019
 */
package gui.objects.tasks;

import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;
import entities.Zombie;

public class StartButton implements ButtonTask {

	@Override
	public void run(Color color) {
		Launcher.getGame().setInGame(true);
		Launcher.getGame().getPlayer().resetHealth();
		Zombie.resetZombieKills();
	}

}
