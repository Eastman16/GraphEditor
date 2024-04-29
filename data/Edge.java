package GraphEditor.data;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;

public class Edge implements Serializable {

    String nameZ;
    String help ="";
    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;
    private Color color;

    public Edge(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = Color.CYAN;
    }

    public Edge(int x1, int y1, int x2, int y2, String nameZ){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = Color.BLACK;
        this.nameZ=nameZ;
    }

    public Edge(int x1, int y1, int x2, int y2, String nameZ, String help){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = Color.BLACK;
        this.nameZ=nameZ;
        this.help = help;
    }

    public String getHelp() {
        return help;
    }
    public void setNameZ(String nameZ) {
        this.nameZ = nameZ;
    }
    public int getX1() {return x1;}
    public void setX1(int x1) {this.x1 = x1;}
    public int getY1() {return y1;}
    public void setY1(int y1) {this.y1 = y1;}
    public int getX2() {return x2;}
    public void setX2(int x2) {this.x2 = x2;}
    public int getY2() {return y2;}
    public void setY2(int y2) {this.y2 = y2;}
    public Color getColor() {return color;}
    public void setColor(Color color) {this.color = color;}

    //moving edge by dx and dy
    public void moveEdge(int dx, int dy, Edge edge){
        edge.setX1(edge.getX1()+dx);
        edge.setY1(edge.getY1()+dy);
        edge.setX2(edge.getX2()+dx);
        edge.setY2(edge.getY2()+dy);
    }

    //checking if the mouse is over the edge
    public boolean isMouseOver(int mx, int my){
            double distance = Line2D.ptSegDist(x1, y1, x2, y2, mx,my);
            if(distance<2) return true;
        if(this.getX1()==this.getX2() && this.getY1()==this.getY2()){
            if((this.getX1()+25-mx)*(this.getX1()+25-mx)+(this.getY1()-15-my)*(this.getY1()-15-my)<=225) return true;
            else if((this.getX1()+25-mx)*(this.getX1()+25-mx)+(this.getY1()+15-my)*(this.getY1()+15-my)<=225) return true;
        }
        return false;
    }

    //drawing the edge from x1,y1 to x2,y2
    void draw (Graphics g){
        g.setColor(color);

        if(help.equals("help2")){
            g.drawOval(x1,y1-30,30,30);
            g.drawString("z"+nameZ,x1+25,y1-10);
            return;
        }

        if(help.equals("help")){
            g.drawString("z"+nameZ,(x1+x2)/2 -35,(y1+y2)/2 +20);
            return;
        }else{
            if(x1==x2 && y1==y2){
                g.drawOval(x1,y1,30,30);
                g.drawString("z"+nameZ,x1+25,y1+20);
                return;
            }
            else{
                g.drawLine(x1,y1,x2,y2);
                g.drawString("z"+nameZ,(x1+x2)/2 -35,(y1+y2)/2 -5);
                return;
            }
        }

    }
}
