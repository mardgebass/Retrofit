package CategoryTest;

import dto.Category;
import enums.CategoryType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.annachemic.db.dao.CategoriesMapper;
import service.CategoryService;
import utils.DbUtils;
import utils.PrettyLogger;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class CategoryTests {
    static Retrofit client;
    static CategoryService categoryService;
    static CategoriesMapper categoriesMapper;

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        categoryService = client.create(CategoryService.class);
        categoriesMapper = DbUtils.getCategoriesMapper();
    }

    @Test
    void createDbCategoryByIdTest() {

        Integer countCategoriesBefore = DbUtils.countCategories(categoriesMapper);

        DbUtils.createNewCategory(categoriesMapper);

        Integer countCategoriesAfter = DbUtils.countCategories(categoriesMapper);

        assertThat(countCategoriesAfter, equalTo(countCategoriesBefore+1));

    }


    @Test
    void getCategoryByIdTest() throws IOException {

        Integer id = CategoryType.FURNITURE.getId();

        Integer countCategoriesBefore = DbUtils.countCategories(categoriesMapper);

        Response<Category> response = categoryService.getCategory(id).execute();

        Integer countCategoriesAfter = DbUtils.countCategories(categoriesMapper);

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(countCategoriesBefore, equalTo(countCategoriesAfter));
        assertThat(response.body().getTitle(), equalTo(CategoryType.FOOD.getTitle()));
        assertThat(response.body().getId(), equalTo(id));

    }

    @Test
    void getCategoryByIdNegativeTest() throws IOException {

        Response<Category> response = categoryService.getCategory(4).execute();

        assertThat(response.isSuccessful(), is(false));

    }

}