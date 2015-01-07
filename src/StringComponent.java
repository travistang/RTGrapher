
import java.awt.Dimension;
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
public class StringComponent extends JComponent{
    StringComponent(Coordinate c){
        string=new String();
        this.c=c;
        setVisible(true);
    }
    StringComponent(String s, Coordinate c){
        string =s;
        this.c=c;
        setVisible(true);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawString(string,(int)c.x,(int)c.y);
    }
    public String string;
    public Coordinate c;
}
