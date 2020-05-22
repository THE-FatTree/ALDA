package aufgabe1;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class TUI {

    private static Dictionary<String, String> dic;

    public static void main (String[] args) throws Exception {

        System.out.println("Welcome to the ricefields motherfucker");

        Scanner scanner = new Scanner(System.in);
        do {
            String input = scanner.nextLine();
            commands(input);
        } while (true);
    }

    private static void commands(String command) throws Exception {

        String args[] = command.split(" ");

        switch (args[0]) {
            case "create":
                create(args);
                break;
            case "read":
                read();
                break;
            case "p":
                print();
                break;
            case "s":
                search(args);
                break;
            case "i":
                insert(args);
                break;
            case "r":
                remove(args);
                break;
            case "t":
                test();
                break;
            case "exit":
                System.exit(0);
                break;
        }
    }

    private static void create(String[] args) {
        System.out.println("Creating new dictionary");
        if (args[1].equals("Hash"))
            dic = new HashDictionary<>();

        else if (args[1].equals("Binary"))
            dic = new BinaryTreeDictionary<>();

        else
            dic = new SortedArrayDictionary<>();
    }

    private static void print() {
        if (dic == null)
            System.out.println("Use 'create' to create your first Dictionary!");

        for (Dictionary.Entry<String, String> v : dic)
            System.out.println(v.getKey() + ": " + v.getValue());
    }

    private static void search(String[] args) {
        if (dic.search(args[1]) != null)
            System.out.println(dic.search(args[1]));
        else
            System.out.println("Wort wurde nicht gefunden!");
    }

    private static void insert(String[] args) {
        System.out.printf("Adding %s: %s to the Dictionary\n", args[1], args[2]);
        dic.insert(args[1], args[2]);
    }

    private static void remove(String[] args) {
        System.out.printf("Removing %s from Dictionary\n", args[1]);
        dic.remove(args[1]);
    }

    private static void read() {

    }

    private static void test() {

    }
}
