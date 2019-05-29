package base;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**Represents a sound object that can play sounds simultaneously, loops sounds, and play sounds a single time
 * 
 * Forked from Ben Leistiko, thanks for all his help with getting a sound file to work properly
 * @author Ben Leistiko
 * 
 * @version 5/20/18 7:51 
 *
 */
public class BetterSound extends Thread {
	Clip c;
	boolean loop;
	FloatControl volume;

	public BetterSound(File f, boolean runWhenCreated, boolean loop) {
		this.loop = loop;
		try {
			c = AudioSystem.getClip();
			c.open(AudioSystem.getAudioInputStream(f));
			volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (LineUnavailableException e) {
			System.out.println("ERROR, line unavalible");
		} catch (IOException e) {
			System.out.println("ERROR, IO");
		} catch (UnsupportedAudioFileException e) {
			System.out.println("ERROR, unspported");
		}
		if (runWhenCreated)
			start();
		
	}

	/**
	 * WHATEVER YOU DO DON'T CALL THIS!!!!!!!!!!!!!!!!! just call something called
	 * start() in the thread class DON'T BE DUMB, DON'T CALL THIS please, I really
	 * mean it!
	 */
	public void run() {
		if(loop)
			c.loop(c.LOOP_CONTINUOUSLY);
		c.start();
	}

	/**
	 * Sets the volume of this clip
	 * @param vol - 0 to 1
	 */
	public void setVolume(double vol) {
		volume.setValue((float) ((volume.getMaximum() - volume.getMinimum())*vol+volume.getMinimum()));
	}

	public boolean isRunning() {
		return c.isRunning();
	}
}
