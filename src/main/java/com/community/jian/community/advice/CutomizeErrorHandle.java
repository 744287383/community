package com.community.jian.community.advice;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice//此处可以添加处理哪个类等等的参数
public class CutomizeErrorHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ModelAndView handleControllerException(HttpServletRequest request, Throwable e, Model model) {
        e.printStackTrace();
        HttpStatus status = getStatus(request);
        if (e instanceof RuntimeException){
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","服务器太热，要不然稍后再访问！！！");
        }

        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }



}
