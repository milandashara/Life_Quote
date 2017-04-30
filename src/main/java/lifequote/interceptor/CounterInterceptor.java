package lifequote.interceptor;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by milanashara on 2/12/17.
 */

public class CounterInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private CounterService counterService;

    //before the actual handler will be executed
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

//        long startTime = System.currentTimeMillis();
//        request.setAttribute("startTime", startTime);
        counterService.increment("counter.calls.preHandle."+request.getRequestURL());
        return true;
    }

    //after the handler is executed
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView)
            throws Exception {

        counterService.increment("counter.calls.postHandle."+request.getRequestURL());
        //log it

    }
}
