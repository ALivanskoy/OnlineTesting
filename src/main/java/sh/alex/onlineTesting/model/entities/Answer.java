package sh.alex.onlineTesting.model.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность Answer представляет собой ответ на вопрос теста.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {

    /**
     * Идентификатор ответа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Текст ответа.
     */
    @Column(nullable = false)
    private String text;

    /**
     * Флаг, указывающий, является ли ответ правильным.
     */
    @Column(nullable = false)
    private Boolean correct;

    /**
     * Идентификатор вопроса, к которому относится ответ.
     */
    @Column(nullable = false)
    private Long questionId;

    /**
     * Конструктор для создания нового ответа.
     *
     * @param text    текст ответа
     * @param correct флаг, указывающий, является ли ответ правильным
     */
    public Answer(String text, Boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    /**
     * Переопределение метода toString() для получения текста ответа.
     *
     * @return текст ответа
     */
    @Override
    public String toString() {
        return text;
    }

    /**
     * Метод для получения флага, указывающего, является ли ответ правильным.
     *
     * @return true, если ответ правильный, иначе false
     */
    public Boolean isCorrect() {
        return correct;
    }
}
