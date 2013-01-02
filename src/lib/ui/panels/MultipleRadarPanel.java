package lib.ui.panels;

import lib.types.PAD;
import lib.types.PADState;
import lib.types.Palette;
import lib.ui.PanelUpdater;
import lib.ui.panels.base.MultiplePanel;
import lib.ui.panels.base.SinglePanel;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

public class MultipleRadarPanel extends MultiplePanel {

    protected float currentArcStart = 90f;
    protected int arcLen = 360;
    protected float maxArcStroke = 10f;

    public MultipleRadarPanel(int width, int height) {
        super(width, height);

        PanelUpdater.handle(this);
    }

    protected int getRadius() {
        return (getW() < getH()) ? getW() / 2 : getH() /2;
    }

    protected void drawArc(int arcStart, int arcLen, Graphics2D g2d, Color color, float stroke) {
        Arc2D arc = new Arc2D.Double(getCenterX() - getRadius(), getCenterY() - getRadius(),
                                     2f * getRadius(), 2f * getRadius(),
                                     arcStart, arcLen, Arc2D.PIE);

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(stroke));

        g2d.draw(arc);
    }

    protected void drawCircle(Graphics2D g2d, Color color) {
        Ellipse2D circle = new Ellipse2D.Double(getCenterX() - getRadius(), getCenterY() - getRadius(),
                                                2f * getRadius(), 2f * getRadius());

        g2d.setColor(color);
        g2d.fill(circle);

        g2d.draw(circle);
    }

    @Override
    public void customPaintComponent(Graphics2D g2d) {
        int len = values.size();
        if (0 == len) {
            return;
        }

        PADState current = values.get(len - 1);

        float value = current.getP().getValue();
        float certainty = current.getP().getCertainty();

        drawCircle(g2d, Palette.getTransparent((value > 0) ? Palette.green : Palette.red, certainty));

        float arcSpeed = -1 * (current.getA().getValue() + 1f);
        currentArcStart = (currentArcStart + arcSpeed) % 360;

        float arcStroke = (current.getD().getValue() + 1f) * maxArcStroke / 2f;

        drawArc((int)currentArcStart, arcLen, g2d, Palette.grey, arcStroke);

        // Draw the value
        g2d.setColor(Palette.black);
        //String s = String.format("%s: %.2f (%.2f)", this.label, value, certainty);
        String s = String.format("%.2f, %.2f, %.2f", current.getP().getValue(), current.getA().getValue(), current.getD().getValue());

        int sLen = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        g2d.drawString(s, getCenterX() - sLen / 2, getCenterY());
    }
}
