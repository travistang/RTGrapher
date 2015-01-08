/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Travis
 */

import java.nio.ByteBuffer;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
public class BTManager {
    BTManager(String targetPortName,Grapher g){
        this.targetPortName=targetPortName;
        serialPort=new SerialPort(targetPortName);
        baudRate=115200;
        this.g=g;
        serialPortReader=new SerialPortReader();
        
    }
    public SerialPort serialPort;
    private final SerialPortReader serialPortReader;
    public int baudRate;
    public String targetPortName;
    private byte[] buffer;
    private final Grapher g;
    
    public static enum byteHandlingMethod{
        X_DATA_Y_TIME,
        X_DATA_Y_DATA,
        X_TIME_Y_DATA
    }
    public void connect(){
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(baudRate, 8, 1, 0);//Set params.
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask
            serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
        }
        catch (SerialPortException ex) {
            Grapher.printLog(g,ex.getMessage());
        }
    }
    public void processData(BTManager.byteHandlingMethod method){
        switch(method){
            case X_TIME_Y_DATA://4 bytes will be used to represent each data. First will be the integer
                ByteBuffer bb=ByteBuffer.wrap(buffer);
                    float value=bb.getFloat();
                    g.addPoint(new Coordinate(System.nanoTime()-g.startTime,value));
                    break;
            case X_DATA_Y_DATA:
                    byte[] tempBuf1,tempBuf2;
                    tempBuf1=new byte[4];
                    tempBuf2=new byte[4];
                    System.arraycopy(buffer, 0, tempBuf1, 0, 4);
                    System.arraycopy(buffer, 4, tempBuf2, 0, 4);
                    bb=ByteBuffer.wrap(tempBuf1);
                    value=bb.getFloat();
                    bb=ByteBuffer.wrap(tempBuf2);
                    float secondValue=bb.getFloat();
                    g.addPoint(new Coordinate(value,secondValue));
                    break;
            case X_DATA_Y_TIME:
                    bb=ByteBuffer.wrap(buffer);
                    value=bb.getFloat();
                    g.addPoint(new Coordinate(value,System.nanoTime()-g.startTime));
                    break;
        }
    }
    class SerialPortReader implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR()){//If data is available
                if(event.getEventValue() == 8){//Check bytes count in the input buffer
                    //Read data, if 8 bytes available 
                    
                        try {
                            buffer= serialPort.readBytes(8);
                        }
                    
                        catch (SerialPortException ex) {
                           Grapher.printLog(g,ex.getMessage());
                        }
                }
                if(event.getEventValue() == 4){//Check bytes count in the input buffer
                    //Read data, if 4 bytes available 
                    
                        try {
                            buffer= serialPort.readBytes(4);
                            
                        }
                        catch (SerialPortException ex) {
                             Grapher.printLog(g,ex.getMessage());
                        }
                }
            }
            else if(event.isCTS()){//If CTS line has changed state
                if(event.getEventValue() == 1){//If line is ON
                    System.out.println("CTS - ON");
                }
                else {
                    System.out.println("CTS - OFF");
                }
            }
            else if(event.isDSR()){///If DSR line has changed state
                if(event.getEventValue() == 1){//If line is ON
                    System.out.println("DSR - ON");
                }
                else {
                    System.out.println("DSR - OFF");
                }
            }
        }
    }
    
}
