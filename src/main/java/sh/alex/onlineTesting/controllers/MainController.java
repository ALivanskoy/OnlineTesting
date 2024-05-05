package sh.alex.onlineTesting.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для обработки основного маршрута приложения.
 */
@Controller
public class MainController {

    /**
     * Обрабатывает GET-запросы на корневой путь ("/").
     *
     * @param model Объект Model, используемый для передачи данных в представление.
     * @return Имя представления ("index") для отображения.
     */
    @RequestMapping("/")
    public String getAboutPage(Model model) {
        // Получение информации об аутентификации пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        // Добавление атрибута в модель для использования в представлении
        model.addAttribute("isAuthenticated", isAuthenticated);

        // Возвращение имени представления для отображения
        return "index";
    }
}
