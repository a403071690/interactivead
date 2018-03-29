package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestD {
    public static void main(String[] args){
        String apiHtmlDocPath = "G:/mywork/interactivead/src/main/webapp/static/html/";
        try {

            File file = new File(apiHtmlDocPath);
            if (file.exists()) {
                if (!true) {
                    System.out.println("1");
                    return;
                }
            } else {
                System.out.println("2");
                file.createNewFile();
            }
            System.out.println("3");
            FileOutputStream fos = new FileOutputStream(file);
            String text = "asdasd";
            fos.write(text.getBytes());
            fos.flush();
            fos.close();
            System.out.println("生成 ApiHtmlDoc 成功!");
        }catch (
                Exception e
                ){
            e.printStackTrace();
        }

    }
}
