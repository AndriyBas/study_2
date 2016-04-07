package zak.adcs.logicscheme.elements;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.awt.*;

@XStreamAlias("norelement")
public class NorElement extends OrElement {

    public NorElement() {
        super();
    }

    public NorElement(int x, int y, int width, int height, Color standartColor, Color selectedColor, Color textColor,
                         Font font, String name, int inertiaDelay, int dynamicDelay, int inCount) {
        super(x, y, width, height, standartColor, selectedColor, textColor, font, name, inertiaDelay, dynamicDelay,
                inCount);
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(textColor);
        g2.drawOval(x + width / 6 * 5 - width / 12, y + height / 2 - width / 12, width / 6, width / 6);
    }

}
