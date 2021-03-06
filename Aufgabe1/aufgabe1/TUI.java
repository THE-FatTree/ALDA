package aufgabe1;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class TUI {

    private static Dictionary<String, String> dic;

    public static void main (String[] args) throws Exception {

        System.out.println("Welcome");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            commands(input);
        }
    }

    private static void commands(String command) {

        String[] args= command.split(" ");

        switch (args[0]) {
            case "create":
                if (args.length > 1)
                    create(args);
                else
                    createE(args);
                break;
            case "read":
                if(args.length > 1)
                    read(Integer.parseInt(args[1]));
                else
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
                if(args.length > 1)
                    testruntime(Integer.parseInt(args[1]));
                else
                    testruntime();
                break;
            case "exit":
                System.exit(0);
                break;
        }
    }

    private static void create(String[] args) {
        System.out.printf("Creating new %s dictionary\n", args[1]);
        if (args[1].equals("Hash"))
            dic = new HashDictionary<>();

        else if (args[1].equals("Binary"))
            dic = new BinaryTreeDictionary<>();

        else
            dic = new SortedArrayDictionary<>();
    }

    private static void createE(String[] args) {
        dic = new SortedArrayDictionary<>();
        System.out.println("Creating new Sorted Array dictionary");
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

    private static void read(int n) {
        int counter = 0;
        File selectedFile;
        String line;
        JFileChooser chooseRead = new JFileChooser();
        chooseRead.setCurrentDirectory(new File("ALDA"));
        if (chooseRead.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooseRead.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        } else
            return;

        FileReader in;
        long start = 0;
        long end = 0;

        try {
            in = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(in);
            start = System.nanoTime();
            while ((line = br.readLine()) != null && counter < n) {
                String[] words = line.split(" ");
                dic.insert(words[0], words[1]);
                counter++;
            }
            end = System.nanoTime();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long res = end - start;
        System.out.println("Read took " + (res) + "ns");
    }

    private static void read() {
        File selectedFile;
        String line;
        JFileChooser chooseRead = new JFileChooser();
        chooseRead.setCurrentDirectory(new File("ALDA"));
        int rv = chooseRead.showOpenDialog(null);
        if (rv == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooseRead.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        } else
            return;

        FileReader in;
        long start = 0;
        long end = 0;

        try {
            in = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(in);
            start = System.nanoTime();
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                dic.insert(words[0], words[1]);
            }
            end = System.nanoTime();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long res = end - start;
        System.out.println("Read took " + (res) + "ns");
    }

    private static void testruntime(int n) {
        int counter = 0;

        File selectedFile;
        String line;
        List<String> german = new LinkedList<>();
        List<String> british = new LinkedList<>();

        JFileChooser chooseTest = new JFileChooser();
        chooseTest.setCurrentDirectory(new File("ALDA"));
        int rv = chooseTest.showOpenDialog(null);
        if (rv == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooseTest.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        } else
            return;

        FileReader in;
        long startG;
        long endG;
        long startE;
        long endE;
        long timeG = 0;
        long timeE = 0;

        try {
            in = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(in);
            while ((line = br.readLine()) != null && counter < n) {
                String[] words = line.split(" ");
                german.add(words[0]);
                british.add(words[1]);
                dic.insert(words[0], words[1]);
                counter++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListIterator<String> G = german.listIterator();

        while (G.hasNext()) {
            startG = System.nanoTime();
            dic.search(G.next());
            endG = System.nanoTime();
            timeG += (endG - startG);
        }

        System.out.println("Search time for german words: " + (timeG) + "ns");

        ListIterator<String> E = british.listIterator();

        while (E.hasNext()) {
            startE = System.nanoTime();
            dic.search(E.next());
            endE = System.nanoTime();
            timeE += (endE - startE);
        }

        System.out.println("Search time for british words: " + (timeE) + "ns");
    }

    private static void testruntime() {

        File selectedFile;
        String line;
        List<String> german = new LinkedList<>();
        List<String> british = new LinkedList<>();

        JFileChooser chooseTest = new JFileChooser();
        chooseTest.setCurrentDirectory(new File("ALDA"));
        int rv = chooseTest.showOpenDialog(null);
        if (rv == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooseTest.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        } else
            return;

        FileReader in;
        long startG;
        long endG;
        long startE;
        long endE;
        long timeG = 0;
        long timeE = 0;

        try {
            in = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(in);
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                german.add(words[0]);
                british.add(words[1]);
                dic.insert(words[0], words[1]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListIterator<String> G = german.listIterator();

        while (G.hasNext()) {
            startG = System.nanoTime();
            dic.search(G.next());
            endG = System.nanoTime();
            timeG += (endG - startG);
        }

        System.out.println("Search time for german words: " + (timeG) + "ns");

        ListIterator<String> E = british.listIterator();

        while (E.hasNext()) {
            startE = System.nanoTime();
            dic.search(E.next());
            endE = System.nanoTime();
            timeE += (endE - startE);
        }

        System.out.println("Search time for british words: " + (timeE) + "ns");
    }

}
