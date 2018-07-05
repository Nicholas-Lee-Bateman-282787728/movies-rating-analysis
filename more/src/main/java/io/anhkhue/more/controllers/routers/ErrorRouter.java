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

    @RequestMapping("/errors")
    public ModelAndView errorPage(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                request.setAttribute("ERROR_MESSAGE", "Chúng tôi không thể tìm thấy trang bạn yêu cầu!");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                request.setAttribute("ERROR_MESSAGE", "Đã xảy ra sự cố ngoài ý muốn!");
            } else {
                request.setAttribute("ERROR_MESSAGE", "Hệ thống đang có vẫn đề. Xin chân thành cáo lỗi quý vị.");
            }
        }

        return new ModelAndView("/errors/error");
    }

    @RequestMapping("*/errors/unauthorized")
    public ModelAndView unauthorized() {
        return new ModelAndView("/errors/unauthorized");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
