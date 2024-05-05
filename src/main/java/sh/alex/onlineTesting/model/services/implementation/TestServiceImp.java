package sh.alex.onlineTesting.model.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sh.alex.onlineTesting.model.entities.Answer;
import sh.alex.onlineTesting.model.entities.Question;
import sh.alex.onlineTesting.model.entities.Test;
import sh.alex.onlineTesting.model.entities.TestResults;
import sh.alex.onlineTesting.model.repository.ResultRepository;
import sh.alex.onlineTesting.model.services.interfaces.AnswerService;
import sh.alex.onlineTesting.model.services.interfaces.QuestionService;
import sh.alex.onlineTesting.model.services.interfaces.TestService;
import java.util.List;

/**
 * Реализация сервиса для работы с тестами и результатами тестирования.
 */
@Service
public class TestServiceImp implements TestService {

    private final ResultRepository repository;
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param repository      репозиторий для работы с результатами тестирования
     * @param questionService сервис для работы с вопросами
     * @param answerService   сервис для работы с ответами
     */
    @Autowired
    public TestServiceImp(ResultRepository repository, QuestionService questionService, AnswerService answerService) {
        this.repository = repository;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    /**
     * Метод для создания нового теста.
     *
     * @return созданный тест
     */
    @Override
    public Test create() {
        long questionQuantity = questionService.getDbSize();

        if (questionQuantity == 0) return null;

        List<Question> questions;
        List<Answer> answers;
        Test test = new Test();

        if (questionQuantity <= 10) {
            questions = questionService.getRandomQuestions((int) questionQuantity);
        } else questions = questionService.getRandomQuestions(10);

        for (Question question : questions) {
            answers = answerService.getByQuestionId(question.getId());
            test.addQuestion(question, answers);
        }

        return test;
    }

    /**
     * Метод для получения списка всех результатов тестирования.
     *
     * @return список результатов тестирования
     */
    @Override
    @Transactional
    public List<TestResults> getResults() {
        return repository.findAll();
    }

    /**
     * Метод для сохранения результатов тестирования.
     *
     * @param testResults результаты тестирования
     */
    @Override
    @Transactional
    public void saveResults(TestResults testResults) {
        repository.save(testResults);
    }

    /**
     * Метод для получения списка результатов тестирования по имени пользователя.
     *
     * @param username имя пользователя
     * @return список результатов тестирования пользователя
     */
    @Override
    public List<TestResults> getResultsByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}

