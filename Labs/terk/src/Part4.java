import RobotClient.CreateClient;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class Part2
{

    public static void main(String[] args)
    {

        // Instantiate the robot and robot GUI
        CreateClient myRobot = new CreateClient("EGN3060 Robot","10.0.0.10");
        int x;

        myRobot.waitForPlay();
        myRobot.initialize();

       while(myRobot.isPlaying())
       {
    	   x = myRobot.getTextFieldValueAsInt();
    	   if(x > 0)
    	   {
    		   squareSpiral(myRobot.getTextFieldValueAsInt(), myRobot);
    	   }
       }

    }

    public static void squareSpiral(int x, CreateClient i)
    {
    	int y = 100;
		for(;x>0;x--)
		{
			i.moveDistance(y);
			i.moveAngle(90);
			y += 100;
		}
    }
}

