# Interactive Graph Visualization Tool
# Author: Marcin Sitarz

# Demonstration
![grapheditor_screen](https://github.com/Eastman16/GraphEditor/assets/129722863/787657f9-06a1-4c73-b64a-b15283146d46)

## Description
This Java application allows users to create and manipulate graphs for Moore machines. It provides an intuitive graphical interface where nodes represent states and edges represent transitions between states.

## Features
- **Node Management:** Add, remove, and modify nodes representing states in the Moore machine.
- **Edge Management:** Create, delete, and customize edges representing transitions between states.
- **Graph Export:** Export the created graph as an image file for sharing or further use.
- **Example Graph:** Load a pre-built example graph to get started quickly.
- **Data Persistence:** Save and load graphs to and from binary files for later use.

## Usage
- **Creating Nodes:** Right-click on an empty area of the canvas and select "Create new Node" from the context menu. Enter the index q and y for the new node.
- **Creating Edges:** Right-click on a node and select "Create Edge from this Node" from the context menu. Choose the destination node and provide the z index for the edge.
- **Modifying Nodes:** Right-click on a node and select the desired action from the context menu, such as changing the q or y index, changing the node's color, or deleting the node.
- **Modifying Edges:** Right-click on an edge to change its properties, such as the z index, color, or to delete it.
- **Exporting Graph:** Use the "Export" option from the menu bar to export the graph as a PNG image file.
- **Saving and Loading:** Use the "Save file" and "Open file" options from the menu bar to save and load graphs from binary files.
