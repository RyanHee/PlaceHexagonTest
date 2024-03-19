import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class Panel extends JPanel implements ActionListener {
    //private int[]xlst;
    //private int[]ylst;
    private int intt;
    private HashMap<Node, Boolean>map;
    private HashMap<String, BufferedImage[]>animalTokenMap;
    private ArrayList<Node>visted;
    private int[]xPoints, yPoints;
    private int angle, drawSelected;
    private static BufferedImage selectOutline, outline, rotateImage;
    private Node test, n1, nodeSelected;
    private HexButton rotate;
    private ArrayList<String> tileNames, animalList;
    private String[] tileName4, animalToken4;
    private BufferedImage[] tiles4;
    private HexButton[] fourButtonTiles;
    private JButton[]fourButtonAnimal;
    private boolean valSelected;
    private String curVal;
    //private HexButton hexButton;
    public Panel(){
        nodeSelected=null;
        try{
            //img = ImageIO.read(Panel.class.getResource("tile.png"));
            //img1 = ImageIO.read(Panel.class.getResource("tile1.png"));
            outline=ImageIO.read(new File("img/tileOutline.png"));
            selectOutline=ImageIO.read(new File("img/selectedTile.png"));

            Scanner sc = new Scanner(new File("names.txt"));
            tileNames = new ArrayList<>();
            animalList = new ArrayList<>();
            while (sc.hasNext()){
                tileNames.add(sc.next());
            }
            for (int i=0;i<20;i++){
                animalList.add("B");
                animalList.add("E");
                animalList.add("H");
                animalList.add("S");
                animalList.add("F");
            }
            Collections.shuffle(animalList);
            Collections.shuffle(tileNames);
            tiles4=new BufferedImage[4];
            fourButtonTiles =new HexButton[4];
            fourButtonAnimal= new JButton[4];
            tileName4 =new String[4];
            animalToken4=new String[4];
            for (int i=0;i<4;i++){
                tiles4[i]=ImageIO.read(new File("img/Tile/"+ tileNames.get(0)+".png"));
                tileName4[i]= tileNames.get(0);
                tileNames.remove(0);
                fourButtonTiles[i]=new HexButton("");
                fourButtonTiles[i].addActionListener(this);
                fourButtonAnimal[i]=new JButton("");
                fourButtonAnimal[i].addActionListener(this);
                animalToken4[i]=animalList.remove(0);
            }


            String[]A=new String[]{"B", "E", "F", "H", "S"};
            String[] ALong=new String[]{"bear", "elk", "fox", "hawk", "salmon"};
            for (int i=0;i<5;i++){
                animalTokenMap.put(A[i], new BufferedImage[]{ImageIO.read(new File("img/tokens/"+ALong[i]+".png")),
                        ImageIO.read(new File("img/tokens/"+ALong[i]+"Active.png")),
                        ImageIO.read(new File("img/tokens/"+ALong[i]+"Inactive.png"))});
            }

            rotateImage=ImageIO.read(new File("img/tilePlacementRotateClockwise.png"));
            //System.out.println(Arrays.toString(tiles4));
        }
        catch (Exception e){
            System.out.println(1231);
        }
        angle=0;
        drawSelected=-1;
        test=new Node("", "MS-FHB");
        n1 = new Node("");
        n1.addActionListener(this);
        map=new HashMap<>();
        curVal="";
        valSelected=false;
        rotate = new HexButton("");
        rotate.addActionListener(this);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int w=58;
        int h=58;
        int i=1;
        visted=new ArrayList<>();
        map=new HashMap<>();
        putButtons(g, test,800, 450, w, h, i );

    }
    public void paint(Graphics g){

        super.paint(g);


        for (int i=0;i<4;i++){
            add(fourButtonTiles[i]);
            fourButtonTiles[i].setBounds(95, 100+i*100, 87, 87);
            //FourButtons[i].paintComponent(g);

            g.drawImage(tiles4[i], 100, 100+i*100, 75, 87, null);
            g.drawImage(outline, 100, 100+i*100, 75, 87, null);
            if (i==drawSelected){
                g.drawImage(selectOutline, 100, 100+i*100, 75, 87, null);
            }
        }

        g.drawImage(rotateImage, 1203, 500, 75, 87, null);
        add(rotate);
        rotate.setBounds(1200, 500, 87, 87);
        //rotate.paintComponent(g);
        /*
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
        //test.setBounds(800*getWidth()/1600, 450*getHeight()/900, w, h);
        //System.out.println(test.getX()+" "+test.getY());
        //test.paintComponent(g);

        */

    }

    private void putButtons(Graphics g, Node n, int x, int y, int w, int h, int id){
        if (n==null){
            return;
        }
        if (map.get(n)!=null && map.get(n)){
            return;
        }
        //System.out.println("here? "+n);
        //System.out.println(n+" "+x+", "+y);
        double[]xlst=new double[6];
        double[]ylst=new double[6];
        for(int i = 0; i < 6; i++) {
            double v = i*Math.PI/3;
            //use this for ^
            xlst[i] = x+w/2-w/2*Math.cos(v + Math.PI/2);
            ylst[i] = y+h/2-h/2*Math.sin(v + Math.PI/2);
            //use this for ------
            //xPoints[i] = x + (int)Math.round(-width*Math.sin(v + Math.PI/2));
            //yPoints[i] = y + (int)Math.round(-height*Math.cos(v + Math.PI/2));
        }
        double er=w*50*4/58/58;
        //String s="hh"+id;
        if (n.getVal()!=null){
            //s=n.getVal();
            n.updateNeighbor();
        }
        else{
            if (n.neighborCount()==6){
                n.updateNeighbor();
            }
        }
        //System.out.println(s+" "+n.neighborCount());
        Graphics2D g2 = (Graphics2D) g.create();
        g2.rotate(Math.toRadians(n.getRotateAngle()), x + 28, y + 29);

        g2.drawImage(n.getImg(), x+3, y, w*50/58, h, null);
        g2.dispose();
        //g.drawImage(n.getImg(), x+3, y, w*50/58, h,null);
        //g.drawImage(outline, x-2, y, w*50/58, h, null);
        //g.drawString(s, x+15, y+30);

        n.addActionListener(this);
        add(n);
        n.setBounds(x,y,w,h);
        visted.add(n);
        map.put(n, true);
        id++;

        int[]nx=new int[6];
        int[]ny=new int[6];
        nx[0]=x+25;
        nx[1]=x+50;
        nx[2]=x+25;
        nx[3]=x-25;
        nx[4]=x-50;
        nx[5]=x-25;
        ny[0]=y-44;
        ny[1]=y;
        ny[2]=y+44;
        ny[3]=y+44;
        ny[4]=y;
        ny[5]=y-44;
        /*
        nx[0]= (int) Math.round(xlst[0]-er);
        nx[1]=(int) Math.round(xlst[1]-er);
        nx[2]=(int) Math.round(xlst[0]-er);
        nx[3]=(int) Math.round(xlst[0]-w+er);
        nx[4]=(int) Math.round(xlst[5]-w+er);
        nx[5]=(int) Math.round(xlst[0]-w+er);
        ny[0]=(int)Math.round(ylst[1]-h);
        ny[1]=(int)Math.round(ylst[0]);
        ny[2]=(int)Math.round(ylst[2]);
        ny[3]=(int)Math.round(ylst[4]);
        ny[4]=(int)Math.round(ylst[0]);
        ny[5]=(int)Math.round(ylst[1]-h);
        */
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
            return;
        }

        for (int i=0;i<4;i++){
            HexButton b = fourButtonTiles[i];
            if (e.getSource().equals(b)&&!valSelected){
                System.out.println("FOurbUttons");
                valSelected=true;
                curVal= tileName4[i];
                System.out.println(curVal);
                drawSelected=i;
                nodeSelected=null;
                repaint();
                return;
            }
        }
        if (nodeSelected!=null && e.getSource().equals(rotate)){
            nodeSelected.addRotateAngle();
            System.out.println("rotateeeee");
            repaint();
            return;
        }
        Node n=(Node) e.getSource();
        if (n!=null&&!n.getPlaced()&&valSelected){
            //System.out.println("plz work");
            System.out.println(n);
            n.setVal(curVal);
            valSelected=false;
            tileName4[drawSelected]= tileNames.get(0);
            tileNames.remove(0);
            try{
                tiles4[drawSelected]=ImageIO.read(new File("img/Tile/"+ tileName4[drawSelected]+".png"));
            }
            catch (Exception E){
                System.out.println("blah");
            }
            drawSelected=-1;
            nodeSelected=n;
        }




        repaint();
    }
}