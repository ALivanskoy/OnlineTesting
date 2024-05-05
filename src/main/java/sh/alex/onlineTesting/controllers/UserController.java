package sh.alex.onlineTesting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sh.alex.onlineTesting.model.entities.UserEntity;
import sh.alex.onlineTesting.model.repository.UserRepository;

import java.util.List;

/**
 * Контроллер для управления пользователями.
 */
@Controller
public class UserController {

    private final UserRepository userRepository;

    /**
     * Конструктор для инициализации репозитория UserRepository.
     *
     * @param userRepository репозиторий для работы с пользователями
     */
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Обработчик GET-запроса для отображения списка пользователей.
     *
     * @param currentUser объект, представляющий текущего аутентифицированного пользователя
     * @param model       объект для передачи данных в представление
     * @return имя представления для отображения списка пользователей
     */
    @GetMapping("/users")
    public String showUserList(@AuthenticationPrincipal UserEntity currentUser, Model model) {
        List<UserEntity> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        return "users/users";
    }
}
