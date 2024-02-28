import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class node extends JButton {

    private int id;
    private Polygon hexagon;
    private String val;
    private node[] neighbors;
    private int[]xPoints, yPoints;
    private boolean isPlaced;
    private BufferedImage img, outline;
    public node(String label){
        super(label);
        id=0;
        xPoints = new int[6];
        yPoints = new int[6];
        neighbors=new node[6];
        isPlaced=false;
        setOpaque(false);
        setContentAreaFilled(false);
        //setBorderPainted(false);
        hexagon = new Polygon(xPoints, yPoints, 6);
        try{
            img = ImageIO.read(node.class.getResource("tile1.png"));
        }
        catch (Exception e){
            System.out.println("fuck");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int i){
        id=i;
        id=0;
    }
    public node(String label, String s){
        super(label);
        xPoints = new int[6];
        yPoints = new int[6];
        neighbors=new node[6];
        val=s;
        System.out.println(Arrays.toString(xPoints));
        System.out.println(Arrays.toString(yPoints));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        hexagon = new Polygon(xPoints, yPoints, 6);
        try{
            img = ImageIO.read(node.class.getResource("tile.png"));
        }
        catch (Exception e){
            System.out.println("fuck");
        }
        isPlaced=true;
        for (int i=0;i<6;i++){
            node n = new node("");
            neighbors[i]=n;
            int num;
            if (i<3){
                num=i+3;
            }
            else{
                num=i-3;
            }
            n.setNeighbor(this, num);
        }
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

    public String getVal(){
        return val;
    }
    public void setVal(String v){
        val=v;
        isPlaced=true;
        try{
            img = ImageIO.read(node.class.getResource("tile.png"));
        }
        catch (Exception e){
            System.out.println("fuck");
        }
        for (int i=0;i<6;i++){
            if (neighbors[i]==null){
                neighbors[i]=new node("");
            }
            node neighbor = neighbors[i];
            int num;
            if (i<3){
                num=i+3;
            }
            else{
                num=i-3;
            }
            neighbor.setNeighbor(this, num);
        }
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
    /*
    public void drawImg(Graphics g,int xx, int yy, int w, int h){

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
    */
    public node[] getNeighbors(){
        return neighbors;
    }
    public BufferedImage getImg(){
        return img;
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
        //System.out.println(x0+" "+y0+" 00");
        for(int i=0; i<6; i++) {
            double v = i*Math.PI/3;
            xPoints[i] = x0 + (int)Math.round((getWidth()/2)*Math.sin(v));
            yPoints[i] = y0 + (int)Math.round((getHeight()/2)*Math.cos(v));
        }
        //g.setColor(Color.lightGray);
        //g.drawPolygon(xPoints, yPoints, 6);
        //g.drawImage(img, getX(), getY(), getWidth()*50/58, getHeight(), null);
        //drawImg(g, this.getX(), this.getY(), getWidth(), getHeight());
        super.paintComponent(g);
    }

    public node get(int i){
        if (i<6){
            return neighbors[i];
        }
        return null;
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

    public String toString(){
        return val+getPlaced();
    }

    public boolean equals(node n){
        String v = n.getVal();
        if (v.equals(val)){
            node[]neighbors1=n.getNeighbors();
            for (int i=0;i<6;i++){
                if (!neighbors[i].getVal().equals(neighbors1[i].getVal())){
                    return false;
                }
            }
            return true;
        }
        return false;

    }
}