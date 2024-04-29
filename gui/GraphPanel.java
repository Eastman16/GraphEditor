package GraphEditor.gui;

import GraphEditor.data.Edge;
import GraphEditor.data.Graph;
import GraphEditor.data.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener,KeyListener {
    private static final long serialVersionUID = 1L;
    private int mouseX=0;
    private int mouseY=0;

    protected Graph graph;
    protected Node nodeUnderCursor = null;
    protected Edge edgeUnderCursor = null;
    protected int mouseCursor = Cursor.DEFAULT_CURSOR;
    private boolean mouseButtonLeft = false;
    private boolean mouseButtonRight = false;

    public GraphPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        setFocusable(true);
        requestFocus();
    }

    public Graph getGraph() {return graph;}
    public void setGraph(Graph graph) {this.graph = graph;}

    //checking if you hovered over a node
    private Node findNode(int mx, int my) {
        for (Node node : graph.getNodes()) {
            if(node.isMouseOver(mx,my)){
                return node;
            }
        }
        return null;
    }
    //checking if you hovered over an edge
    private Edge findEdge(int mx, int my){
        for (Edge edge : graph.getEdges()){
            if(edge.isMouseOver(mx,my)){
                return edge;
            }
        }
        return null;
    }
    //getting the coordinates of the edge you hovered over
    private Edge findEdge(MouseEvent event){
        return findEdge(event.getX(),event.getY());
    }


    //getting the coordinates of the node you hovered over
    private Node findNode(MouseEvent event) {
        return findNode(event.getX(), event.getY());
    }

    //change of the cursor depending on the object you hovered over
    protected void setMouseCursor(MouseEvent event){
        nodeUnderCursor = findNode(event);
        edgeUnderCursor = findEdge(event);
        if(nodeUnderCursor!=null || edgeUnderCursor!=null){
            mouseCursor=Cursor.HAND_CURSOR;
        }else if(mouseButtonLeft){
            mouseCursor=Cursor.MOVE_CURSOR;
        }else{
            if(!(mouseCursor==Cursor.WAIT_CURSOR))
                mouseCursor=Cursor.DEFAULT_CURSOR;
        }

        setCursor(Cursor.getPredefinedCursor(mouseCursor));
        mouseX= event.getX();
        mouseY= event.getY();

    }
    protected void setMouseCursor(){
        nodeUnderCursor=findNode(mouseX,mouseY);
        edgeUnderCursor=findEdge(mouseX,mouseY);
        if(nodeUnderCursor!=null || edgeUnderCursor!=null){
            mouseCursor=Cursor.HAND_CURSOR;
        } else if(mouseButtonLeft){
            mouseCursor=Cursor.MOVE_CURSOR;
        }else{
            mouseCursor=Cursor.DEFAULT_CURSOR;
        }
        setCursor(Cursor.getPredefinedCursor(mouseCursor));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(graph==null) return;
        graph.draw(g);
        draw(g);
    }

    //popup menu for creating a new node
    protected void createPopupMenu(MouseEvent event){
        JPopupMenu popup = new JPopupMenu();
        JMenuItem itemCreateNewNode = new JMenuItem("Create new Node");
        popup.add(itemCreateNewNode);

        itemCreateNewNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action){
                String nameQ = JOptionPane.showInputDialog("Insert index q:");
                String nameY = JOptionPane.showInputDialog("Insert index y:");
                if(nameQ!=null && !nameQ.equals("") && nameY!=null && !nameY.equals("")){
                    graph.addNode(new Node(event.getX(), event.getY(),nameQ,nameY));
                    repaint();
                }else{
                    JOptionPane.showMessageDialog(null,"Looks like you forgot to fill some option","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        popup.show(event.getComponent(),event.getX(),event.getY());
    }

    //popup menu for changing the properties of an edge
    protected void createPopupMenu(MouseEvent event, Edge edge){
        JPopupMenu popup = new JPopupMenu();

        JMenuItem itemChangeNameZ = new JMenuItem("Change z index");
        popup.add(itemChangeNameZ);
        itemChangeNameZ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newZ = JOptionPane.showInputDialog(null,"Insert new index z","New z index",JOptionPane.PLAIN_MESSAGE);
                if(newZ!=null && !newZ.equals("")){
                    edge.setNameZ(newZ);
                    repaint();
                }else{
                    JOptionPane.showMessageDialog(null,"Looks like you didn't fill the field","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JMenuItem itemRemoveEdge = new JMenuItem("Remove Edge");
        popup.add(itemRemoveEdge);
        itemRemoveEdge.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e){
               for(Edge edge1: graph.getEdges()){
                   if(edge1.getX1()==edge.getX1() && edge1.getY1()== edge.getY1() && edge1.getX2()==edge.getX2() && edge1.getY2()==edge.getY2()){
                       graph.removeEdge(edge);
                       if(edge1.getHelp().equals("help")){
                           graph.removeEdge(edge1);
                       }
                   }else if(edge1.getX1()==edge.getX2() && edge1.getY1()==edge.getY2() && edge1.getX2()== edge.getX1() && edge1.getY2()==edge.getY1()){
                       graph.removeEdge(edge);
                       if(edge1.getHelp().equals("help")){
                           graph.removeEdge(edge1);
                       }
                   }
               }
               repaint();
           }
        });
        JMenuItem itemChangeEdgeColor = new JMenuItem("Change Edge Color");
        popup.add(itemChangeEdgeColor);

        itemChangeEdgeColor.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e){
               Color newColor = JColorChooser.showDialog(null,"Choose Edge Color",edge.getColor());
               if(newColor!=null){
                   edge.setColor(newColor);
               }
               repaint();
           }
        });
        popup.show(event.getComponent(),event.getX(),event.getY());
    }

    //popup menu for changing the properties of a node
    protected void createPopupMenu(MouseEvent event, Node node) {
        JPopupMenu popup = new JPopupMenu();

        //change of the index q of the node
        JMenuItem itemChangeNameQ = new JMenuItem("Change q index");
        popup.add(itemChangeNameQ);
        itemChangeNameQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newQ = JOptionPane.showInputDialog(null, "Insert new index q", "New q index", JOptionPane.PLAIN_MESSAGE);
                if (newQ != null && !newQ.equals("")) {
                    node.setNameQ(newQ);
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Looks like you didn't fill the field", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //change of the index y of the node
        JMenuItem itemChangeNameY = new JMenuItem("Change y index");
        popup.add(itemChangeNameY);
        itemChangeNameY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newY = JOptionPane.showInputDialog(null, "Insert new index y", "New y index", JOptionPane.PLAIN_MESSAGE);
                if (newY != null && !newY.equals("")) {
                    node.setNameY(newY);
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Looks like you didn't fill the field", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //change of the color of the node
        JMenuItem itemChangeNodeColor = new JMenuItem("Change Node Color");
        popup.add(itemChangeNodeColor);

        itemChangeNodeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose Node Color", node.getColor());
                if (newColor != null) {
                    node.setColor(newColor);
                }
                repaint();
            }
        });

        //delete the node
        JMenuItem itemRemoveNode = new JMenuItem("Remove Node");
        popup.add(itemRemoveNode);

        itemRemoveNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.removeNode(node);
                repaint();
            }
        });

        //draw an edge from the node
        JMenuItem itemCreateEdge = new JMenuItem("Create Edge from this Node");
        popup.add(itemCreateEdge);

        itemCreateEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x1 = node.getX();
                int y1 = node.getY();
                int test = 0;
                String[] nodeIndexes = new String[graph.getNodes().length];
                for (Node node : graph.getNodes()) {
                    nodeIndexes[test] = node.getNameQ();
                    test++;
                }
                JComboBox combo = new JComboBox(nodeIndexes);
                JOptionPane.showMessageDialog(null, combo, "Choose index of the destination node", JOptionPane.QUESTION_MESSAGE);
                String result = (String) combo.getSelectedItem();
                String nameZ = JOptionPane.showInputDialog(null, "z index:", "Choose x index", JOptionPane.QUESTION_MESSAGE);
                if (nameZ != null && !nameZ.equals("")) {
                    int iterator = 0;
                    int x2 = 0;
                    int y2 = 0;
                    for (Node node : graph.getNodes()) {
                        if (result.equals(node.getNameQ())) {
                            x2 = node.getX();
                            y2 = node.getY();
                        }
                        iterator++;
                    }

                    if (node.getNameQ().equals(result)) {
                        Edge edgeNew = new Edge(x1, y1, x2, y2, nameZ, "help2");
                        graph.addEdge(edgeNew);
                        repaint();
                        return;
                    }

                    String help = "help";
                    boolean isAnother = false;

                    for (Edge edge : graph.getEdges()) {
                        if (edge.getX1() == x1 && edge.getY1() == y1 && edge.getX2() == x2 && edge.getY2() == y2) {
                            Edge edgeNew = new Edge(x1, y1, x2, y2, nameZ, help);
                            isAnother = true;
                            graph.addEdge(edgeNew);
                            repaint();
                        }
                        if (edge.getX1() == x2 && edge.getY1() == y2 && edge.getX2() == x1 && edge.getY2() == y1) {
                            Edge edgeNew = new Edge(x1, y1, x2, y2, nameZ, help);
                            isAnother = true;
                            graph.addEdge(edgeNew);
                            repaint();
                        }
                    }
                    if (!isAnother) {
                        Edge edgeNew = new Edge(x1, y1, x2, y2, nameZ);
                        graph.addEdge(edgeNew);
                        repaint();
                    }

                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Looks like you didn't fill the field", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        popup.show(event.getComponent(), event.getX(), event.getY());
    }

    //drawing the graph
    public void draw (Graphics g){
        int test=0;
        Integer[]nodeIndexes = new Integer[graph.getNodes().length];
        for(Node node: graph.getNodes()){
            nodeIndexes[test]=test;
            g.setColor(Color.BLACK);
            g.drawString("q"+node.getNameQ()+"/"+"y"+node.getNameY(),node.getX()-13,node.getY()+5);
            test++;
        }
        String nodeXY1="";
        String nodeXY2="";

        for(Edge edge: graph.getEdges()){
            for(Node node: graph.getNodes()){
                if(node.getX()==edge.getX1() && node.getY()==edge.getY1()) nodeXY1=node.getNameQ();
                if(node.getX()==edge.getX2() && node.getY()==edge.getY2()) nodeXY2=node.getNameQ();
            }
            if(nodeXY1.equals(nodeXY2)){
                if(edge.getHelp().equals("help2")){
                    g.drawString("(q"+nodeXY1+"-->q"+nodeXY2+")",edge.getX1()+36,edge.getY1()-10);
                }else{
                    g.drawString("(q"+nodeXY1+"-->q"+nodeXY2+")",edge.getX1()+36,edge.getY1()+20);
                }
            }else if(edge.getHelp().equals("help")){
                g.drawString("(q"+nodeXY1+"-->q"+nodeXY2+")",(edge.getX1()+ edge.getX2())/2 -24,(edge.getY1()+edge.getY2())/2 +20);

            }
            else{
                g.drawString("(q"+nodeXY1+"-->q"+nodeXY2+")",(edge.getX1()+ edge.getX2())/2 -24,(edge.getY1()+edge.getY2())/2 -5);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent event) {}
    @Override
    public void mouseEntered(MouseEvent event) {}
    @Override
    public void mouseExited(MouseEvent event) {}
    @Override
    public void mousePressed(MouseEvent event){
        if(event.getButton()==1) mouseButtonLeft=true;
        if(event.getButton()==3) mouseButtonRight=true;
        setMouseCursor(event);
    }
    @Override
    public void mouseReleased(MouseEvent event){
        if(event.getButton()==1){
            mouseButtonLeft=false;
        }
        if(event.getButton()==3){
            mouseButtonRight=false;
        }
        setMouseCursor(event);
        if(event.getButton()==3){
            if(nodeUnderCursor!=null){
                createPopupMenu(event,nodeUnderCursor);
            }else if(edgeUnderCursor!=null){
                createPopupMenu(event,edgeUnderCursor);
            }
            else{
                createPopupMenu(event);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent event){
        if(mouseButtonLeft){
            if(nodeUnderCursor!=null){
                Node starting_node = new Node(nodeUnderCursor.getX(),nodeUnderCursor.getY());
                for(Edge edge: graph.getEdges()){
                    if((edge.getX1()==starting_node.getX()) && (edge.getY1()==starting_node.getY())){
                        nodeUnderCursor.moveNode(event.getX()-mouseX,event.getY()-mouseY,nodeUnderCursor);
                        edge.setX1(nodeUnderCursor.getX());
                        edge.setY1(nodeUnderCursor.getY());
                        mouseX= event.getX();
                        mouseY=event.getY();
                    }
                    if((edge.getX2()==starting_node.getX()) && (edge.getY2()==starting_node.getY())){
                        nodeUnderCursor.moveNode(event.getX()-mouseX,event.getY()-mouseY,nodeUnderCursor);
                        edge.setX2(nodeUnderCursor.getX());
                        edge.setY2(nodeUnderCursor.getY());
                        mouseX= event.getX();
                        mouseY=event.getY();
                    }

                }
                nodeUnderCursor.moveNode(event.getX()-mouseX,event.getY()-mouseY,nodeUnderCursor);
                mouseX= event.getX();
                mouseY=event.getY();
            } else{
                graph.moveAllNodes(event.getX()-mouseX,event.getY()-mouseY);
                mouseX= event.getX();
                mouseY=event.getY();
            }

        }
        mouseX= event.getX();
        mouseY=event.getY();
        repaint();
        setMouseCursor(event);
    }
    @Override
    public void mouseMoved (MouseEvent event){
           setMouseCursor(event);

    }
    @Override
    public void keyPressed(KeyEvent event){
        int dist;
        if(event.isShiftDown()){
            dist=10;
        } else{
            dist=1;
        }
        switch(event.getKeyCode()){
            case KeyEvent.VK_LEFT:
                graph.moveAllNodes(-dist,0);
                break;
            case KeyEvent.VK_RIGHT:
                graph.moveAllNodes(dist,0);
                break;
            case KeyEvent.VK_UP:
                graph.moveAllNodes(0,-dist);
                break;
            case KeyEvent.VK_DOWN:
                graph.moveAllNodes(0, dist);
                break;
            case KeyEvent.VK_DELETE:
                if(nodeUnderCursor!=null){
                    graph.removeNode(nodeUnderCursor);
                    nodeUnderCursor=null;
                }
                if(edgeUnderCursor!=null){
                    for(Edge edge1: graph.getEdges()){
                        if(edge1.getX1()==edgeUnderCursor.getX1() && edge1.getY1()== edgeUnderCursor.getY1() && edge1.getX2()==edgeUnderCursor.getX2() && edge1.getY2()==edgeUnderCursor.getY2()){
                            graph.removeEdge(edgeUnderCursor);
                            if(edge1.getHelp().equals("help")){
                                graph.removeEdge(edge1);
                            }
                        }else if(edge1.getX1()==edgeUnderCursor.getX2() && edge1.getY1()==edgeUnderCursor.getY2() && edge1.getX2()== edgeUnderCursor.getX1() && edge1.getY2()==edgeUnderCursor.getY1()){
                            graph.removeEdge(edgeUnderCursor);
                            if(edge1.getHelp().equals("help")){
                                graph.removeEdge(edge1);
                            }
                        }
                    }
                    repaint();

                }
                break;
        }
        repaint();
        setMouseCursor();
    }
    @Override
    public void keyReleased(KeyEvent event){}
    @Override
    public void keyTyped(KeyEvent event){
        char symbol= event.getKeyChar();
        if(nodeUnderCursor!=null){
            switch(symbol){
                case 'r':
                    nodeUnderCursor.setColor(Color.RED);
                    break;
                case 'g':
                    nodeUnderCursor.setColor(Color.GREEN);
                    break;
                case 'b':
                    nodeUnderCursor.setColor(Color.blue);
                    break;
                case '+':
                    int r=nodeUnderCursor.getR()+10;
                    nodeUnderCursor.setR(r);
                    break;
                case '-':
                    r=nodeUnderCursor.getR()-10;
                    if(r>=10) nodeUnderCursor.setR(r);
                    break;
            }
            repaint();
            setMouseCursor();
        }
    }

}
