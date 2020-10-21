package com.lazyhippos.todolistapp.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest httpServletRequest, Exception exception) throws Exception{
        if(AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class)
        != null){
            throw exception;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("url", httpServletRequest.getRequestURL());
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        return modelAndView;
    }
}
