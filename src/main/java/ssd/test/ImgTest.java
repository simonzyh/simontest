package ssd.test;

import net.coobird.thumbnailator.Thumbnails;

import java.io.*;

public class ImgTest {

    public  static void main(String[] args) throws  Exception {
        InputStream inputStream=new FileInputStream(new File("/Users/zyh/Downloads/1590989289708.jpg"));

        OutputStream swapStream=new FileOutputStream(new File("/Users/zyh/Downloads/1590989289708-1.jpg"));
        Thumbnails.of(inputStream).scale(0.5).outputQuality(0.5).toOutputStream(swapStream);

    }
}
