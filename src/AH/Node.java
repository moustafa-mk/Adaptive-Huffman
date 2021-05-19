package AH;

import java.util.Objects;

class Node {
    Node mParent, mLeft, mRight;
    Character mChar;
    Integer mFreq;
    Integer mNumber;
    String mCode;

    Node() {
        mFreq = mNumber = 0;
        mParent = mLeft = mRight = null;
    }

    Node(Character character, Integer freq, Node parent, Integer number, String code) {
        mChar = character;
        mFreq = freq;
        mParent = parent;
        mNumber = number;
        mCode = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(mLeft, node.mLeft) &&
                Objects.equals(mRight, node.mRight) &&
                Objects.equals(mChar, node.mChar) &&
                Objects.equals(mFreq, node.mFreq) &&
                Objects.equals(mNumber, node.mNumber) &&
                Objects.equals(mCode, node.mCode);
    }

    Node(Node node) {
        mParent = node.mParent;
        mLeft = node.mLeft;
        mRight = node.mRight;
        mChar = node.mChar;
        mFreq = node.mFreq;
        mNumber = node.mNumber;
        mCode = node.mCode;
    }
}
