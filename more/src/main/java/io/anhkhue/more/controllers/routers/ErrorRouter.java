package io.anhkhue.more.controllers.routers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorRouter implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView errorPage(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                request.setAttribute("ERROR_MESSAGE", "Chúng tôi không thể tìm thấy trang bạn yêu cầu!");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                request.setAttribute("ERROR_MESSAGE", "Đã xảy ra lỗi ngoài ý muốn!");
            }
        }

        return new ModelAndView("/errors/error");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
