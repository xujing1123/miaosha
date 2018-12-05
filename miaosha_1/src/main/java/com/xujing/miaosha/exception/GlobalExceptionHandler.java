package com.xujing.miaosha.exception;

import com.xujing.miaosha.result.CodeMsg;
import com.xujing.miaosha.result.Result;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 定义全局异常处理类
 * @ControllerAdvice + @ExceptionHandler 全局处理 Controller 层异常
 *
 *
 * 优点：将 Controller 层的异常和数据校验的异常进行统一处理，减少模板代码，减少编码量，提升扩展性和可维护性。
 * 缺点：只能处理 Controller 层未捕获（往外抛）的异常，对于 Interceptor（拦截器）层的异常，Spring 框架层的异常，就无能为力了。
 * */

/**
 * 默认情况下，@ControllerAdvice方法适用于每个请求（即所有控制器），但您可以通过使用注释上的属性将其缩小到控制器的子集
 * 如：
 * @ControllerAdvice(annotations = LoginController.class)  //只对一个controller起作用
 * @ControllerAdvice("com.xujing.miaosha.controller")      //只一个包下的controller起作用
 * @ControllerAdvice(assignableTypes = {LoginController.class,GoodsController.class}) //对多个controller起作用
 * */
//@RestControllerAdvice  相当于上面两个注释
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 声明异常处理方法
     * 处理Exception及其子类的异常
     * */
    @ExceptionHandler(value=Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();

        if(e instanceof GlobleException) {
            GlobleException ex = (GlobleException) e;
            return Result.error(ex.getCm());
        }else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else {
            return Result.error(CodeMsg.SERVER_ERROE);
        }
    }

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //binder.addValidators(null);
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "Magical Sam");
    }

}
