package ProductTest;

import com.github.javafaker.Faker;
import dto.Product;
import enums.CategoryType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.annachemic.db.dao.CategoriesMapper;
import ru.annachemic.db.dao.ProductsMapper;
import service.ProductService;
import utils.DbUtils;
import utils.PrettyLogger;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ProductDeleteTest {
    static ProductsMapper productsMapper;
    static Retrofit client;
    static ProductService productService;
    private static Integer productId;
    Faker faker = new Faker();
    Product product;

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
        productsMapper = DbUtils.getProductsMapper();
    }

    @BeforeEach
    void setUp() throws IOException {
        product = new Product().withTitle(faker.backToTheFuture().character())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FURNITURE.getTitle());

        Response<Product> response = productService.createProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
//        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));

        productId = response.body().getId();
    }

    @Test
    void deleteProductTest() throws IOException {

        Integer countProductsBefore = DbUtils.countProducts(productsMapper);

        Response<ResponseBody> response = productService.deleteProduct(productId).execute();

        Integer countProductsAfter = DbUtils.countProducts(productsMapper);

        assertThat(countProductsAfter, equalTo(countProductsBefore-1));

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.isSuccessful(), is(true));
    }

    @Test
    void negativeTest() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(22).execute();

        assertThat(response.isSuccessful(), is(false));
    }

}