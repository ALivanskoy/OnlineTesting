package sh.alex.onlineTesting.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Сущность TestResults представляет результаты прохождения теста пользователем.
 */
@Entity
@Table(name = "results")
@Data
public class TestResults {

    /**
     * Идентификатор записи результатов теста.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя, прошедшего тест.
     */
    @Column
    private String username;

    /**
     * Дата и время прохождения теста.
     */
    @Column(name = "DateTime", columnDefinition = "DATETIME")
    private LocalDateTime localDateTime;

    /**
     * Количество правильных ответов, данных пользователем во время прохождения теста.
     */
    @Column
    private byte rightAnswers;
}
