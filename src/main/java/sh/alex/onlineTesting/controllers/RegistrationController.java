package sh.alex.onlineTesting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sh.alex.onlineTesting.model.entities.UserEntity;
import sh.alex.onlineTesting.model.exeptions.UserAlreadyExistsException;
import sh.alex.onlineTesting.model.services.interfaces.UserEntityService;

/**
 * Контроллер для обработки регистрации пользователей.
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserEntityService userEntityService;

    /**
     * Конструктор для инициализации сервиса UserEntityService.
     *
     * @param userEntityService сервис для работы с пользователями
     */
    @Autowired
    public RegistrationController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    /**
     * Обработчик GET-запроса для отображения формы регистрации.
     *
     * @param model объект для передачи данных в представление
     * @return имя представления для отображения формы регистрации
     */
    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "users/registration";
    }

    /**
     * Обработчик POST-запроса для регистрации нового пользователя.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @param model    объект для передачи данных в представление
     * @return имя представления для отображения результата регистрации или перенаправления на страницу входа
     */
    @PostMapping
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        try {
            userEntityService.create(username, password);
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            model.addAttribute("user", new UserEntity());
            return "registration";
        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка при регистрации");
            model.addAttribute("user", new UserEntity());
            return "registration";
        }
        return "redirect:/login";
    }
}

