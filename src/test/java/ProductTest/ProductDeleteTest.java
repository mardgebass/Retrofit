package ProductTest;

import com.github.javafaker.Faker;
import dto.Product;
import enums.CategoryType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.ProductService;
import utils.PrettyLogger;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ProductDeleteTest {
    static Retrofit client;
    static ProductService productService;
    private static Integer productId;
    Faker faker = new Faker();
    Product product;

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
    }

    @BeforeEach
    void setUp() throws IOException {
        product = new Product().withTitle(faker.backToTheFuture().character())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.ELECTRONIC.getTitle());

        Response<Product> response = productService.createProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));

        productId = response.body().getId();
    }

    @Test
    void postProductTest() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(productId).execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.isSuccessful(), is(true));
    }

    @Test
    void negativeTest() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(22).execute();

        assertThat(response.isSuccessful(), is(false));
    }

}