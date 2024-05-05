package sh.alex.onlineTesting.model.services.interfaces;

import sh.alex.onlineTesting.model.entities.Answer;

import java.util.List;

/**
 * Сервис для работы с сущностями Answer (Ответ).
 */
public interface AnswerService {

    /**
     * Метод для получения списка всех ответов.
     *
     * @return список всех ответов
     */
    List<Answer> getAll();

    /**
     * Метод для получения списка ответов по идентификатору вопроса.
     *
     * @param id идентификатор вопроса
     * @return список ответов, связанных с указанным вопросом
     */
    List<Answer> getByQuestionId(Long id);

    /**
     * Метод для создания нового ответа.
     *
     * @param answer    сущность ответа для создания
     * @param questionId идентификатор вопроса, к которому относится ответ
     */
    void create(Answer answer, Long questionId);

    /**
     * Метод для получения ответа по его идентификатору.
     *
     * @param id идентификатор ответа
     * @return сущность ответа
     */
    Answer getById(Long id);

    /**
     * Метод для обновления существующего ответа.
     *
     * @param id     идентификатор ответа для обновления
     * @param answer обновленная сущность ответа
     * @return обновленная сущность ответа
     */
    Answer update(Long id, Answer answer);

    /**
     * Метод для удаления ответа по его идентификатору.
     *
     * @param id идентификатор ответа для удаления
     */
    void delete(Long id);
}
