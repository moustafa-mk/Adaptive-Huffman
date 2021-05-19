package AH;

import javafx.util.Pair;

class AdaptiveHuffman {
    static String encode(String string) {
        Tree tree = new Tree();
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            Character character = string.charAt(i);
            encoded.append(tree.insert(character)).append(' ');
        }
        return encoded.toString();
    }

    static String decode(String string) {
        Pair<String, Integer> pair = getNextBlock(0, string);
        String nextBlock = pair.getKey();
        Integer idx = pair.getValue();
        Character nextBlockChar = (char) Integer.parseInt(nextBlock, 2);
        StringBuilder decoded = new StringBuilder();
        decoded.append(nextBlockChar);
        Tree tree = new Tree();
        tree.insert(nextBlockChar);

        String nextNYT = "0";
        while (idx < string.length()) {
            pair = getNextBlock(idx, string);
            nextBlock = pair.getKey();
            idx = pair.getValue();
            if (nextBlock.equals("")) break;
            if (nextBlock.equals(nextNYT)) {
                nextNYT += '0';
                pair = getNextBlock(idx, string);
                nextBlock = pair.getKey();
                idx = pair.getValue();
                nextBlockChar = (char) Integer.parseInt(nextBlock, 2);
                tree.insert(nextBlockChar);
                decoded.append(nextBlockChar);
            } else {
                nextBlockChar = tree.getCharOf(nextBlock);
                if(nextBlockChar == null) break;
                decoded.append(nextBlockChar);
                tree.insert(nextBlockChar);
            }
        }

        return decoded.toString();
    }

    private static Pair<String, Integer> getNextBlock(Integer idx, String string) {
        if (string.charAt(idx) == ' ') idx++;
        StringBuilder ret = new StringBuilder();
        for (; idx < string.length(); idx++) {
            if (string.charAt(idx) == ' ') break;
            ret.append(string.charAt(idx));
        }
        return new Pair<>(ret.toString(), idx);
    }
}
