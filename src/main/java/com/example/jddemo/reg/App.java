package com.example.jddemo.reg;

import java.text.ParseException;

public class App {



    public static void main(String[] args) throws ParseException {

        String num="12345678";
        boolean number  = isNumber(num, 2);


    }

    /**
     *   数字类型 保留两位小数
     * @param str    校验的字符串
     * @param scale  保留小数位
     * @return
     */
    public static boolean isNumber(String str,int  scale){

        String reg="^(\\d{1,8})(\\.\\d{1," + scale + "})?$";
        return str.matches(reg);

    }

    public static void checkQQ(){
        String qq = "2345664";

        String regex = "^[1-9]\\d{4,14}";

        boolean flag = qq.matches(regex);
        if(flag)
            System.out.println(qq+"...is ok");
        else
            System.out.println(qq+"... 不合法");

    }
}
