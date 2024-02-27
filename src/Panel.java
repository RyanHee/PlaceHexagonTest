import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class Panel extends JPanel implements ActionListener {
    private int[]xlst;
    private int[]ylst;
    private int[]xPoints, yPoints;
    private int angle;
    private BufferedImage img, img1;
    private node test;
    //private HexButton hexButton;
    public Panel(){
        try{
            img = ImageIO.read(Panel.class.getResource("tile.png"));
            img1 = ImageIO.read(Panel.class.getResource("tile1.png"));
        }
        catch (Exception e){
            System.out.println(1231);
        }
        angle=0;
        test=new node("");

        //hexButton.setBounds(200*getWidth()/1600, 100*getHeight()/900, 50, 50);
        //hexButton.setVisible(true);

    }


    public void paint(Graphics g){
        super.paint(g);
        xPoints=new int[6];
        yPoints=new int[6];
        int x = 300;
        int y = 300;
        int w = 58;
        int h = 58;
        System.out.println("w: "+w+", h: "+h);
        for(int i = 0; i < 6; i++) {
            double v = i*Math.PI/3;
            //use this for ^
            xPoints[i] = x+w/2+(int)Math.round(-w/2*Math.cos(v + Math.PI/2));
            yPoints[i] = y+w/2+(int)Math.round(-w/2*Math.sin(v + Math.PI/2));
            //use this for ------
            //xPoints[i] = x + (int)Math.round(-width*Math.sin(v + Math.PI/2));
            //yPoints[i] = y + (int)Math.round(-height*Math.cos(v + Math.PI/2));
        }
        w=50;
        System.out.println(Arrays.toString(xPoints));
        System.out.println(Arrays.toString(yPoints));
        g.fillPolygon(new Polygon(xPoints, yPoints, 6));
        //右下
        g.drawImage(img1, xPoints[0], yPoints[2], w,h,null);
        //右上
        g.drawImage(img1, xPoints[0], yPoints[1]-h, w,h,null);
        //右
        g.drawImage(img1, xPoints[1], yPoints[0], w,h,null);
        //左下
        g.drawImage(img1, xPoints[0]-w, yPoints[2], w,h,null);
        //左上
        g.drawImage(img1, xPoints[0]-w, yPoints[1]-h, w,h,null);
        //左
        g.drawImage(img1, xPoints[5]-w, yPoints[0], w,h,null);

        test.setBounds(800*getWidth()/1600, 450*getHeight()/900, w, w);
        test.paintComponent(g);



    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}