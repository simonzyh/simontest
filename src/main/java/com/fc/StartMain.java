/**
 * @Title: StartMain.java
 * @Package: com.fc
 * @Description: TODO
 * @author 寮犱笟鍗�
 * @date 2014骞�鏈�9鏃�涓嬪崍12:09:22
 * @version 1.3.1
 */


package com.fc;

import com.fc.core.Task1;
import com.fc.core.Task2;
import com.fc.dataload.DateLoad;

import java.io.IOException;


/**
 * @author 寮犱笟鍗�
 * @version V1.3.1
 * @Description
 * @date 2014骞�鏈�9鏃�涓嬪崍12:09:22
 */

public class StartMain {

    /**
     * @param args
     * @throws Exception
     * @throws IOException
     * @Description
     * @author 寮犱笟鍗�
     */
    public static void main(String[] args) throws IOException, Exception {

        DateLoad.load();
        Task1.run();
        Task2.run();
        //Task3.run();


    }

}
