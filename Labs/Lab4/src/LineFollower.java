// EGN 360 Lab 4 -- robot navigation using visual servoing
//
// Dr. Sukthankar -- October 6, 2008


import RobotClient.CreateClient;
import RobotClient.RobotClient;
import RobotClient.ImageGrabber;
import java.io.*;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import edu.cmu.ri.mrpl.TeRK.client.components.services.QwerkController;


public class LineFollower
{
    private static String ipAddress = "10.0.0.10";	// XXX YOUR ROBOT HERE

    private static CreateClient	myRobot;
    private static QwerkController qwerkController;
    private static ImageGrabber grabber;

    private static void init() {
    	String applicationName = "LineFollower";
    	myRobot = new CreateClient(applicationName, ipAddress);
        myRobot.waitForPlay();
    	myRobot.initialize();
    	qwerkController = myRobot.getQwerkController();
    	grabber = new ImageGrabber(qwerkController);
    }

    /*
     * Captures an image from TeRK camera and converts it to grayscale
     */
    public static short[][] getGrayImage() {
		Color[][] colorImage = grabber.colorArray();
		short[][] grayimage = new short[320][240];
		for (int x=0; x<320; x++) {
		    for (int y=0; y<240; y++) {
			grayimage[x][y] = (short)(
			    ( colorImage[x][y].getRed() +
			      colorImage[x][y].getGreen() +
			      colorImage[x][y].getBlue() ) / 3.0);
		    }
		}
		return grayimage;
    }
    
    public static short[][] getRedImage() {
		Color[][] colorImage = grabber.colorArray();
		short[][] redimage = new short[320][240];
		for (int x=0; x<320; x++) {
		    for (int y=0; y<240; y++) {
			redimage[x][y] = (short)(colorImage[x][y].getRed());
		    }
		}
		return redimage;
    }
    
    public static short[][] getBlueImage() {
		Color[][] colorImage = grabber.colorArray();
		short[][] blueimage = new short[320][240];
		for (int x=0; x<320; x++) {
		    for (int y=0; y<240; y++) {
			blueimage[x][y] = (short)(colorImage[x][y].getBlue());
		    }
		}
		return blueimage;
    }
    
    public static short[][] getGreenImage() {
		Color[][] colorImage = grabber.colorArray();
		short[][] greenimage = new short[320][240];
		for (int x=0; x<320; x++) {
		    for (int y=0; y<240; y++) {
			greenimage[x][y] = (short)(colorImage[x][y].getGreen());
		    }
		}
		return greenimage;
    }








    public static void main(String[] args) 
    {
		init();
	
		boolean stopRequested = false;
		while (!stopRequested) {
		    short[][] img = getGrayImage();
	
			System.out.println(img[100][100]);
	
		    /*
		     * Insert image processing and control code here
		     */
	
	
		    // sleep 500ms between iterations
		    stopRequested = myRobot.sleepUnlessStop(500);
		}
    }
}
