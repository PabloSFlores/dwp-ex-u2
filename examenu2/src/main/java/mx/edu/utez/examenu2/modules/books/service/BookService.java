package mx.edu.utez.examenu2.modules.books.service;

import mx.edu.utez.examenu2.kernel.ResponseApi;
import mx.edu.utez.examenu2.modules.books.model.Book;
import mx.edu.utez.examenu2.modules.books.model.IBookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {
    private final IBookRepository iBookRepository;

    public BookService(IBookRepository iBookRepository) {
        this.iBookRepository = iBookRepository;
    }

    public ResponseApi<Page<Book>> findAll(Pageable pageable) {
        return new ResponseApi<>(
                this.iBookRepository.findAll(pageable),
                "Ok",
                false,
                HttpStatus.OK
        );
    }

    public ResponseApi<Page<Book>> findAllBooksWithCover(Pageable pageable) {
        return new ResponseApi<>(
                this.iBookRepository.findByCoverIsNotNull(pageable),
                "Ok",
                false,
                HttpStatus.OK
        );
    }

    public ResponseApi<Book> findOne(Long id) {
        if (this.iBookRepository.findById(id).isEmpty())
            return new ResponseApi<>(null, "NotFound", true, HttpStatus.NOT_FOUND);
        return new ResponseApi<>(
                this.iBookRepository.findById(id).get(),
                "Book details",
                false,
                HttpStatus.OK
        );
    }

    public ResponseApi<Book> save(Book book) {
        return new ResponseApi(
                this.iBookRepository.saveAndFlush(book),
                "Saved",
                false,
                HttpStatus.OK
        );
    }

    public ResponseApi<Book> update(Book book) {
        if (this.iBookRepository.findById(book.getId()).isEmpty())
            return new ResponseApi<>(null, "NotFound", true, HttpStatus.NOT_FOUND);
        return new ResponseApi<>(
                this.iBookRepository.saveAndFlush(book),
                "Updated",
                false,
                HttpStatus.OK
        );
    }

    public ResponseApi<Boolean> delete(Long id) {
        if (this.iBookRepository.findById(id).isEmpty())
            return new ResponseApi<>(null, "NotFound", true, HttpStatus.NOT_FOUND);
        this.iBookRepository.deleteById(id);
        return new ResponseApi<>(
                this.iBookRepository.findById(id).isEmpty(),
                "Deleted",
                false,
                HttpStatus.OK
        );
    }
}
