package parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PatternParser {
    final int mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0;
    String patPath = "/tmp/pattern.ser";
    List<String> patterns;

    public PatternParser(String patternFilePath) {

        patterns = new ArrayList<>();

        try {
            // TODO:  read the patterns from pattern file
            BufferedReader br = new BufferedReader (new FileReader (patternFilePath));
            String line;
            while( (line = br.readLine() ) != null) {
                patterns.add(switchPatCode(line));
            }


            FileOutputStream fileOut =
                    new FileOutputStream(patPath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(patterns);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in "+ patPath);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private String switchPatCode(String origin) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < origin.length(); i++) {
            int code;
            switch (origin.charAt(i)) {
                case 'A':
                case 'a':
                    code = mADD;
                    break;
                case 'U':
                case 'u':
                    code = mUPDATE;
                    break;
                case 'D':
                case 'd':
                    code = mDELETE;
                    break;
                case 'F':
                case 'f':
                    code = mFIND;
                    break;
                default:
                    code = mUNKNOWN;
                    break;
            }
            res.append(code);
        }

        return res.toString();
    }

    public List<String> getPatterns() {
        try {
            FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            patterns = (List<String>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }

        System.out.println("Deserialized patterns...");
        return patterns;
    }
}
