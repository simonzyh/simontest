package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by yehua.zyh on 2016/12/27.
 */
public class SearchString {

    public static void main(String[] args) {
        File f = new File("D:\\workspace\\lpadmin");
        try {
            search(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void search(File f) throws Exception {
        if (f.isDirectory()) {
            File[] childFils = f.listFiles();
            for (File child : childFils) {
                search(child);
            }
        } else {
            if (!f.getName().endsWith("java")) {
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "gbk"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("queryServiceClient")) {
                    System.out.println(line + " " + f.getPath());
                }
            }
        }


    }
}
