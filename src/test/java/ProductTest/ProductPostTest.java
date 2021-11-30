package ProductTest;

import okhttp3.ResponseBody;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import dto.Product;
import enums.CategoryType;
import service.ProductService;
import utils.PrettyLogger;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductPostTest {
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

    @Test
    void postProductTest() throws IOException {

        product = new Product().withTitle(faker.funnyName().name())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FURNITURE.getTitle());

        Response<Product> response = productService.createProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));

        productId = response.body().getId();
    }


    @AfterEach
    void getProductTest() throws IOException {
        Response<Product> response = productService.getProduct(productId).execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));
    }

    @AfterAll
    static void tearDown() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(productId).execute();
        assertThat(response.isSuccessful(), is(true));
    }

}