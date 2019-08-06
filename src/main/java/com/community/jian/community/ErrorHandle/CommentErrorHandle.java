package com.community.jian.community.ErrorHandle;

import com.community.jian.community.Controller.CommentContorller;
import com.community.jian.community.dto.ResultDTO;
import com.community.jian.community.exception.ApplicationErrorMessage;
import com.community.jian.community.exception.CommentException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(assignableTypes = {CommentContorller.class})
public class CommentErrorHandle {

    @ExceptionHandler(CommentException.class)
    @ResponseBody
    ResultDTO commentHandeException(HttpServletRequest request, CommentException e, Model model) {
        e.printStackTrace();

        return ResultDTO.errorOf(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResultDTO handleControllerException(HttpServletRequest request, Throwable e, Model model) {
        e.printStackTrace();
       return ResultDTO.errorOf(ApplicationErrorMessage.SERVICE_HOT);

    }


    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
