package GraphEditor.data;

import java.awt.*;
import java.io.Serializable;

public class Node implements Serializable {
    String nameQ;
    String nameY;
    protected int x;
    protected int y;
    protected int r = 20;
    private Color color=Color.WHITE;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
        this.color = Color.WHITE;
    }

    public Node(int x, int y, String nameQ, String nameY){
        this.x=x;
        this.y=y;
        this.nameQ=nameQ;
        this.nameY=nameY;
    }
    public String getNameQ() {
        return nameQ;
    }
    public void setNameQ(String nameQ) {
        this.nameQ = nameQ;
    }
    public String getNameY() {
        return nameY;
    }
    public void setNameY(String nameY) {
        this.nameY = nameY;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getR() {
        return r;
    }
    public void setR(int r) {
        this.r = r;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    //checking if the mouse is over the node
    public boolean isMouseOver(int mx, int my){return (x-mx)*(x-mx)+(y-my)*(y-my)<=r*r;}

    //moving the node
    public void moveNode(int dx, int dy, Node node){
        node.setX(node.getX()+dx);
        node.setY(node.getY()+dy);
    }

    //drawing the node
    void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x-r,y-r,2*r,2*r);
        g.setColor(Color.BLACK);
        g.drawOval(x-r,y-r,2*r,2*r);
    }
}
