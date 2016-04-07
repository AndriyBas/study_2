package zak.adcs.logicscheme.modelling;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

public class TimeDiagramPanel extends JPanel implements Scrollable {

    private ModellingModel modellingModel;

    public TimeDiagramPanel(ModellingModel modellingModel) {
        this.modellingModel = modellingModel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        setBackground(modellingModel.getBackgroundColor());
        FontRenderContext context = g2.getFontRenderContext();
        g2.setFont(modellingModel.getFont());
        ArrayList<String> names = modellingModel.getNames();
        int maxWidth = 0;
        int maxHeight = 0;
        Rectangle2D bounds;
        for (String name : names) {
            bounds = getFont().getStringBounds(name, context);
            if (bounds.getWidth() > maxWidth) {
                maxWidth = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > maxHeight) {
                maxHeight = (int) bounds.getHeight();
            }
        }
        bounds = getFont().getStringBounds(ModellingModel.TIME_STRING, context);
        if (bounds.getWidth() > maxWidth) {
            maxWidth = (int) bounds.getWidth();
        }
        modellingModel.setDiagramSize(new Dimension(maxWidth + ModellingModel.INTERVAL_WIDTH * modellingModel.getT() + ModellingModel.DIAGRAM_BORDER * 3,
                maxHeight * (names.size() + 1) + ModellingModel.DIAGRAM_Y_INTERVAL * names.size() + ModellingModel.DIAGRAM_BORDER * 2));
        setSize(modellingModel.getDiagramSize());
        g2.setColor(modellingModel.getTextColor());
        int y = ModellingModel.DIAGRAM_BORDER;
        for (int i = 0; i < names.size(); i++) {
            LineMetrics metrics = modellingModel.getFont().getLineMetrics(names.get(i), context);
            g2.drawString(names.get(i), ModellingModel.DIAGRAM_BORDER, (int) (y + metrics.getAscent()));
            y += maxHeight + ModellingModel.DIAGRAM_Y_INTERVAL;
        }
        if (modellingModel.getT() > 0) {
            ArrayList<HashMap<Integer, Character>> values = modellingModel.getDiagrams();
            y = ModellingModel.DIAGRAM_BORDER;
            g2.setColor(modellingModel.getStandartColor());
            for (int i = 0; i < values.size(); i++) {
                int x = 2 * ModellingModel.DIAGRAM_BORDER + maxWidth;
                for (int t = 0; t < modellingModel.getT(); t++) {
                    if (values.get(i).get(t).compareTo('1') == 0) {
                        g2.fillRect(x, y, ModellingModel.INTERVAL_WIDTH, maxHeight);
                    } else {
                        g2.drawLine(x, y + maxHeight, x + ModellingModel.INTERVAL_WIDTH, y + maxHeight);
                    }
                    x += ModellingModel.INTERVAL_WIDTH;
                }
                y += maxHeight + ModellingModel.DIAGRAM_Y_INTERVAL;
            }
        }
        g2.setColor(modellingModel.getTextColor());
        int x = ModellingModel.DIAGRAM_BORDER;
        LineMetrics metrics = modellingModel.getFont().getLineMetrics(ModellingModel.TIME_STRING, context);
        g2.drawString(ModellingModel.TIME_STRING, x, y + metrics.getAscent());
        x += ModellingModel.DIAGRAM_BORDER + maxWidth;
        for (int t = 0; t < modellingModel.getT(); t++) {
            String tString = String.valueOf(t);
            bounds = modellingModel.getFont().getStringBounds(tString, context);
            metrics = modellingModel.getFont().getLineMetrics(tString, context);
            g2.drawString(tString, (int) (x - bounds.getWidth() / 2), (int) (y + metrics.getAscent()));
            x += ModellingModel.INTERVAL_WIDTH;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return modellingModel.getDiagramSize();
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 1;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

}
