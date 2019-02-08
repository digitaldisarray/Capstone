package xyz.disarray.fortnut.entities.statics;

import xyz.disarray.fortnut.Handler;
import xyz.disarray.fortnut.entities.Entity;

public abstract class StaticEntity extends Entity {
	
	public StaticEntity(Handler handler, float x, float y, int width, int height){
		super(handler, x, y, width, height);
	}

}
