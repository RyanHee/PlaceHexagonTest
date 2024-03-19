import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class Node extends JButton {

    private HashSet<String>animals;
    private Polygon hexagon;
    private String val, animal;
    private int rotateAngle;
    private Node[] neighbors;
    private int[]xPoints, yPoints;
    private boolean isPlaced;
    private BufferedImage img;
    private String[] Sides;
    public Node(String label){
        super(label);
        //id=0;
        animal="";
        xPoints = new int[6];
        yPoints = new int[6];
        neighbors=new Node[6];
        Sides=new String[6];
        isPlaced=false;
        setOpaque(false);
        setContentAreaFilled(false);
        rotateAngle=0;
        animals=new HashSet<>();
        hexagon = new Polygon(xPoints, yPoints, 6);
        try{
            img = ImageIO.read(Node.class.getResource("tile1.png"));
        }
        catch (Exception e){
            System.out.println("fuck");
        }
    }


    public Node(String label, String s){
        super(label);
        animal="";
        xPoints = new int[6];
        yPoints = new int[6];
        neighbors=new Node[6];
        val=s;
        //System.out.println(Arrays.toString(xPoints));
        //System.out.println(Arrays.toString(yPoints));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        hexagon = new Polygon(xPoints, yPoints, 6);
        try{
            img = ImageIO.read(new File("img/Tile/"+val+".png"));
        }
        catch (Exception e){
            System.out.println("fuck");
        }
        isPlaced=true;
        for (int i=0;i<6;i++){
            Node n = new Node("");
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
        updateNeighbor();
    }

    public boolean setAnimal(String s){
        if (animal.equals("")){
            if (animals.contains(s)){
                animal=s;
                return true;
            }
        }
        return false;
    }

    public int addRotateAngle(){
        rotateAngle+=60;
        String[]lst = new String[6];
        for (int i=0;i<6;i++){
            if (i>0){
                lst[i]=Sides[i-1];
            }
            else{
                lst[i]=Sides[5];
            }
        }
        System.out.println(Arrays.toString(lst));
        Sides=lst;
        return rotateAngle;
    }

    public int getRotateAngle(){
        return rotateAngle;
    }

    public void setNeighbor(Node n, int idx){
        if (neighbors[idx]==null){
            //System.out.println("here");
            neighbors[idx]=n;
            if (idx<3){
                n.setNeighbor(this, idx+3);
            }
            else{
                n.setNeighbor(this, idx-3);
            }
        }
        else if (!neighbors[idx].getPlaced()){
            //neighbors[idx]=n;
            //System.out.println("tf");
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
            //System.out.println("this place is placed!!!");
        }
    }


    public boolean hasEmptyNeighbors(){
        for (Node n:neighbors){
            if (n==null){
                continue;
            }
            if (n.getVal()==null){
                return true;
            }
        }
        return false;
    }
    public String getVal(){
        return val;
    }
    public void setVal(String v){
        if (val!=null && !val.equals("")){
            return;
        }
        if (v.equals("empty")){
            val="empty";
            updateNeighbor();
        }
        val=v;
        isPlaced=true;
        //System.out.println(val);
        try{
            img = ImageIO.read(new File("img/Tile/"+val+".png"));
            String[]lst=val.split("-");
            String left=lst[0].substring(0,1);
            String right=lst[0].substring(1,2);
            for (int i=0;i<3;i++){
                Sides[i]=right;
            }
            for (int i=3;i<6;i++) {
                Sides[i]=left;
            }
            System.out.println(Arrays.toString(Sides));
            char[]animallst=lst[1].toCharArray();
            for (char c:animallst){
                animals.add(Character.toString(c));
            }
        }
        catch (Exception e){
            System.out.println("fuck");
        }
        for (int i=0;i<6;i++){
            if (neighbors[i]==null){
                //System.out.println(toString()+" "+i+" null!");
                neighbors[i]=new Node("");
                Node neighbor = neighbors[i];
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
        updateNeighbor();
    }


    public int neighborCount(){
        int i=0;
        for (Node n:neighbors){
            if (n!=null)
                i++;
        }
        return i;
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

    public Node[] getNeighbors(){
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

    public void updateNeighbor(){
        for (int i=0;i<6;i++){
            Node neighbor = neighbors[i];
            Node neighbor1;
            if (i==5){
                neighbor1=neighbors[0];
            }
            else{
                neighbor1=neighbors[i+1];
            }
            int num=i+2;
            if (i>3){
                num-=6;
            }
            neighbor.setNeighbor(neighbor1, num);
        }
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


    public Node get(int i){
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

    public boolean equals(Node n){
        String v = n.getVal();
        if (v.equals(val)){
            Node[]neighbors1=n.getNeighbors();
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