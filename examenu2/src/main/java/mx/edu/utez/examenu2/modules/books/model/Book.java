package mx.edu.utez.examenu2.modules.books.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(200)", nullable = false)
    private String name;
    @Column(columnDefinition = "VARCHAR(200)", nullable = false)
    private String author;
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDate publishDate;
    @Column(columnDefinition = "LONGTEXT")
    private String cover;

}
