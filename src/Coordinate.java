/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Travis
 */
import java.awt.Color;
import java.awt.Point;
public class Coordinate extends Point{
    public Coordinate(double x, double y){
        this.x=x;
        this.y=y;
        this.color=Color.BLACK;
    }
    public Coordinate(Coordinate c){
        this.x=c.x;
        this.y=c.y;
        this.color=c.color;
    }
    Coordinate(double x, double y,Color color){
        this.x=x;
        this.y=y;
        this.color=color;
    }
   public double x,y;
   public Color color;
}
