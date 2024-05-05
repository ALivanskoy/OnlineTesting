package sh.alex.onlineTesting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sh.alex.onlineTesting.model.entities.UserEntity;
import sh.alex.onlineTesting.model.exeptions.UserNotFoundException;
import sh.alex.onlineTesting.model.services.interfaces.UserEntityService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Контроллер для обработки запросов, связанных с профилем пользователя.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserEntityService userEntityService;

    /**
     * Обрабатывает GET-запросы на "/profile" и отображает страницу профиля пользователя.
     *
     * @param model Объект Model для передачи данных в представление.
     * @return Имя представления ("users/profile") для отображения страницы профиля.
     */
    @GetMapping
    public String showProfilePage(Model model) {
        // Получение информации об аутентифицированном пользователе
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userEntityService.getByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        model.addAttribute("isModerator", auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR")));

        return "users/profile";
    }

    /**
     * Обрабатывает POST-запросы на "/profile" для обновления пароля пользователя.
     *
     * @param updatedUser Объект UserEntity с обновленными данными пользователя.
     * @return Перенаправление на страницу профиля после обновления пароля.
     */
    @PostMapping
    public String updatePassword(UserEntity updatedUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        userEntityService.update(username, updatedUser);
        return "redirect:/profile";
    }

    /**
     * Обрабатывает POST-запросы на "/profile/assign-roles" для обновления ролей пользователя.
     *
     * @param username Имя пользователя, для которого обновляются роли.
     * @param roles    Список ролей, которые должны быть назначены пользователю.
     * @param model    Объект Model для передачи данных в представление.
     * @return Перенаправление на страницу профиля после обновления ролей.
     */
    @PostMapping("/assign-roles")
    public String updateRoles(@RequestParam("username") String username,
                              @RequestParam("roles") List<String> roles,
                              Model model) {

        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        try {
            userEntityService.updateRoles(username, authorities);
            model.addAttribute("success", "Роли пользователя " + username + " успешно обновлены");
        } catch (UserNotFoundException e) {
            model.addAttribute("error", "Пользователь " + username + " не найден");
        }

        return "redirect:/profile";
    }
}
