/*Class: EnemyPlayer
 * 
 * Purpose: To set framework for the game. Creates some graphics objects,windows, and menus
 * Author: The Mustangs
 * Last edited: 5/22/2019
 * */
package entities;

import java.awt.Color;

//represents the enemy player
public class EnemyPlayer extends Entity {

	//creates the enemy player
	public EnemyPlayer(int x, int y, int id) {
		super(x, y, 10, 10, Color.GRAY);
		setID(id);
	}

}
