package sh.alex.onlineTesting.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность Question представляет собой вопрос в тесте.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    /**
     * Идентификатор вопроса.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Текст вопроса.
     */
    @Column(nullable = false, unique = true)
    private String text;

    /**
     * Конструктор для создания нового вопроса.
     *
     * @param text текст вопроса
     */
    public Question(String text) {
        this.text = text;
    }

    /**
     * Переопределение метода toString() для получения текста вопроса.
     *
     * @return текст вопроса
     */
    @Override
    public String toString() {
        return text;
    }
}
