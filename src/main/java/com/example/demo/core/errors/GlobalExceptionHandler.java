package com.example.demo.core.errors;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e, HttpServletRequest request, Model model) {
        log.error("400 Error: " + e.getMessage());
        model.addAttribute("msg", e.getMessage());
        return "err/400";
    }

    @ExceptionHandler(Exception401.class)
    @ResponseBody
    public String ex401(Exception401 e) {
        log.warn("401 Error: " + e.getMessage());
        String script = """
                    <script>
                        alert('%s');
                        location.href = '/login-form';
                    </script>
                    """.formatted(e.getMessage());
        return script;
    }

    @ExceptionHandler(Exception403.class)
    @ResponseBody
    public String ex403(Exception403 e) {
        log.warn("403 Error: " + e.getMessage());
        String script = """
                    <script>
                        alert('%s');
                        history.back()
                    </script>
                    """.formatted(e.getMessage());
        return script;
    }

    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e, Model model) {
        log.error("404 Error: " + e.getMessage());
        model.addAttribute("msg", e.getMessage());
        return "err/404";
    }
    @ExceptionHandler(Exception500.class)
    public String ex500(Exception500 e, Model model) {
        log.error("500 Error: " + e.getMessage());
        model.addAttribute("msg", e.getMessage());
        return "err/500";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, Model model) {
        log.error("500 Error: " + e.getMessage());
        model.addAttribute("msg", "시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
        return "err/500";
    }
}
