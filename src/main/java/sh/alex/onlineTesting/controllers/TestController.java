package sh.alex.onlineTesting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sh.alex.onlineTesting.model.entities.Answer;
import sh.alex.onlineTesting.model.entities.Question;
import sh.alex.onlineTesting.model.entities.Test;
import sh.alex.onlineTesting.model.entities.TestResults;
import sh.alex.onlineTesting.model.services.implementation.TestServiceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для управления тестом и результатами.
 */
@Controller
public class TestController {

    private final TestServiceImp testService;

    /**
     * Конструктор для инициализации сервиса TestServiceImp.
     *
     * @param testService сервис для работы с тестами и результатами
     */
    @Autowired
    public TestController(TestServiceImp testService) {
        this.testService = testService;
    }

    /**
     * Обработчик GET-запроса для отображения страницы теста.
     *
     * @param model объект для передачи данных в представление
     * @return имя представления для отображения страницы теста или страницы ошибки
     */
    @GetMapping("/test")
    public String showTestPage(Model model) {
        Test test = testService.create();
        if (test == null) {
            model.addAttribute("error", "В базе данных нет вопросов для теста.");
            return "error";
        }

        model.addAttribute("test", test);
        return "test/test";
    }

    /**
     * Обработчик POST-запроса для сохранения результатов теста.
     *
     * @param model   объект для передачи данных в представление
     * @param answers Map, содержащая ответы пользователя на вопросы теста
     * @return имя представления для отображения результатов теста
     */
    @PostMapping("/test/submit")
    public String submitTestResults(Model model, @RequestParam Map<String, String> answers) {
        int rightAnswers = 0;
        Test test = testService.create();

        // Подсчет правильных ответов
        for (Map.Entry<Question, List<Answer>> entry : test.getQuestions().entrySet()) {
            Question question = entry.getKey();
            List<Answer> questionAnswers = entry.getValue();
            String userAnswer = answers.get(question.getText());

            if (userAnswer != null) {
                for (Answer answer : questionAnswers) {
                    if (answer.getText().equals(userAnswer) && answer.isCorrect()) {
                        rightAnswers++;
                        break;
                    }
                }
            }
        }

        // Получение имени пользователя из контекста безопасности
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Создание и сохранение объекта TestResults
        TestResults testResults = new TestResults();
        testResults.setUsername(username);
        testResults.setLocalDateTime(LocalDateTime.now());
        testResults.setRightAnswers((byte) rightAnswers);
        testService.saveResults(testResults);

        model.addAttribute("rightAnswers", rightAnswers);
        return "test/test-result";
    }

    /**
     * Обработчик GET-запроса для отображения страницы с результатами тестов.
     *
     * @param model объект для передачи данных в представление
     * @return имя представления для отображения страницы с результатами тестов
     */
    @GetMapping("/results")
    public String showResultsPage(Model model) {
        List<TestResults> results = testService.getResults();
        model.addAttribute("results", results);

        return "test/results";
    }
}
