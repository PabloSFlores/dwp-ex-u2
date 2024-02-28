package mx.edu.utez.examenu2.kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.examenu2.modules.books.model.Book;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseApi<T> {
    T data;
    String message;
    Boolean error;
    HttpStatus httpStatus;
}
