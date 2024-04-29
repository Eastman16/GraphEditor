package GraphEditor.data;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph(){
        this.nodes = new ArrayList<Node>();
        this.edges = new ArrayList<Edge>();
    }

    public void addNode(Node node){this.nodes.add(node);}
    public void addEdge(Edge edge){this.edges.add(edge);}
    public void removeNode(Node node){this.nodes.remove(node);}
    public void removeEdge(Edge edge){this.edges.remove(edge);}

    public Node[]getNodes(){
        Node[]array=new Node[0];
        return nodes.toArray(array);
    }

    public Edge[]getEdges(){
        Edge[]array=new Edge[0];
        return edges.toArray(array);
    }

    //iterating through all nodes and edges and drawing them
    public void draw (Graphics g){
        for (Edge edge : edges){
            edge.draw(g);
        }
        for (Node node : nodes){;
            node.draw(g);
        }
    }

    //iterating through all nodes and edges and moving them
    public void moveAllNodes(int dx, int dy){
        for (Node node : nodes){
            node.moveNode(dx, dy,node);
        }
        for(Edge edge : edges){
            edge.moveEdge(dx,dy,edge);
        }
    }

}
