package AH;

public class Main {

    public static void main(String[] args) {
        String string = "DSAFAFDJAFSDJSDACNASDNFDS";
        String enc = AdaptiveHuffman.encode(string);
        System.out.println(enc);
        System.out.println(AdaptiveHuffman.decode(enc));
    }
}
