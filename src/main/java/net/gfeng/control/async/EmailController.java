package net.gfeng.control.async;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//异步调用
//除了异步请求，一般上我们用的比较多的应该是异步调用。通常在开发过程中，会遇到一个方法是和实际业务无关的，没有紧密性的。比如记录日志信息等业务。这个时候正常就是启一个新线程去做一些业务处理，让主线程异步的执行其他业务。
//需要在启动类加入@EnableAsync使异步调用@Async注解生效
//在需要异步执行的方法上加入此注解即可@Async("threadPool"),threadPool为自定义线程池。


@Controller
@EnableAsync
@RequestMapping("/app")
public class EmailController {

    //获取ApplicationContext对象方式有多种,这种最简单,其它的大家自行了解一下
    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/email/asyncCall", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> asyncCall () {
    	System.out.println("外部线程：" + Thread.currentThread().getName());
    	
        Map<String, Object> resMap = new HashMap<String, Object>();
        try{
            //这样调用同类下的异步方法是不起作用的
            //this.testAsyncTask();
            //通过上下文获取自己的代理对象调用异步方法
            EmailController emailController = (EmailController)applicationContext.getBean(EmailController.class);
            emailController.testAsyncTask();
        	
            resMap.put("code",200);
        }catch (Exception e) {
            resMap.put("code",400);
            System.err.println(e);
        }
        return resMap;
    }

    //注意一定是public,且是非static方法
    @Async()
    public void testAsyncTask() throws InterruptedException {
    	System.out.println("内部线程：" + Thread.currentThread().getName());
        Thread.sleep(10000);
        System.out.println("异步任务执行完成！");
    }

}