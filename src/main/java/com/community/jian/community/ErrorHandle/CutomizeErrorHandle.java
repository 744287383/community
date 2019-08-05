package com.community.jian.community.ErrorHandle;

import com.community.jian.community.dto.ResultDTO;
import com.community.jian.community.exception.ApplicationErrorMessage;
import com.community.jian.community.exception.CommentException;
import com.community.jian.community.exception.ServiceException;
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

    @ExceptionHandler(CommentException.class)
    @ResponseBody
    ResultDTO commentHandeException(HttpServletRequest request, CommentException e, Model model) {
        e.printStackTrace();

        return ResultDTO.errorOf(e);
    }
    @ExceptionHandler(ServiceException.class)
    ModelAndView serviceHandeException(HttpServletRequest request, ServiceException e, Model model) {
        e.printStackTrace();
        model.addAttribute("message", e.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable e, Model model) {
        e.printStackTrace();
        model.addAttribute("message", ApplicationErrorMessage.SERVICE_HOT.getMessage());
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
