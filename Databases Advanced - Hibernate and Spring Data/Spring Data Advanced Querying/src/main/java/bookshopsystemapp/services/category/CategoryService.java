package bookshopsystemapp.services.category;

import bookshopsystemapp.domain.entities.Category;

import java.util.Set;

public interface CategoryService {

    void newCategory(Category category);

    void newCategories(Iterable<Category> categories);

    Set<Category> getRandomCategories();
}
