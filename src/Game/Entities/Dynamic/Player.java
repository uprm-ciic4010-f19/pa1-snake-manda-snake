package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Random;

import Game.GameStates.State;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

    public int lenght;
    public boolean justAte;
    private Handler handler;
    public int speed;
    public int xCoord;
    public int yCoord;
    public double score;

    public int moveCounter;

    public String direction;//is your first name one?

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        direction= "Right";
        justAte = false;
        lenght= 1;
        speed = 8;
        score = 0;

    }

    public void tick(){
        moveCounter++;
        if(moveCounter>=speed) {
            checkCollisionAndMove();
            moveCounter=0;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)&&(direction!="Down")){
            direction="Up";
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)&&(direction!="Up")){
            direction="Down";
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)&&(direction!="Right")){
            direction="Left";
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)&&(direction!="Left")){
            direction="Right";
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)){
        	speed++;
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)){
        	speed--;
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){
        	addTail();
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
            State.setState(handler.getGame().pauseState);
        }




    }

    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        switch (direction){
            case "Left":
                if(xCoord==0){
                	xCoord=handler.getWorld().GridWidthHeightPixelCount-1;
                }else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                	xCoord=0;
                }else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                	yCoord=handler.getWorld().GridWidthHeightPixelCount-1;
                }else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                	yCoord=0;
                }else{
                    yCoord++;
                }
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;


        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat();
        }

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }

    }

    public void render(Graphics g,Boolean[][] playeLocation){
        Random r = new Random();
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(Color.GREEN);

                if(playeLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                }
                
                if(handler.getWorld().appleLocation[i][j]) {
                    g.setColor(Color.RED);

                	
                	if (handler.getGame().gameState.world.apple.isGood() == true) {
                		g.fillRect((i*handler.getWorld().GridPixelsize),
                                (j*handler.getWorld().GridPixelsize),
                                handler.getWorld().GridPixelsize,
                                handler.getWorld().GridPixelsize);
                	}
                	else {
                        g.setColor(Color.YELLOW);
                        
                        g.fillRect((i*handler.getWorld().GridPixelsize),
                                (j*handler.getWorld().GridPixelsize),
                                handler.getWorld().GridPixelsize,
                                handler.getWorld().GridPixelsize);

                	}
                }

            }
        }

        g.setFont(new Font("serif", Font.PLAIN, 30));
        g.drawString("Score: "+String.valueOf(new DecimalFormat("##.##").format(score)),10, 40);

    }

    public void Eat(){
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        
        if (handler.getGame().gameState.world.apple.isGood() == true) {
            addTail();
            score += Math.sqrt(2*(score+1));	
        }
        else {
        	removeTail();
            score -= Math.sqrt(2*(score+1));	
        }
    }

    public void addTail() {
    	speed--;
    	lenght++;
        Tail tail= null;
    	switch (direction){
        case "Left":
            if( handler.getWorld().body.isEmpty()){
                if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                    tail = new Tail(this.xCoord+1,this.yCoord,handler);
                }else{
                    if(this.yCoord!=0){
                        tail = new Tail(this.xCoord,this.yCoord-1,handler);
                    }else{
                        tail =new Tail(this.xCoord,this.yCoord+1,handler);
                    }
                }
            }else{
                if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                    tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                    }else{
                        tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                    }
                }

            }
            break;
        case "Right":
            if( handler.getWorld().body.isEmpty()){
                if(this.xCoord!=0){
                    tail=new Tail(this.xCoord-1,this.yCoord,handler);
                }else{
                    if(this.yCoord!=0){
                        tail=new Tail(this.xCoord,this.yCoord-1,handler);
                    }else{
                        tail=new Tail(this.xCoord,this.yCoord+1,handler);
                    }
                }
            }else{
                if(handler.getWorld().body.getLast().x!=0){
                    tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }
                }

            }
            break;
        case "Up":
            if( handler.getWorld().body.isEmpty()){
                if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                    tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                }else{
                    if(this.xCoord!=0){
                        tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                    }else{
                        tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                    }
                }
            }else{
                if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                    tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                    }
                }

            }
            break;
        case "Down":
            if( handler.getWorld().body.isEmpty()){
                if(this.yCoord!=0){
                    tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                }else{
                    if(this.xCoord!=0){
                        tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                    }else{
                        tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                    } System.out.println("Tu biscochito");
                }
            }else{
                if(handler.getWorld().body.getLast().y!=0){
                    tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                    }
                }

            }
            break;
    }
    handler.getWorld().body.addLast(tail);
    handler.getWorld().playerLocation[tail.x][tail.y] = true;

    }
    
    public void removeTail() {
    	if (lenght > 1) { 
	    	speed++;
	    	lenght--;
	    	Tail tail = handler.getWorld().body.getLast();
	        handler.getWorld().playerLocation[tail.x][tail.y] = false;
		    handler.getWorld().body.removeLast();
		    tail = null;
    	}
    	else {
            State.setState(handler.getGame().gameOverState);
    	}
    }
    
    public void kill(){
        lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

                handler.getWorld().playerLocation[i][j]=false;

            }
        }
    }

    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }
}
