import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class node extends JButton {

    private Polygon hexagon;
    private String val;
    private int x, y, width, height;
    private node[] neighbors;
    private int[]xPoints, yPoints;
    private boolean isPlaced;
    private BufferedImage img, outline;
    public node(String label){
        super(label);
        xPoints = new int[6];
        yPoints = new int[6];
        System.out.println(Arrays.toString(xPoints));
        System.out.println(Arrays.toString(yPoints));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        hexagon = new Polygon(xPoints, yPoints, 6);
        try{
            img = ImageIO.read(Panel.class.getResource("tile.png"));
        }
        catch (Exception e){
            System.out.println("fuck");
        }
    }

    public node(String label, String s){
        super(label);
        xPoints = new int[6];
        yPoints = new int[6];
        val=s;
        isPlaced=true;
    }


    public void setNeighbor(node n, int idx){
        if (neighbors[idx]==null){
            System.out.println("here");
            neighbors[idx]=n;
            if (idx<3){
                n.setNeighbor(this, idx+3);
            }
            else{
                n.setNeighbor(this, idx-3);
            }
        }
        else if (!neighbors[idx].getPlaced()){
            neighbors[idx]=n;
            System.out.println("tf");
            if (this.getPlaced()){
                if (idx<3){
                    n.setNeighbor(this, idx+3);
                }
                else{
                    n.setNeighbor(this, idx-3);
                }
            }

        }
        else{
            System.out.println("this place is placed!!!");
        }
    }
    public void setVal(String v){
        val=v;
        isPlaced=true;
    }

    public void placed(){
        isPlaced=true;
        try{
            img = ImageIO.read(Panel.class.getResource("tile.png"));
        }
        catch (Exception e){
            System.out.println("fuck");
        }
    }
    public void drawImg(Graphics g,int xx, int yy, int w, int h){
        System.out.println();
        System.out.println("w: "+w+", h: "+h);
        g.drawImage(img, xx, yy, w, h,null);
        //g.drawImage(outline, xx, yy, w, h, null);
        int[]nx=new int[6];
        int[]ny=new int[6];
        nx[0]=xPoints[0];
        nx[1]=xPoints[1]+w/15;
        nx[2]=xPoints[0];
        nx[3]=xPoints[0]-w;
        nx[4]=xPoints[5]-w-w/15;
        nx[5]=xPoints[0]-w;
        ny[0]=yPoints[1]-h;
        ny[1]=yPoints[0];
        ny[2]=yPoints[2+h/35];
        ny[3]=yPoints[2]+h/35;
        ny[4]=yPoints[0];
        ny[5]=yPoints[1]-h;
        for (int i=0;i<6;i++){
            node n = neighbors[i];
            //System.out.println(i);
            if (n==null){
                //System.out.println("bitch");
            }
            else{
                //System.out.println("wtf");
                //System.out.println(n);
                if (!n.getPlaced()){
                    n.drawImg(g, nx[i],ny[i],w,h);
                }

            }
        }
    }
    public boolean getPlaced(){
        return isPlaced;
    }
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK);
        int x0 = getSize().width/2;
        int y0 = getSize().height/2;
        for(int i=0; i<6; i++) {
            double v = i*Math.PI/3;
            xPoints[i] = x0 + (int)Math.round((getWidth()/2)*Math.sin(v));
            yPoints[i] = y0 + (int)Math.round((getHeight()/2)*Math.cos(v));
        }
        g.drawPolygon(xPoints, yPoints,6);
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        int x0 = getSize().width/2;
        int y0 = getSize().height/2;
        for(int i=0; i<6; i++) {
            double v = i*Math.PI/3;
            xPoints[i] = x0 + (int)Math.round((getWidth()/2)*Math.sin(v));
            yPoints[i] = y0 + (int)Math.round((getHeight()/2)*Math.cos(v));
        }
        System.out.println(Arrays.toString(xPoints));
        System.out.println(Arrays.toString(yPoints));
        g.fillPolygon(xPoints, yPoints, 6);
        super.paintComponent(g);
    }



    @Override
    public boolean contains(int x1, int y1) {
        if (hexagon == null ||
                !hexagon.getBounds().equals(getBounds())) {
            int x0 = getSize().width/2;
            int y0 = getSize().height/2;
            for(int i=0; i<6; i++) {
                double v = i*Math.PI/3;
                xPoints[i] = x0 + (int)Math.round((getWidth()/2)*Math.cos(v));
                yPoints[i] = y0 + (int)Math.round((getHeight()/2)*Math.sin(v));
            }
            hexagon = new Polygon(xPoints,yPoints,6);
        }
        return hexagon.contains(x1, y1);
    }

}