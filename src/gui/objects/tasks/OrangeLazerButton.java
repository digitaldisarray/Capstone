package gui.objects.tasks;

import gui.objects.ButtonTask;

import java.awt.Color;

import base.Launcher;

public class OrangeLazerButton implements ButtonTask {


	@Override
	public void run(Color color) {
		Launcher.getGame().getPlayer().setLazerColor(Color.ORANGE);

	}

}
