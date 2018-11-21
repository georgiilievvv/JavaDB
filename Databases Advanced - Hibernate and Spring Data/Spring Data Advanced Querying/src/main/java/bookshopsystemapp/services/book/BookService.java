package bookshopsystemapp.services.book;



import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.domain.entities.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {

    void newBook(Book book);

    void newBooks(Iterable<Book> books);

    long getBooksCount();

    List<String> getBookTitleByAgeRestriction(AgeRestriction ageRestriction);

    List<String> getBookTitleOfGoldenEditionBookWithLessThen5000Copies();

    List<String> getBookTitleAndPriceForBooksWithPriceUnder5AndHigherThan40();

    List<String> getBookTitleOfBooksNotReleasedOnGivenYear(Integer year);

    List<String> getBookTitleEditionTypeAndPriceForBooksReleasedBeforeDate(LocalDate date);

    List<String> getBookTitleForBookTitlesContainingText(String text);

    List<String> getBookTitleAndAuthorForAuthorsLastNameStartsWith(String starting);

    Integer getCountOfBooksWithTitleLongerThan(Integer length);

    String getBookDetailsByTitle(String title);

    Integer increaseCopiesForBooksReleasedAfterDate(LocalDate startDate, Integer copiesToAdd);

    Integer removeBooksWithCopiesLessThan(Integer minCopies);
}
