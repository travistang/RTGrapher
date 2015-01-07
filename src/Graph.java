

import java.util.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Travis
 */
public class Graph{
    public Graph(){
        pointList=new HashMap();
        pointVector=new Vector<>();
    };

    
    public void setMinX(float min){
        if(maxX>min)minX=min;
    };
    public void setMaxX(float max){
        if(minX<max)maxX=max;
    };
    public void setMinY(float min){
        if(maxY>min)minY=min;
    };
    public void setMaxY(float max){
        if(minY<max)maxY=max;
    };
    public float getMinX(){
        return minX;
    };
    public float getMaxX(){
        return maxX;
    };
    public float getMinY(){
        return minY;
    };
    public float getMaxY(){
        return maxY;
    };
    public int getPointCount(){
        return pointCount;
    }
    public void setPointCount(int count){
        pointCount=count;
    }
    private float minX,maxX,minY,maxY;
    private int pointCount;
    public HashMap pointList;
    public Vector<Coordinate> pointVector;
    public double abs(double d){
        if(d<0)return -d;
        return d;
    }
    
    public  Coordinate getJustifiedCoordinate(int width,int height,Coordinate c){
        int xUnit=(int)(width/(maxX-minX));
        int yUnit=(int)(height/(maxY-minY));
        if(isInView(c)){
            int newX=(int)(c.x-minX)*xUnit;
            int newY=(int)(maxY-c.y)*yUnit;
            return new Coordinate(newX,newY,c.color);
        }
        return null;
    }
    public boolean isInView(Coordinate c){
        if((c.x<=maxX&&c.x>=minX)
            &&(c.y<=maxY&&c.y>=minY)){
            return true;
        }
            return false;
    }
    public Vector<Coordinate> getPointsCoordinates(int width,int height){
            Vector<Coordinate> returnPointList=new Vector<>();
            if(!returnPointList.isEmpty()){
            for(int i=0;i<getPointCount();i++){
                        Coordinate p=getJustifiedCoordinate(width,height, (Coordinate) pointList.get(i));
                        if(p!=null){
                            returnPointList.add(p);
                        }
    }}
            
            return returnPointList;
    }
    
}
