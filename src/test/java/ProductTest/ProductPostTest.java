package ProductTest;

import okhttp3.ResponseBody;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import dto.Product;
import enums.CategoryType;
import ru.annachemic.db.dao.ProductsMapper;
import service.ProductService;
import utils.DbUtils;
import utils.PrettyLogger;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductPostTest {
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
    void setUp() {
        product = new Product()
                .withTitle(faker.food().dish())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FOOD.getTitle());
    }

    @Test
    void postProductTest() throws IOException {

        Integer countProductsBefore = DbUtils.countProducts(productsMapper);

        Response<Product> response = productService.createProduct(product).execute();

        Integer countProductsAfter = DbUtils.countProducts(productsMapper);

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(countProductsAfter, equalTo(countProductsBefore+1));
        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));

        productId = response.body().getId();
    }

    @Test
    void postDbProductTest() {

        Integer countProductsBefore = DbUtils.countProducts(productsMapper);

        DbUtils.createNewProduct(productsMapper);

        Integer countProductsAfter = DbUtils.countProducts(productsMapper);

        assertThat(countProductsAfter, equalTo(countProductsBefore+1));

    }


    @AfterEach
    void tearDown() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(productId).execute();
        assertThat(response.isSuccessful(), is(true));
    }
}