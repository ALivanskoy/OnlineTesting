package sh.alex.onlineTesting.model.entities;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

/**
 * Класс Test представляет тест, состоящий из вопросов и ответов.
 */
@Data
@AllArgsConstructor
public class Test {

    /**
     * Map, содержащий вопросы и соответствующие им списки ответов.
     * Ключ - объект Question, значение - список объектов Answer.
     */
    private Map<Question, List<Answer>> questions;

    /**
     * Конструктор без аргументов, инициализирующий пустой Map для вопросов и ответов.
     */
    public Test() {
        this.questions = new HashMap<>();
    }

    /**
     * Метод для добавления вопроса и соответствующих ему ответов в тест.
     *
     * @param question объект Question, представляющий вопрос
     * @param answers  список объектов Answer, представляющих ответы на вопрос
     */
    public void addQuestion(Question question, List<Answer> answers) {
        this.questions.put(question, answers);
    }
}
