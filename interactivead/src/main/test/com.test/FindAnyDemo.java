package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindAnyDemo {
    public static void main(String[]argss){
       int i =1;
       for (int j = 0;j<10;j++){
           System.out.println(j);
           if (j<5){
               System.out.println(j);
           }

       }



        /* List list = new ArrayList();
        list.add(7);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        for (int i=1;i<20;i++){
            Optional optional = list.stream().findFirst();
            Optional optional2 = list.stream().findAny();
            System.out.println(optional.get().toString());
            System.out.println(optional2.get().toString());
        }*/
       /* Optional optional = list.stream().findFirst();
        Optional optional2 = list.stream().findAny();
        System.out.println(optional.get().toString());
        System.out.println(optional2.get().toString());*/
    }
}
