package sh.alex.onlineTesting.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Контроллер для обработки ошибок и исключений в приложении
 */
@Controller
@ControllerAdvice
public class CustomErrorController implements ErrorController {

    /**
     * Константа, определяющая путь для обработки ошибок.
     */
    private static final String ERROR_PATH = "/error";

    /**
     * Обрабатывает GET-запросы на путь "/error" и перенаправляет их на представление "error.html".
     *
     * @return ModelAndView для перенаправления на представление "error.html".
     */
    @GetMapping(ERROR_PATH)
    public ModelAndView handleError() {
        return new ModelAndView("forward:/error");
    }

    /**
     * Обрабатывает все исключения (Exception.class) в приложении и возвращает строку с текстом ошибки.
     *
     * @param e Объект Exception, содержащий информацию об исключении.
     * @return Строка с текстом ошибки и сообщением об исключении.
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        return "Error: " + e.getMessage();
    }
}
