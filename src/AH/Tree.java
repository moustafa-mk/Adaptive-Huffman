package AH;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Tree {
    private Node root, NYT;
    private Set<Character> unique;
    private Map<Integer, Set<Node>> block;
    private Map<Character, Node> characterNodeMap;
    private Map<String, Character>  codeToChar;
    private Integer __ALPHA_SIZE;
    private String NYTCode;

    Tree() {
        unique = new HashSet<>();
        block = new HashMap<>();
        characterNodeMap = new HashMap<>();
        codeToChar = new HashMap<>();
        __ALPHA_SIZE = 26 * 2 - 1;
        root = new Node(null, 0, null, __ALPHA_SIZE--, "");
        NYT = root;
        NYTCode = "";
    }

    private Boolean exists(Character character) {
        return unique.contains(character);
    }

    String insert(Character character) {
        StringBuilder code;
        if(exists(character)) {
            Node current = getSymbolNode(character);
            code = new StringBuilder(current.mCode);
            updateTree(current);
            codeToChar = new HashMap<>();
            updateChildCode(root);
        } else {
            unique.add(character);
            removeNode(NYT);
            NYT.mRight = new Node(character, 1, NYT, __ALPHA_SIZE--, NYT.mCode + "1");
            NYT.mLeft = new Node(null, 0, NYT, __ALPHA_SIZE--, NYT.mCode + "0");
            addNode(NYT);
            characterNodeMap.put(character, NYT.mRight);
            addNode(NYT.mRight);
            addNode(NYT.mLeft);
            if(root.mRight == null) root = NYT;
            NYT = NYT.mLeft;

            Node parent = NYT.mParent;
            updateTree(parent);
            codeToChar = new HashMap<>();
            updateChildCode(root);

            code = new StringBuilder(Integer.toBinaryString(character));
            while(code.length() < 8) code.insert(0, "0");
            code.insert(0, NYTCode + " ");
            NYTCode += "0";
        }

        return code.toString();
    }

    private Node getSymbolNode(Character character) {
        return characterNodeMap.get(character);
    }

    private void addNode(Node node) {
        if(!block.containsKey(node.mFreq)) block.put(node.mFreq, new HashSet<>());
        block.get(node.mFreq).add(node);
    }

    private void removeNode(Node node) {
        if(block.containsKey(node.mFreq)) block.get(node.mFreq).remove(node);
    }

    private void updateTree(Node node) {
        if(node == null) return;
        Node swapNode = checkSwap(node);
        if(swapNode.mCode != null && !swapNode.equals(node)) swap(node, swapNode);
        removeNode(node);
        node.mFreq++;
        addNode(node);

        updateTree(node.mParent);
    }

    private void updateChildCode(Node node) {
        codeToChar.put(node.mCode, node.mChar);
        if(node.mLeft != null && node.mRight != null) {
            node.mLeft.mCode = node.mCode + "0";
            node.mRight.mCode = node.mCode + "1";

            removeNode(node.mLeft);
            removeNode(node.mRight);
            addNode(node.mLeft);
            addNode(node.mRight);

            updateChildCode(node.mLeft);
            updateChildCode(node.mRight);
        }
    }

    private Node checkSwap(Node node) {
        Set<Node> currentBlock = block.get(node.mFreq);

        Node swapNode = new Node(null, 0, null, -100, null);
        if(currentBlock == null || node.equals(root)) return swapNode;

        for(Node n : currentBlock) {
            if(n.mNumber > swapNode.mNumber && !node.mParent.equals(n)) swapNode = n;
        }

        return swapNode;
    }

    private void swap(Node node, Node swapNode) {
        removeNode(node);
        removeNode(swapNode);

        Node nodeParent = node.mParent;
        Node swapNodeParent = swapNode.mParent;

        if(nodeParent.equals(swapNodeParent)) {
            if(node.equals(nodeParent.mLeft)) {
                nodeParent.mLeft = swapNode;
                nodeParent.mRight = node;
            } else {
                nodeParent.mLeft = node;
                nodeParent.mRight = swapNode;
            }
        }

        else {
            if (swapNode.equals(swapNodeParent.mLeft)) swapNodeParent.mLeft = node;
            else swapNodeParent.mRight = node;

            if (node.equals(nodeParent.mLeft)) nodeParent.mLeft = swapNode;
            else nodeParent.mRight = swapNode;
        }

        Node temp = new Node(node);
        node.mParent = swapNode.mParent;
        node.mNumber = swapNode.mNumber;
        node.mCode = swapNode.mCode;

        swapNode.mParent = temp.mParent;
        swapNode.mNumber = temp.mNumber;
        swapNode.mCode = temp.mCode;

        addNode(node);
        addNode(swapNode);
    }

    Character getCharOf(String code) {
        if(codeToChar.containsKey(code) && codeToChar.get(code) != null) return codeToChar.get(code);
        return null;
    }
}
