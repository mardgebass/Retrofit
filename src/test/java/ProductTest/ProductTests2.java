package ProductTest;

import com.github.javafaker.Faker;
import dto.Product;
import enums.CategoryType;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.CategoryService;
import service.ProductService;
import utils.RetrofitUtils;

import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ProductTests2 {
    static Retrofit client;
    static ProductService productService;
    static CategoryService categoryService;
    private static Integer productId;
    Faker faker = new Faker();
    Product product;
    Product product1;
    Product product2;
    Product product3;
    ArrayList productList;

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
        categoryService = client.create(CategoryService.class);
    }

    @BeforeEach
    void setUp() {
        ArrayList<Product> productList = new ArrayList<>();
        product = new Product(1, "11", 111, "1111");
        product1 = new Product(2, "22", 222, "2222");
        product2 = new Product(3, "33", 333, "3333");
        product3 = new Product().withTitle(faker.food().dish())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FOOD.getTitle());
        productList.add(product);
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
    }

    @Test
    void postProductsTest() throws IOException {
        Response<ArrayList<Product>> response = productService.createProducts(productList).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }


//    @AfterAll
//    static void tearDown() throws IOException {
//        Response<ResponseBody> response = productService.deleteProduct(productId).execute();
//        assertThat(response.isSuccessful(), is(true));
//    }

}