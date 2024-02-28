import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Panel extends JPanel implements ActionListener {
    //private int[]xlst;
    //private int[]ylst;
    private HashMap<Integer, Boolean>map;
    private ArrayList<node>visted;
    private int[]xPoints, yPoints;
    private int angle;
    private static BufferedImage img, img1, outline;
    private node test, n1;
    //private HexButton hexButton;
    public Panel(){
        try{
            img = ImageIO.read(Panel.class.getResource("tile.png"));
            img1 = ImageIO.read(Panel.class.getResource("tile1.png"));
            outline=ImageIO.read(Panel.class.getResource("tileOutline.png"));
        }
        catch (Exception e){
            System.out.println(1231);
        }
        angle=0;
        test=new node("", "test");
        n1 = new node("");
        n1.addActionListener(this);
        //hexButton.setBounds(200*getWidth()/1600, 100*getHeight()/900, 50, 50);
        //hexButton.setVisible(true);
        map=new HashMap<>();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int w=58;
        int h=58;
        int i=1;
        visted=new ArrayList<>();
        putButtons(g, test,800, 450, w, h, i );

    }
    public void paint(Graphics g){
        super.paint(g);
        //super.paintComponent(g);
        xPoints=new int[6];
        yPoints=new int[6];
        int x = 300;
        int y = 300;
        int w = 58;
        int h = 58;
        //System.out.println("w: "+w+", h: "+h);
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
        //System.out.println(Arrays.toString(xPoints));
        //System.out.println(Arrays.toString(yPoints));



        //g.fillPolygon(new Polygon(xPoints, yPoints, 6));
        g.drawImage(img, x+w*4/50, y, w, h,null);
        add(n1);
        n1.setBounds(x, y, 58, 58);

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



        w=58;
        //test.setBounds(800*getWidth()/1600, 450*getHeight()/900, w, h);
        //System.out.println(test.getX()+" "+test.getY());
        //test.paintComponent(g);


    }

    private void putButtons(Graphics g, node n, int x, int y, int w, int h, int id){
        int temp;
        if (n==null){
            return;
        }
        if (visted.contains(n)){
            return;
        }
        //System.out.println("here? "+n);
        int[]xlst=new int[6];
        int[]ylst=new int[6];
        for(int i = 0; i < 6; i++) {
            double v = i*Math.PI/3;
            //use this for ^
            xlst[i] = x+w/2+(int)Math.round(-w/2*Math.cos(v + Math.PI/2));
            ylst[i] = y+h/2+(int)Math.round(-h/2*Math.sin(v + Math.PI/2));
            //use this for ------
            //xPoints[i] = x + (int)Math.round(-width*Math.sin(v + Math.PI/2));
            //yPoints[i] = y + (int)Math.round(-height*Math.cos(v + Math.PI/2));
        }
        //g.fillPolygon(xlst,ylst,6);
        //id=temp;
        int er=w*50*4/58/58;

        g.drawImage(n.getImg(), x+er, y, w*50/58, h,null);
        g.drawImage(outline, x+er, y, w*50/58, h, null);
        n.addActionListener(this);
        add(n);
        n.setBounds(x,y,w,h);
        visted.add(n);
        id++;
        er=w*50*6/58/58;
        int[]nx=new int[6];
        int[]ny=new int[6];
        nx[0]=xlst[0]-er;
        nx[1]=xlst[1]-er;
        nx[2]=xlst[0]-er;
        nx[3]=xlst[0]-w+er;
        nx[4]=xlst[5]-w+er;
        nx[5]=xlst[0]-w+er;
        ny[0]=ylst[1]-h;
        ny[1]=ylst[0];
        ny[2]=ylst[2];
        ny[3]=ylst[2];
        ny[4]=ylst[0];
        ny[5]=ylst[1]-h;
        //System.out.println(Arrays.toString(nx));
        //System.out.println(Arrays.toString(ny));
        for (int i=0;i<6;i++){
            putButtons(g, n.getNeighbors()[i], nx[i], ny[i], w, h, id);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(1);
        if (e.getSource().equals(n1)){
            System.out.println("how tf does this work");
        }
        else{
            node n=(node) e.getSource();
            if (n!=null&&!n.getPlaced()){
                //System.out.println("plz work");
                System.out.println(n);
                node[]neighbors = n.getNeighbors();
                for (int i=0;i<6;i++){
                    node neighbor = neighbors[i];
                    if (neighbor!=null && neighbor.getPlaced()){
                        n.setVal("sheeeesh");
                        System.out.println(n.getVal());
                        int num;
                        if (i<3){
                            num=i+3;
                        }
                        else{
                            num=i-3;
                        }
                        neighbor.setNeighbor(n, num);
                    }
                }
            }
        }
        repaint();
    }
}