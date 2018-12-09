package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder exportData = new StringBuilder();

        List<Category> categories = this.categoryRepository.extractCategoriesByItemsCount();

        for (Category category : categories) {

            exportData.append("Category: ").append(category.getName())
                    .append(System.lineSeparator());

            for (Item item : category.getItems()) {

                exportData.append("---ItemName: ").append(item.getName()).append(System.lineSeparator());
                exportData.append("---ItemPrice: ").append(item.getPrice()).append(System.lineSeparator()).append(System.lineSeparator());
            }

        }

        return exportData.toString().trim();
    }
}
