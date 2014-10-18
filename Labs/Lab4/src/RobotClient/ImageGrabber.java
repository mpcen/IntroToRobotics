// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageGrabber.java
package RobotClient;

import edu.cmu.ri.mrpl.TeRK.Image;
import edu.cmu.ri.mrpl.TeRK.VideoException;
import edu.cmu.ri.mrpl.TeRK.client.components.services.QwerkController;
import edu.cmu.ri.mrpl.TeRK.client.components.services.VideoStreamService;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;

public class ImageGrabber {

    public VideoStreamService videoStreamService; // rkk private

    public static final int IMAGE_WIDTH = 320;
    public static final int IMAGE_HEIGHT = 240;
    private Color grid[][];
    public Image IMG;

//    public int[] getPixels(){
//        java.awt.Image img = importImage();
//        int x=0; int y=0; int w=320; int h=240;
//        int pixels[] = new int[w * h];
//        for (int z = 0; z < pixels.length; z++) {
//            pixels[z] = 0;
//        }
//        PixelGrabber pg = new PixelGrabber(img, x, y, w, h, pixels, 0, w);
//        try {
//            pg.grabPixels();
//        } catch (InterruptedException e) {
//            System.err.println("interrupted waiting for pixels!");
//            return null;
//        }
//        return pixels;
//    }
    public ImageGrabber(QwerkController qwerkController) {
        videoStreamService = qwerkController.getVideoStreamService();
        grid = new Color[IMAGE_WIDTH][IMAGE_HEIGHT];
        if(videoStreamService == null)
            System.out.println("Video Stream Service is NULL");
    }

    public Image importImage() // rkk private
    {
        try {
            videoStreamService.startCamera();
            Image img = videoStreamService.getFrame(0);
            return img;
        } catch (VideoException e) {
            System.out.println((new StringBuilder()).append("VideoException").append(e).toString());
        }
        return null;
    }

    public java.awt.Image getImage() {
        return Toolkit.getDefaultToolkit().createImage(importImage().data);
    }

    public Color[][] colorArray() {
        handlePixels(importImage(), 0, 0, 320, 240);
        return grid;
    }

    public static Color[][] colorArray(java.awt.Image image) {
        return handlePixels(image, 0, 0, 320, 240);
    }

    public int[][][] intArray() {
        return convertRGBtoIntArray(colorArray());
    }

    public Color colorPixel(int x, int y) {
        colorArray();
        return grid[x][y];
    }

    public int[] intPixel(int x, int y) {
        colorArray();
        int array[] = {
            grid[x][y].getRed(), grid[x][y].getGreen(), grid[x][y].getBlue(), grid[x][y].getAlpha()
        };
        return array;
    }

    private int[][][] convertRGBtoIntArray(Color grid[][]) {
        int intArray[][][] = new int[grid.length][grid[0].length][4];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                intArray[i][j][0] = grid[i][j].getRed();
                intArray[i][j][1] = grid[i][j].getGreen();
                intArray[i][j][2] = grid[i][j].getBlue();
                intArray[i][j][3] = grid[i][j].getAlpha();
            }

        }

        return intArray;
    }

    private void handlePixels(Image img, int x, int y, int w, int h) {
        grid = handlePixels(Toolkit.getDefaultToolkit().createImage(img.data), x, y, w, h);
    }

    private static Color[][] handlePixels(java.awt.Image img, int x, int y, int w, int h) {
        int pixels[] = new int[w * h];
        for (int z = 0; z < pixels.length; z++) {
            pixels[z] = 0;
        }
        PixelGrabber pg = new PixelGrabber(img, x, y, w, h, pixels, 0, w);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            System.err.println("interrupted waiting for pixels!");
            return (Color[][]) null;
        }
        if ((pg.getStatus() & 0x80) != 0) {
            System.err.println("image fetch aborted or errored");
            return (Color[][]) null;
        }
        Color grid[][] = new Color[320][240];
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                grid[i][j] = handleSinglePixel(x + i, y + j, pixels[j * w + i]);
            }
        }

        return grid;
    }

    private static Color handleSinglePixel(int x, int y, int pixel) {
        int alpha = pixel >> 24 & 0xff;
        int red = pixel >> 16 & 0xff;
        int green = pixel >> 8 & 0xff;
        int blue = pixel & 0xff;
        return new Color(red, green, blue, alpha);
    }
}
