import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;

public class NumberSquare extends GCompound {
    int value;
    GLabel label;
    double size;
    GRect outline;
    public NumberSquare(int number){
        value = number;
        label = new GLabel(number + "");
        size = 240.0 / SortingAlgorithms.numSquares;
        label.setFont(new Font(Font.DIALOG,Font.BOLD,(int)(size*2/3)));
        add(label,(size/2)-label.getWidth()/2,size * 5 / 6);
        outline = new GRect(size,size);
        add(outline);
        outline.setFillColor(Color.white);
        outline.setFilled(true);
        outline.sendToBack();
    }
    public void setNum(int num){
        value = num;
        label.setLabel(num + "");
        label.setLocation((size/2)-label.getWidth()/2,size * 5 / 6);
    }

    @Override
    public void setColor(Color color) {
        outline.setFillColor(color);
    }
}
