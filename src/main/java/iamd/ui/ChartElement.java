package iamd.ui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface ChartElement {
    public void paint(Graphics2D g2, AffineTransform tx2);

    public boolean containsPoint(Point2D point);

    public double getMaxRadius();

    public Rectangle2D getBounds2D();
}
