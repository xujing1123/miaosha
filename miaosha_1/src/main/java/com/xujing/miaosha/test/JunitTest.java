package com.xujing.miaosha.test;


import com.xujing.miaosha.Miaosha1Application;
import com.xujing.miaosha.service.GoodsService;
import com.xujing.miaosha.vo.GoodsVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = Miaosha1Application.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class JunitTest {

    @Autowired
    GoodsService goodsService;


    @Test
    public void over(){
        GoodsVo goods = new GoodsVo();
        goods.setId(2L);
        boolean isSuccess = goodsService.reduceStock(goods);
        System.out.println(isSuccess  );
    }

}
