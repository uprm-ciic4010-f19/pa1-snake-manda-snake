package Game.Entities.Static;

import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;

    public int xCoord;
    public int yCoord;
    
    private boolean good;
    public int steps;

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
        this.good = true;
        this.steps = 0;
    }
    
    public boolean isGood() {
    	return good;
    }
    
    public void setGood(boolean var) {
    	this.good = var;
    }


}
