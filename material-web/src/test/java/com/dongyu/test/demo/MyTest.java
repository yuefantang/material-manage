package com.dongyu.test.demo;

import com.dongyu.test.MaterialWebTestApplication;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
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
}
