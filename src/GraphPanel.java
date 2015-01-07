/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Travis
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JTextArea;
public class GraphPanel extends JPanel{
    public GraphPanel(){
        graph=new Graph();
        this.setOpaque(true);
        this.setBackground(Color.white);
        
        showOriginTag=true;
        showXAxisTag=true;
        showYAxisTag=true;
        xAxisName="X";
        yAxisName="Y";
    };
    //flags for toggling name tags
    boolean showOriginTag;
    boolean showXAxisTag;
    boolean showYAxisTag;
    
    //name tags
    String xAxisName,
                yAxisName;
    public Graph graph;
    public void showDefaultView(){
        graph.setMinX(-5);
        graph.setMaxX(5);
        graph.setMinY(-5);
        graph.setMaxY(5);
        repaint();
    };
    public void centerOrigin(){
        float dx=graph.getMaxX()-graph.getMinX();
        float dy=graph.getMaxY()-graph.getMinY();
        graph.setMinX(-dx/2);
        graph.setMaxX(dx/2);
        graph.setMinY(-dy/2);
        graph.setMaxY(dy/2);
        repaint();
    };
    
    public void addPoint(Coordinate c){
        graph.pointVector.add(c);
        //if(graph.pointList!=null)graph.pointList.put(graph.getPointCount(),c);
        //graph.setPointCount(graph.pointList.size());
        repaint();
    };
    @Override
    public void paintComponent(Graphics g){
                    super.paintComponent(g);
                    float minX=graph.getMinX();
                    float maxX=graph.getMaxX();
                    float minY=graph.getMinY();
                    float maxY=graph.getMaxY();
                    int width=getWidth();
                    int height=getHeight();
                    int xUnit=(int)(width/(maxX-minX));
                    int yUnit=(int)(height/(maxY-minY));
                    
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.BLACK );
	//g.setStroke(new BasicStroke(2));
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Font font=new Font("Apple Gothic", Font.PLAIN,14);
                    g2.setFont(font);
                    int x0=0,y0=0;
                    if(minX<0&&maxX>0){
                        x0=(int)graph.abs(minX)*xUnit;
                        g2.drawLine(x0,0,x0,height);//y axis
                    }
                    if(minY<0&&maxY>0){
                        y0=(int)(height-(graph.abs(minY)*yUnit));
                        g2.drawLine(0,y0,width,y0);//x axis
                    }
                    if(graph.isInView(new Coordinate(x0,y0))&&showOriginTag){
                        g2.setColor(Color.BLACK);
                        g2.drawString("O", x0, y0);
                    }
                    g2.setStroke(new BasicStroke(5));
                    //Vector<Coordinate> pointList=graph.getPointsCoordinates(getWidth(),getHeight());
                    //Enumeration<Coordinate> pointListenum=pointList.elements();
                    Iterator<Coordinate> it=graph.pointVector.iterator();
                    while(it.hasNext()){
                        Coordinate c=it.next();
                        //Coordinate c=it.next();
                            if(graph.isInView(c)){
                                    c=graph.getJustifiedCoordinate(width, height, 
                                                      c);
                                g2.setColor(c.color);
                                g2.fillOval((int)c.x-5, (int)c.y-5, 10, 10);
                            }
                            //PointComponent p=new PointComponent(c,20,20);
                            //add(p,new Point((int)p.coordinate.x,(int)p.coordinate.y));
                    }
                    
                    g2.dispose();
                }
}
