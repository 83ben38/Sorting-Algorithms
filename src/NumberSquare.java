import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;

public class NumberSquare extends GCompound {
    int value;
    GLabel label;
    double size;
    GRect outline;
    static double width;
    public NumberSquare(int number){
        value = number;
        label = new GLabel(number + "");
        size = width / SortingAlgorithms.numSquares;
        label.setFont(new Font(Font.DIALOG,Font.BOLD,(int)(size*2/3)));
        add(label,(size/2)-label.getWidth()/2,size * 5 / 6);
        outline = new GRect(size,size);
        add(outline);
        outline.setFillColor(Color.white);
        outline.setFilled(true);
        outline.sendToBack();
        int v = (int)(255 * number % (SortingAlgorithms.numSquares / 6.0) / (SortingAlgorithms.numSquares / 6.0));
        outline.setColor(switch ((int)(number / (SortingAlgorithms.numSquares / 6.0))){
            case 0 -> new Color(255,  v,0);
            case 1 -> new Color(255-v,255,0);
            case 2 -> new Color(0,255,v);
            case 3 -> new Color(0,255-v,255);
            case 4 -> new Color(v,0,255);
            case 5 -> new Color(255,0,255-v);
            default -> new Color(0,0,0);

        });
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
