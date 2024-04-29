package GraphEditor.gui;

import GraphEditor.data.Edge;
import GraphEditor.data.Graph;
import GraphEditor.data.Node;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GraphEditor extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    private static final String author = "Marcin Sitarz";
    private static final String title = "Graph editor for Moore machines";
    private static final String instruction = "Program description:\n" +
                                            "Author: Marcin Sitarz\n" +
                                            "The program is used to create graphs for Moore machines.\n";
    private static final String BIN_FILE = "test.BIN";

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenu menuGraph = new JMenu("Graph");
    private JMenu menuHelp = new JMenu("Help");
    private JMenu menuExit = new JMenu("Exit");
    private JMenuItem itemNewFile = new JMenuItem("New file", KeyEvent.VK_N);
    private JMenuItem itemOpenFile = new JMenuItem("Open file", KeyEvent.VK_O);
    private JMenuItem itemSaveFile = new JMenuItem("Save file", KeyEvent.VK_S);
    private JMenuItem itemExample = new JMenuItem("Load Example", KeyEvent.VK_L);
    private JMenuItem itemExport = new JMenuItem("Export");
    private JMenuItem itemListOfNodes = new JMenuItem("List of nodes", KeyEvent.VK_N);
    private JMenuItem itemListOfEdges = new JMenuItem("List of edges", KeyEvent.VK_E);
    private JMenuItem itemIntruction = new JMenuItem("Intruction", KeyEvent.VK_I);
    private JMenuItem itemAuthor = new JMenuItem("Author", KeyEvent.VK_A);
    private JMenuItem itemExit = new JMenuItem("Exit", KeyEvent.VK_DELETE);


    private GraphPanel panel = new GraphPanel();

    GraphEditor(){
        this.setTitle(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.add(panel);
        this.setJMenuBar(menuBar);
        menuBar.add(menuFile);
        menuBar.add(menuGraph);
        menuBar.add(menuHelp);
        menuBar.add(menuExit);
        menuFile.add(itemNewFile);
        menuFile.addSeparator();
        menuFile.add(itemOpenFile);
        menuFile.addSeparator();
        menuFile.add(itemSaveFile);
        menuFile.addSeparator();
        menuFile.add(itemExample);
        menuFile.addSeparator();
        menuFile.add(itemExport);
        menuGraph.add(itemListOfNodes);
        menuGraph.addSeparator();
        menuGraph.add(itemListOfEdges);
        menuHelp.add(itemIntruction);
        menuHelp.addSeparator();
        menuHelp.add(itemAuthor);
        menuExit.add(itemExit);
        showExample();

        itemNewFile.addActionListener(this);
        itemOpenFile.addActionListener(this);
        itemSaveFile.addActionListener(this);
        itemExample.addActionListener(this);
        itemListOfNodes.addActionListener(this);
        itemListOfEdges.addActionListener(this);
        itemIntruction.addActionListener(this);
        itemAuthor.addActionListener(this);
        itemExit.addActionListener(this);
        itemExport.addActionListener(this);

        this.setVisible(true);
    }

    //displaying list of nodes
    private void showListOfNodes(Graph graph) {
        Node array[]= graph.getNodes();
        String nodeCount = "Node count: " + array.length + "\n";
        for (Node node: array){
            nodeCount += "q"+node.getNameQ()+"(x: " + node.getX() +",y: "+node.getY()+",r: "+ node.getR()+")    ";
        }
        JOptionPane.showMessageDialog(this,nodeCount,"Node list",JOptionPane.PLAIN_MESSAGE);
    }
    //displaying list of edges
    private void showListOfEdges(Graph graph) {
        Edge[] array = graph.getEdges();
        String edgeCount = "Edge count: " + array.length + "\n";
        for(Edge edge: array){
            edgeCount += "[(x1: " + edge.getX1() +",y1: "+edge.getY1()+")-->(x2: "+ edge.getX2()+",y2: "+edge.getY2()+")],    ";
        }
        JOptionPane.showMessageDialog(this,edgeCount,"Edge list",JOptionPane.PLAIN_MESSAGE);

    }
    //filling the graph with example data
    private void showExample() {
        Graph graph = new Graph();
        Node node1=new Node(30,100,"0","0");
        node1.setR(20);
        node1.setColor(Color.MAGENTA);
        Node node2=new Node(209,48,"1","0");
        node2.setR(20);
        node2.setColor(Color.RED);
        Node node3=new Node(250,250,"2","1");
        node3.setR(20);
        node3.setColor(Color.YELLOW);
        Node node4 = new Node(150,300,"3","0");
        node4.setColor(Color.GREEN);
        node4.setR(20);
        Edge edge1=new Edge(30,100,209,48,"0");

        graph.addEdge(edge1);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        panel.setGraph(graph);
    }

    //save to binary file
    void saveGraphToFile(String filename, Graph graph) throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(graph);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Didn't find file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error while saving file");
        }
    }

    //load from binary file
    void loadGraphFromFile(String filename) throws Exception{
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            panel.setGraph((Graph)in.readObject());
        }catch(FileNotFoundException e){
            throw new Exception("File not found");
        }catch(Exception e){
            throw new Exception("Error while reading file");
        }
    }

    @Override
    public void actionPerformed(ActionEvent event){
        if(event.getSource()==itemExport){
            BufferedImage image = new BufferedImage(panel.getWidth(),panel.getHeight(),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            panel.printAll(g);
            g.dispose();
            try{
                ImageIO.write(image,"png",new File("Graph.png"));
                JOptionPane.showMessageDialog(null,"PNG was created successfully!","INFO",JOptionPane.INFORMATION_MESSAGE);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if(event.getSource()==itemNewFile){
            panel.setGraph(new Graph());
            repaint();
        }
        if(event.getSource()==itemExample){
            showExample();
            repaint();
        }
        if(event.getSource()==itemListOfNodes){
            showListOfNodes(panel.getGraph());
        }
        if(event.getSource()==itemListOfEdges){
            showListOfEdges(panel.getGraph());
        }
        if(event.getSource()==itemAuthor){
            JOptionPane.showMessageDialog(this,author,"About author",JOptionPane.INFORMATION_MESSAGE);
        }
        if(event.getSource()==itemIntruction){
            JOptionPane.showMessageDialog(this,instruction,"Instruction",JOptionPane.PLAIN_MESSAGE);
        }
        if(event.getSource()==itemOpenFile){
            try {
                loadGraphFromFile(BIN_FILE);
                JOptionPane.showMessageDialog(null, "Data loaded from:  " + BIN_FILE);
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Something went wrong:  " + BIN_FILE);
            }
            repaint();

        }
        if(event.getSource()==itemSaveFile){
            try{
            saveGraphToFile(BIN_FILE, panel.getGraph());
            JOptionPane.showMessageDialog(null, "Data saved to:  "+BIN_FILE);}
            catch (Exception e){
                JOptionPane.showMessageDialog(null, "Something went wrong:  "+BIN_FILE);
            }
        }
        if(event.getSource()==itemExit){
            System.exit(0);
        }
    }
}
