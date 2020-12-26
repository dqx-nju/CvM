package org.cvm.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PlayFile {
    List<String> list = new ArrayList<>();

    public void addStatement(String statement) {
        list.add(statement);
    }

    public void save_file(String filepath) {
        FileWriter fw = null;
        try {
            File f = new File(filepath);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        for (int i = 0; i < list.size(); i++) {
            pw.println(list.get(i));
        }
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
