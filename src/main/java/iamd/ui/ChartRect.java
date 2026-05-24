package iamd.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ChartRect implements ChartElement 
{
    private String text1, text2; 
    
    private double x, y, width, height;

    private Color fillingColor = Color.white, 
                  borderColor  = Color.black, 
                  textColor    = Color.black;

    private Font font1 = null, 
                 font2 = null;
    
    private Rectangle2D.Double fillingShape;
    
    public ChartRect(double x, double y, double width, double height, String text1, String text2)
    {
        this.text1 = text1;
        this.text2 = text2;
        
        this.setRect(x, y, width, height);
    }

    public void setRect(double x, double y, double width, double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.fillingShape = new Rectangle2D.Double(x, y, width, height);
    }

    public void setFont1(Font font1)
    {
        this.font1 = font1;
    }
    
    public void setFont2(Font font2)
    {
        this.font2 = font2;
    }
    
    public void setFillingColor(Color color)
    {
        this.fillingColor = color;
    }
    
    public void setBorderColor(Color color)
    {
        this.borderColor = color;
    }
    
    public void setTextColor(Color color)
    {
        this.textColor = color;
    }
    
    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getWidth()
    {
        return this.width;
    }

    public double getHeight()
    {
        return this.height;
    }
    
    @Override
    public void paint(Graphics2D g2, AffineTransform tx2)
    {
        g2.setColor(this.fillingColor);
        g2.fill(tx2.createTransformedShape(this.fillingShape));

        g2.setColor(this.borderColor);
        g2.draw(tx2.createTransformedShape(this.fillingShape));

        g2.setColor(this.textColor);

        
        Point2D text1Pos = tx2.transform(new Point2D.Double(this.x + this.width / 2, this.y + this.height / 3), null);
        g2.setFont(this.font1);
        FontMetrics fm1 = g2.getFontMetrics(this.font1);
        g2.drawString(text1, (float) text1Pos.getX() - fm1.stringWidth(text1) / 2, (float) text1Pos.getY());

        Point2D text2Pos = tx2.transform(new Point2D.Double(this.x + this.width / 2, this.y + 2 * this.height / 3), null);
        g2.setFont(this.font2);
        FontMetrics fm2 = g2.getFontMetrics(this.font2);
        g2.drawString(text2, (float) text2Pos.getX() - fm2.stringWidth(text2) / 2, (float) text2Pos.getY());
    }

    @Override
    public boolean containsPoint(Point2D point)
    {
        return this.fillingShape.contains(point);
    }

    @Override
    public double getMaxRadius()
    {
        return Math.sqrt(this.width * this.width + this.height * this.height) / 2;
    }

    @Override
    public Rectangle2D getBounds2D()
    {
        return this.fillingShape;
    }
}
