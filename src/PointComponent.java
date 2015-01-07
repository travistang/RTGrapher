
import java.awt.Graphics;
import javax.swing.JComponent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Travis
 */
public class PointComponent extends JComponent{
    PointComponent(Coordinate c,int width,int height){
        coordinate=c;
        this.width=width;
        this.height=height;
        setVisible(true);
        revalidate();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(coordinate.color);
        g.fillOval((int)coordinate.x, (int)coordinate.y, width, height);
    }
    public Coordinate coordinate;
    public int width,height;
}
