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
    private HashSet<Node> visited;
    private HashMap<String, BufferedImage[]>animalTokenMap;
    //private ArrayList<Node>visted;
    private int[]xPoints, yPoints;
    private int angle, numSelectedTile, numSelectedAnimal;
    private static BufferedImage selectOutline, outline, rotateImage;
    private Node test, n1, nodeSelected;
    private HexButton rotate;
    private ArrayList<String> tileNames, animalDeck;
    private String[] tileName4, animalToken4;
    private BufferedImage[] tiles4;
    private HexButton[] fourButtonTiles;
    private InvisButton[]fourButtonAnimal;
    private boolean drawHighlightAnimal, valSelected, pickTurn, putTurn, rotateTurn, confirmTurn, animalTurn, putAnimalTurn;
    private JButton confirmB, cancelB;
    private String curVal, curAnimal;
    //private HexButton hexButton;
    public Panel(){
        nodeSelected=null;
        numSelectedAnimal=-1;
        try{
            //img = ImageIO.read(Panel.class.getResource("tile.png"));
            //img1 = ImageIO.read(Panel.class.getResource("tile1.png"));
            outline=ImageIO.read(new File("img/tileOutline.png"));
            selectOutline=ImageIO.read(new File("img/selectedTile.png"));

            Scanner sc = new Scanner(new File("names.txt"));
            tileNames = new ArrayList<>();
            animalDeck = new ArrayList<>();
            while (sc.hasNext()){
                tileNames.add(sc.next());
            }
            for (int i=0;i<20;i++){
                animalDeck.add("B");
                animalDeck.add("E");
                animalDeck.add("H");
                animalDeck.add("S");
                animalDeck.add("F");
            }
            Collections.shuffle(animalDeck);
            Collections.shuffle(tileNames);
            tiles4=new BufferedImage[4];
            fourButtonTiles =new HexButton[4];
            fourButtonAnimal= new InvisButton[4];
            tileName4 =new String[4];
            animalToken4=new String[4];
            animalTokenMap=new HashMap<>();
            for (int i=0;i<4;i++){
                tiles4[i]=ImageIO.read(new File("img/Tile/"+ tileNames.get(0)+".png"));
                tileName4[i]= tileNames.get(0);
                tileNames.remove(0);
                fourButtonTiles[i]=new HexButton("");
                fourButtonTiles[i].addActionListener(this);
                fourButtonAnimal[i]=new InvisButton("");
                fourButtonAnimal[i].addActionListener(this);
                fourButtonAnimal[i].showButton();
                animalToken4[i]= animalDeck.remove(0);
            }

            System.out.println("here");
            String[]A=new String[]{"B", "E", "F", "H", "S"};
            String[] ALong=new String[]{"bear", "elk", "fox", "hawk", "salmon"};
            for (int i=0;i<5;i++){
                animalTokenMap.put(A[i], new BufferedImage[]{ImageIO.read(new File("img/tokens/"+ALong[i]+".png")),
                        ImageIO.read(new File("img/tokens/"+ALong[i]+"Active.png")),
                        ImageIO.read(new File("img/tokens/"+ALong[i]+"Inactive.png"))});
            }
            System.out.println("blck");
            rotateImage=ImageIO.read(new File("img/tilePlacementRotateClockwise.png"));
            //System.out.println(Arrays.toString(tiles4));
        }
        catch (Exception e){
            System.out.println(1231);
        }
        angle=0;
        numSelectedTile =-1;
        confirmB=new JButton("confirm");
        confirmB.addActionListener(this);
        cancelB=new JButton("cancel");
        cancelB.addActionListener(this);

        test=new Node("", "MS-FHB");
        n1 = new Node("");
        n1.addActionListener(this);
        visited =new HashSet<>();
        curVal="";
        valSelected=false;
        rotate = new HexButton("");
        rotate.addActionListener(this);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int w=116;
        int h=116;
        int i=1;
        visited =new HashSet<>();
        putButtons(g, test,800, 450, w, h);



    }
    public void paint(Graphics g){

        super.paint(g);
        add(confirmB);
        confirmB.setBounds(1200, 700, 200, 50);

        add(cancelB);
        cancelB.setBounds(1200, 800, 200, 50);

        for (int i=0;i<4;i++){
            add(fourButtonTiles[i]);
            add(fourButtonAnimal[i]);
            fourButtonTiles[i].setBounds(95, 100+i*100, 87, 87);
            //FourButtons[i].paintComponent(g);
            g.drawImage(tiles4[i], 100, 100+i*100, 75, 87, null);
            g.drawImage(outline, 100, 100+i*100, 75, 87, null);
            if (i== numSelectedTile){
                g.drawImage(selectOutline, 100, 100+i*100, 75, 87, null);
            }
            if (i==numSelectedAnimal&&drawHighlightAnimal){
                g.drawImage(animalTokenMap.get(animalToken4[i])[1], 200, 113+i*100, 60, 60, null);
            }
            else{
                g.drawImage(animalTokenMap.get(animalToken4[i])[0], 200, 113+i*100, 60, 60, null);
            }
            fourButtonAnimal[i].setBounds(270, 113+i*100, 60, 60);

        }

        g.drawImage(rotateImage, 1204, 500, 75, 87, null);
        add(rotate);
        rotate.setBounds(1200, 500, 87, 87);


    }

    private void putButtons(Graphics g, Node n, int x, int y, int w, int h){
        if (n==null){
            return;
        }
        if (visited.contains(n)){
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
        g2.rotate(Math.toRadians(n.getRotateAngle()), x + 58, y + 58);

        g2.drawImage(n.getImg(), x+8, y, w*50/58, h, null);
        if (animalTokenMap.get(n.getAnimal())!=null){
            g.drawImage(animalTokenMap.get(n.getAnimal())[0], x-17+w*50/116, y-25+h/2, 50, 50, null);
        }
        g2.dispose();
        //g.drawImage(n.getImg(), x+3, y, w*50/58, h,null);
        //g.drawImage(outline, x-2, y, w*50/58, h, null);
        //g.drawString(s, x+15, y+30);
        n.paintComponent(g);
        n.addActionListener(this);
        add(n);
        n.setBounds(x,y,w,h);
        visited.add(n);
        //id++;

        int[]nx=new int[6];
        int[]ny=new int[6];
        nx[0]=x+w*50/116;
        nx[1]=x+w*50/58;
        nx[2]=x+w*50/116;
        nx[3]=x-w*50/116;
        nx[4]=x-w*50/58;
        nx[5]=x-w*50/116;
        ny[0]=y-h*3/4;
        ny[1]=y;
        ny[2]=y+h*3/4;
        ny[3]=y+h*3/4;
        ny[4]=y;
        ny[5]=y-h*3/4;
        for (int i=0;i<6;i++){
            putButtons(g, n.getNeighbors()[i], nx[i], ny[i], w, h);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(1);
        if ((putTurn||rotateTurn||confirmTurn||animalTurn)==false){
            pickTurn=true;
        }
        else{
            pickTurn=false;
        }
        if (e.getSource().equals(n1)){
            System.out.println("how tf does this work");
            return;
        }


        //select tile
        for (int i=0;i<4;i++){
            HexButton b = fourButtonTiles[i];
            if (e.getSource().equals(b)&&pickTurn){
                System.out.println("FOurbUttons");
                putTurn=true;
                pickTurn=false;
                curVal= tileName4[i];
                System.out.println(curVal);
                numSelectedTile =i;
                numSelectedAnimal=i;
                nodeSelected=null;
                repaint();
                return;
            }
        }

        //rotate angle
        if (nodeSelected!=null && e.getSource().equals(rotate) && rotateTurn){
            nodeSelected.addRotateAngle();
            System.out.println("rotateeeee");
            //rotateTurn=false;
            confirmTurn=true;
            repaint();
            return;
        }

        //confirm tile placement
        if (e.getSource().equals(confirmB)&&confirmTurn){
            animalTurn=true;
            confirmTurn=false;
            rotateTurn=false;
            drawHighlightAnimal=true;
            repaint();
            return;
        }

        //put hexagon on board
        if (putTurn) {
            Node n = (Node) e.getSource();
            if (n != null && !n.getPlaced()) {
                //System.out.println("plz work");
                System.out.println(n);
                n.setVal(curVal);
                putTurn = false;
                tileName4[numSelectedTile] = tileNames.get(0);
                tileNames.remove(0);
                try {
                    tiles4[numSelectedTile] = ImageIO.read(new File("img/Tile/" + tileName4[numSelectedTile] + ".png"));
                } catch (Exception E) {
                    System.out.println("blah");
                }
                rotateTurn = true;
                numSelectedTile = -1;
                nodeSelected = n;
                confirmTurn=true;
                repaint();
                return;
            }
        }



        if (animalTurn){
            //pick animal
            if (fourButtonAnimal[numSelectedAnimal].equals(e.getSource())){
                curAnimal=animalToken4[numSelectedAnimal];
                //animalToken4[i]=animalDeck.remove(0);
                animalTurn=false;
                putAnimalTurn=true;
                repaint();
                return;
            }
            //cancel animal
            if (e.getSource().equals(cancelB)){
                curAnimal="";
                numSelectedAnimal=-1;
                animalTurn=false;
                drawHighlightAnimal=false;
                repaint();
                return;
            }

        }



        if (putAnimalTurn){
            Node n = (Node) e.getSource();
            if (n.setAnimal(curAnimal)){
                System.out.println("set animal success");
                curAnimal="";
                animalToken4[numSelectedAnimal]=animalDeck.remove(0);
                numSelectedAnimal=-1;
                animalTurn=false;
                drawHighlightAnimal=false;
                repaint();
                return;
            }
        }


    }
}