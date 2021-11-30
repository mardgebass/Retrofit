package ProductTest;

import dto.Product;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.ProductService;
import utils.PrettyLogger;
import utils.RetrofitUtils;

import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProductsGetTest {

    static Retrofit client;
    static ProductService productService;

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
    }

    @Test
    void getProductTest() throws IOException {
        Response<ArrayList<Product>> response = productService.getProducts().execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.isSuccessful(), is(true));
    }


}