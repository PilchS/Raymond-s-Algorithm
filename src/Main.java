import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Node {
    int id;
    int holderval;
    Node l;
    Node r;
    Node parent;

    void displayNode() {
        System.out.println("----------------------");
        if (parent != null) {
            System.out.println("Node " + id + " sends request to Node " + parent.id);
        }
        System.out.println("Node " + id + ":");
        System.out.println("Parent: " + (parent != null ? parent.id : "None"));
        System.out.println("----------------------");
    }
}

class BinaryTree {
    static void traversalInorder(Node rootNode) {
        if (rootNode == null) {
            return;
        }
        traversalInorder(rootNode.l);
        rootNode.displayNode();
        traversalInorder(rootNode.r);
    }

    static void token(Node rootNode, int nodeCS) {
        if (nodeCS == rootNode.id) {
            System.out.println("Node " + rootNode.id + " sends the token back");
            return;
        }

        if (rootNode.parent != null) {
            System.out.println("Node " + rootNode.id + " sends request to Node " + rootNode.parent.id);
        }

        if (nodeCS < rootNode.id) {
            if (rootNode.l != null) {
                token(rootNode.l, nodeCS);
            }
        } else if (nodeCS > rootNode.id) {
            if (rootNode.r != null) {
                token(rootNode.r, nodeCS);
            }
        }
    }

    static void addToRequestQueue(Node requestingNode, Node rootNode) {
        Node currentNode = rootNode;
        while (currentNode != null) {
            if (requestingNode.id < currentNode.id) {
                if (currentNode.l == null) {
                    currentNode.l = requestingNode;
                    requestingNode.parent = currentNode;
                    break;
                } else {
                    currentNode = currentNode.l;
                }
            } else if (requestingNode.id > currentNode.id) {
                if (currentNode.r == null) {
                    currentNode.r = requestingNode;
                    requestingNode.parent = currentNode;
                    break;
                } else {
                    currentNode = currentNode.r;
                }
            } else {
                break; // Duplicate node, do nothing
            }
        }
    }

    public static void main(String[] args) {
        Node rootNode = null;
        Node newNode = null;
        Node node1;
        int nodeT;
        int nodeCS;

        String fileName = "input2.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            if ((line = br.readLine()) != null) {
                nodeT = Integer.parseInt(line);

                rootNode = new Node();
                node1 = new Node();
                rootNode.id = nodeT;
                rootNode.r = rootNode.l = null;
                rootNode.holderval = rootNode.id;

                while ((line = br.readLine()) != null) {
                    int idValue = Integer.parseInt(line);
                    newNode = new Node();
                    newNode.l = newNode.r = null;
                    newNode.id = idValue;
                    addToRequestQueue(newNode, rootNode);
                }
            }

            traversalInorder(rootNode);
            nodeCS = 2;
            System.out.println("----------------------");
            token(rootNode, nodeCS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
