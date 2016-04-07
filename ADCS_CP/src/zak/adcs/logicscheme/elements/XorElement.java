package zak.adcs.logicscheme.elements;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

@XStreamAlias("xorelement")
public class XorElement extends LogicElement {

    private static final String XOR_TEXT = "=1";

    public XorElement() {
        super();
    }

    public XorElement(int x, int y, int width, int height, Color standartColor, Color selectedColor,
                      Color textColor, Font font, String name, int inertiaDelay, int dynamicDelay, int inCount) {
        super(x, y, width, height, standartColor, selectedColor, textColor, font, name, inertiaDelay, dynamicDelay,
                inCount);
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(textColor);
        g2.setFont(font);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D stringBounds = font.getStringBounds(XOR_TEXT, context);
        g2.drawString(XOR_TEXT, x + width / 6 + (int) ((width / 3 * 2 - stringBounds.getWidth()) / 2),
                (int) (y + stringBounds.getHeight()));
    }

}
