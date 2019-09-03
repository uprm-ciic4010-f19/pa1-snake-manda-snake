package UI;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class UIImageButton extends UIObject{
    private BufferedImage[] images;
    private ClickListlener clicker;

    public UIImageButton(float x, float y, int width, int height, BufferedImage[] images,ClickListlener clicker ) {
        super(x - 30, y + 3, width + 50, height + 50);
        this.images=images;
        this.clicker=clicker;
    }


    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if(active){
            if(images.length==3) {
                g.drawImage(images[2], (int) x - 35, (int) y - 25, width + 100, heith + 100, null);
            }
        }
        else if(hovering){
            g.drawImage(images[1],(int)x - 35,(int)y - 25,width + 100,heith + 100,null);
        }else{
            g.drawImage(images[0],(int)x - 35,(int)y - 25,width + 100,heith + 100,null);

        }
    }


    @Override
    public void onClick()
    {
        clicker.onClick();
    }
}
