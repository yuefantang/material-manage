package com.dongyu.test;

import com.dongyu.company.MaterialWebApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 单元测试启动类
 *
 * @author TYF
 * @date 2019/5/7
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MaterialWebApplication.class)
@SpringBootConfiguration
public class MaterialWebTestApplication {
}
