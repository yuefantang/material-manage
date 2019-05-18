package com.dongyu.test.demo;

import com.dongyu.test.MaterialWebTestApplication;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 * @author TYF
 * @date 2019/5/7
 * @since 1.0.0
 */
public class MyTest extends MaterialWebTestApplication {

    @Test
    public void collectionTest(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = dateFormat.format(new Date());
        System.out.println(s);

        String regex = "";
        regex = "^(?=.*(00|11|22|33|44|55|66|77|88|99))[0-9]{11}$";
        if(Pattern.matches(regex,"15185441272")){
            System.out.println("匹配");
        }
    }

    @Test
    public void dateTest(){
       // isValidDate("2019-04-22");
        String str="20190422231617";
        //str="2019-04-22 23:16:17";
        String substring = str.substring(0, 4);
       // System.out.println(substring);

        String a1 = "[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}";//yyyyMMddHHmmss
        String a2 = "[0-9]{4}[0-9]{2}[0-9]{2}";//yyyyMMdd
        boolean date = Pattern.compile(a1).matcher(str).matches();
        boolean date1 = Pattern.compile(a2).matcher(str).matches();
        if(date||date1) {
            str = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6,8);
        }
        System.out.println(str);
    }

    private void isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式yyyyMMdd
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false.
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            System.out.println("格式错误");
        }
    }

    @Test
    public void listTest(){
List<String> l1 = new ArrayList<>();
l1.add("a");
l1.add("a");
l1.add("c");
l1.add("c");
List<String> l2 = new ArrayList<String>();
l2.add("b");
l2.add("b");
l2.add("k");
l2.add("k");

l1.removeAll(l2);//此处指的是将与l2重复的删除
l1.addAll(l2);//此处指加上l2

        for(String str : l1){
            System.out.println(str);
        }


    }
}
