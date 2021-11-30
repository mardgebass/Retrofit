package ProductTest;

import com.github.javafaker.Faker;
import dto.Product;
import enums.CategoryType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.CategoryService;
import service.ProductService;
import utils.PrettyLogger;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ProductGetTest {
    static Retrofit client;
    static ProductService productService;
    private static Integer productId;
    static Faker faker = new Faker();
    static Product product;

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
    }

    @BeforeEach
    void setUp() throws IOException {

        product = new Product().withTitle(faker.food().dish())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FOOD.getTitle());

        Response<Product> response = productService.createProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));

        productId = response.body().getId();

    }

    @Test
    void getProductTest() throws IOException {
        Response<Product> response = productService.getProduct(productId).execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));
    }

    @Test
    void getProductNegativeTest() throws IOException {
        Response<Product> response = productService.getProduct(23432).execute();

        assertThat(response.isSuccessful(), is(false));
    }


    @AfterAll
    static void tearDown() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(productId).execute();
        assertThat(response.isSuccessful(), is(true));
    }

}