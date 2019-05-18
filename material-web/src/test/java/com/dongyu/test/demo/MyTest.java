package com.dongyu.test.demo;

import com.dongyu.company.finance.domain.MiPrice;
import com.dongyu.test.MaterialWebTestApplication;
import org.junit.Test;

import java.lang.reflect.Field;
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
        MiPrice miPrice = new MiPrice();
        printFieldMessage(miPrice);
    }
    private static void printFieldMessage(Object object) {
        // 要获取类的信息，首先要获取类的类类型
        Class<?> class1 = object.getClass();// 传递的是哪个子类的对象，class1就是该子类的类类型
        // 获取类的名称
        System.out.println("类的名称是：" + class1.getName());
        /**
         * 成员变量也是对象 java.lang.reflect.Field Field类封装了关于成员变量的操作
         * getFields()方法获取的是所有的public的成员变量的信息
         * getDeclaredFields获取的是该类自己声明的成员变量的信息
         */
        // Field[] fs = class1.getFields();
        Field[] fs = class1.getDeclaredFields();
        for (Field field : fs) {
            // 得到成员变量的类型的类类型
            Class<?> filedType = field.getType();
            String typeName = filedType.getName();
            String fieldName = field.getName();
            System.out.println(typeName + " " + fieldName);
        }
    }
}
