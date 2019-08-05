package com.community.jian.community.Controller;

import com.community.jian.community.exception.ApplicationErrorMessage;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/error")
public class CutomizeController implements ErrorController {


    @Override
    public String getErrorPath() {
        return "error";
    }
    @RequestMapping(
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = this.getStatus(request);

        ModelAndView modelAndView = new ModelAndView("error");
        if (status.is4xxClientError()){
            modelAndView.addObject("message", ApplicationErrorMessage.REQUEST_ERROR.getMessage());
        }
        if (status.is5xxServerError()){
            modelAndView.addObject("message",ApplicationErrorMessage.SERVICE_HOT.getMessage());
        }

        return modelAndView;
    }
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}
