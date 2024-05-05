package sh.alex.onlineTesting.controllers;

import lombok.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sh.alex.onlineTesting.model.entities.Answer;
import sh.alex.onlineTesting.model.entities.Question;
import sh.alex.onlineTesting.model.services.interfaces.AnswerService;
import sh.alex.onlineTesting.model.services.interfaces.QuestionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с вопросами и ответами.
 */
@Controller
@RequestMapping("/qa")
public class QAController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * Конструктор для инъекции зависимостей QuestionService и AnswerService.
     *
     * @param questionService Сервис для работы с вопросами.
     * @param answerService   Сервис для работы с ответами.
     */
    public QAController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    /**
     * Обрабатывает GET-запросы на "/qa" и отображает список вопросов с ответами.
     *
     * @param model Объект Model для передачи данных в представление.
     * @return Имя представления ("qa/list") для отображения списка вопросов и ответов.
     */
    @GetMapping
    public String listQuestionsAndAnswers(Model model) {
        List<Question> questions = questionService.getAll();
        List<QuestionWithAnswers> questionsWithAnswers = new ArrayList<>();
        for (Question question : questions) {
            QuestionWithAnswers qwa = new QuestionWithAnswers(question, answerService.getByQuestionId(question.getId()));
            questionsWithAnswers.add(qwa);
        }
        model.addAttribute("questionsWithAnswers", questionsWithAnswers);
        return "qa/list";
    }

    /**
     * Обрабатывает GET-запросы на "/qa/add" и отображает форму для добавления нового вопроса.
     *
     * @param model Объект Model для передачи данных в представление.
     * @return Имя представления ("qa/add-question") для отображения формы добавления вопроса.
     */
    @GetMapping("/add")
    public String showAddQuestionForm(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("answers", createEmptyAnswers());
        return "qa/add-question";
    }

    /**
     * Обрабатывает POST-запросы на "/qa/add" для создания нового вопроса с ответами.
     *
     * @param questionText Текст вопроса.
     * @param answer1      Текст ответа 1.
     * @param answer2      Текст ответа 2.
     * @param answer3      Текст ответа 3.
     * @param answer4      Текст ответа 4.
     * @param isCorrect1   Флаг, указывающий, является ли ответ 1 правильным.
     * @param isCorrect2   Флаг, указывающий, является ли ответ 2 правильным.
     * @param isCorrect3   Флаг, указывающий, является ли ответ 3 правильным.
     * @param isCorrect4   Флаг, указывающий, является ли ответ 4 правильным.
     * @return Перенаправление на "/qa" после успешного создания вопроса и ответов.
     */
    @PostMapping("/add")
    public String addQuestion(@RequestParam("questionText") String questionText,
                              @RequestParam("answerText_1") String answer1,
                              @RequestParam("answerText_2") String answer2,
                              @RequestParam("answerText_3") String answer3,
                              @RequestParam("answerText_4") String answer4,
                              @RequestParam(value = "isCorrect_1", defaultValue = "false") Boolean isCorrect1,
                              @RequestParam(value = "isCorrect_2", defaultValue = "false") Boolean isCorrect2,
                              @RequestParam(value = "isCorrect_3", defaultValue = "false") Boolean isCorrect3,
                              @RequestParam(value = "isCorrect_4", defaultValue = "false") Boolean isCorrect4) {

        // Проверка уникальности текста вопроса
        if (!questionService.isQuestionTextUnique(questionText)) {
            return "redirect:/qa?unUniqError";
        }

        // Проверка наличия хотя бы одного правильного ответа
        if (isCorrect1 == isCorrect2 == isCorrect3 == isCorrect4) {
            return "redirect:/qa?answerError";
        }

        // Создание нового вопроса
        Question question = questionService.create(questionText);

        // Создание ответов для нового вопроса
        answerService.create(new Answer(answer1, isCorrect1 != null && isCorrect1), question.getId());
        answerService.create(new Answer(answer2, isCorrect2 != null && isCorrect2), question.getId());
        answerService.create(new Answer(answer3, isCorrect3 != null && isCorrect3), question.getId());
        answerService.create(new Answer(answer4, isCorrect4 != null && isCorrect4), question.getId());

        return "redirect:/qa";
    }

    /**
     * Обрабатывает GET-запросы на "/qa/edit/{id}" и отображает форму для редактирования вопроса.
     *
     * @param id    Идентификатор вопроса, который нужно отредактировать.
     * @param model Объект Model для передачи данных в представление.
     * @return Имя представления ("qa/edit-question") для отображения формы редактирования вопроса.
     */
    @GetMapping("/edit/{id}")
    public String showEditQuestionForm(@PathVariable Long id, Model model) {
        Question question = questionService.getById(id);
        List<Answer> answers = answerService.getByQuestionId(id);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "qa/edit-question";
    }

    /**
     * Создает список объектов Answer на основе списков текстов ответов и флагов, указывающих, является ли ответ правильным.
     *
     * @param answerTexts   список текстов ответов
     * @param isCorrectList список флагов, указывающих, является ли ответ правильным
     * @param questionId    идентификатор вопроса, к которому относятся ответы
     * @return список объектов Answer
     */
    private List<Answer> createAnswers(List<String> answerTexts, List<Boolean> isCorrectList, Long questionId) {
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < answerTexts.size(); i++) {
            String answerText = answerTexts.get(i);
            Boolean isCorrect = isCorrectList.size() > i ? isCorrectList.get(i) : false;
            Answer answer = new Answer(answerText, isCorrect);
            answer.setQuestionId(questionId);
            answers.add(answer);
        }
        return answers;
    }

    /**
     * Создает список из четырех пустых объектов Answer.
     *
     * @return список из четырех пустых объектов Answer
     */
    private List<Answer> createEmptyAnswers() {
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            answers.add(new Answer());
        }
        return answers;
    }


    /**
     * Вложенный статический класс, представляющий вопрос с сопутствующими ответами.
     * Используется для объединения данных Question и списка Answer в одном объекте.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    private static class QuestionWithAnswers {
        private Question question;
        private List<Answer> answers;
    }
}


