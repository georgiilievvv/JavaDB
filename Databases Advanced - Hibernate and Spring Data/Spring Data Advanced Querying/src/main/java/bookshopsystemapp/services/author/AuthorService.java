package bookshopsystemapp.services.author;

import bookshopsystemapp.domain.entities.Author;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AuthorService {

    void registerAuthor(Author author);

    void registerAuthors(Iterable<Author> authors);

    Author getRandomAuthor();

    List<String> getAuthorNamesWithFirstNameEndingWith(String ending);

//    List<String> getAuthorsByBookCopiesCount();

    Integer getAuthorBooksCount(String firstName, String lastName);
}
