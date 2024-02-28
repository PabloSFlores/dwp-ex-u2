package mx.edu.utez.examenu2.modules.books.controller;

import jakarta.websocket.server.PathParam;
import mx.edu.utez.examenu2.kernel.ResponseApi;
import mx.edu.utez.examenu2.modules.books.model.Book;
import mx.edu.utez.examenu2.modules.books.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping(value = "/api/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/paged/")
    public ResponseApi<Page<Book>> findAll(Pageable pageable) {
        return this.bookService.findAll(pageable);
    }

    @GetMapping("/paged/covered/")
    public ResponseApi<Page<Book>> findAllWithCover(Pageable pageable) {
        return this.bookService.findAllBooksWithCover(pageable);
    }

    @GetMapping("/{id}")
    public ResponseApi<Book> findOne(@PathVariable("id") Long id) {
        return this.bookService.findOne(id);
    }

    @PostMapping("/")
    public ResponseApi<Book> save(@RequestBody Book book) {
        return this.bookService.save(book);
    }

    @PutMapping("/")
    public ResponseApi<Book> update(@RequestBody Book book) {
        return this.bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public ResponseApi<Boolean> delete(@PathVariable("id") Long id) {
        return this.bookService.delete(id);
    }
}

