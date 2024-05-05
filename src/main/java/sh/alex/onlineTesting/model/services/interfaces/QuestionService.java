package sh.alex.onlineTesting.model.services.interfaces;

import sh.alex.onlineTesting.model.entities.Question;

import java.util.List;

/**
 * Сервис для работы с сущностями Question (Вопрос).
 */
public interface QuestionService {

    /**
     * Метод для получения списка всех вопросов.
     *
     * @return список всех вопросов
     */
    List<Question> getAll();

    /**
     * Метод для получения вопроса по его идентификатору.
     *
     * @param id идентификатор вопроса
     * @return сущность вопроса
     */
    Question getById(Long id);

    /**
     * Метод для получения количества вопросов в базе данных.
     *
     * @return количество вопросов в базе данных
     */
    long getDbSize();

    /**
     * Метод для создания нового вопроса.
     *
     * @param text текст вопроса
     * @return созданная сущность вопроса
     */
    Question create(String text);

    /**
     * Метод для проверки уникальности текста вопроса.
     *
     * @param text текст вопроса
     * @return true, если текст вопроса уникален, иначе false
     */
    boolean isQuestionTextUnique(String text);

    /**
     * Метод для обновления существующего вопроса.
     *
     * @param id       идентификатор вопроса для обновления
     * @param question обновленная сущность вопроса
     * @return обновленная сущность вопроса
     */
    Question update(Long id, Question question);

    /**
     * Метод для удаления вопроса по его идентификатору.
     *
     * @param id идентификатор вопроса для удаления
     */
    void delete(Long id);

    /**
     * Метод для получения случайного списка вопросов с ограничением по количеству.
     *
     * @param limit максимальное количество вопросов в результирующем списке
     * @return список случайных вопросов с заданным ограничением по количеству
     */
    List<Question> getRandomQuestions(int limit);
}