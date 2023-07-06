package com.example.controllers;

import com.example.interfaces.IBookService;
import com.example.models.Book;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ok(bookService.getBooks());
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody @Valid Book book) {
        bookService.addBook(book);
        return ok("book created");
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);
        return ok("Book with id " + bookId + " deleted successfully");
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<String> updatebook(@PathVariable Integer bookId, @RequestBody @Valid Book book) {
        bookService.updateBook(bookId, book);
        return ok("book with id " + bookId + " updated");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException entityNotFoundException) {
        return notFound().build();
    }
}