import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class node extends JButton {

    private String val;
    private BufferedImage img, outline;
    private boolean isPlaced;
    private Polygon hexagon;
    private int[]xPoints, yPoints;
    private int x, y, width, height;
    private node[]neighbors;

    public node(String label){
        super(label);
        val = "";
        try{
            img = ImageIO.read(Panel.class.getResource("tile1.png"));
            outline = ImageIO.read(Panel.class.getResource("tileOutline.png"));
        }
        catch (Exception e){
            System.out.println("fuck");
        }
        xPoints=new int[6];
        yPoints=new int[6];
        hexagon = new Polygon(xPoints, yPoints, 6);
        isPlaced=false;
        neighbors=new node[6];
    }

    public node(String label, String v){
        super(label);
        val = v;
        try{
            img = ImageIO.read(Panel.class.getResource("tile.png"));
            outline = ImageIO.read(Panel.class.getResource("tileOutline.png"));
        }
        catch (Exception e){
            System.out.println("fuck");
        }
        xPoints=new int[6];
        yPoints=new int[6];
        hexagon = new Polygon(xPoints, yPoints, 6);
        isPlaced=true;
        neighbors=new node[6];
        int idx=0;
        //0<->3
        //1<->4
        //2<->5

        for (node i:neighbors){
            node n=new node("");
            neighbors[idx]=n;
            if (idx>2){
                neighbors[idx].setNeighbor(this, idx-3);
            }
            else{
                neighbors[idx].setNeighbor(this, idx+3);
            }

            idx++;
        }
    }

    public boolean getPlaced(){
        return isPlaced;
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

    @Override
    public void setBounds(int xx, int yy, int w, int h){
        x=xx;
        y=yy;
        width=w;
        height = h;
        for(int i = 0; i < 6; i++) {
            double v = i*Math.PI/3;
            xPoints[i] = x +width/2+ (int)Math.round(-width/2*Math.cos(v + Math.PI/2));
            yPoints[i] = y +height/2+ (int)Math.round(-height/2*Math.sin(v + Math.PI/2));
        }
        hexagon = new Polygon(xPoints, yPoints, 6);
        //Graphics g = getGraphics();
        //System.out.println(g);
        //g.fillPolygon(hexagon);
        System.out.println(Arrays.toString(xPoints));
        System.out.println(Arrays.toString(yPoints));
    }

    public void drawImg(Graphics g,int xx, int yy, int w, int h){
        g.drawImage(img, xx, yy, w, h,null);
        g.drawImage(outline, xx, yy, w, h, null);
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
            System.out.println(i);
            if (n==null){
                System.out.println("bitch");
            }
            else{
                System.out.println("wtf");
                System.out.println(n);
                if (!n.getPlaced()){
                    n.drawImg(g, nx[i],ny[i],w,h);
                }

            }
        }
    }

    public String getVal(){
        return val;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawImg(g, x, y, width, height/100*116);
    }

    @Override
    public boolean contains(int x1, int y1) {
        if (hexagon==null){
            int[] xlst = new int[6];
            int[] ylst = new int[6];

            int x = getSize().width/2;
            int y = getSize().height/2;
            for (int i=0;i<6;i++){
                double v = Math.PI/3;
                xlst[i] = x + (int)Math.round((getWidth()/2)*Math.cos(v));
                ylst[i] = y + (int)Math.round((getHeight()/2)*Math.sin(v));
            }
            hexagon=new Polygon(xlst, ylst, 6);
        }
        return hexagon.contains(x1, y1);
    }

    public String toString(){
        return isPlaced+" "+val;
    }
}
