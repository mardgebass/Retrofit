package utils;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.annachemic.db.dao.CategoriesMapper;
import ru.annachemic.db.dao.ProductsMapper;
import ru.annachemic.db.model.Categories;
import ru.annachemic.db.model.CategoriesExample;
import ru.annachemic.db.model.Products;
import ru.annachemic.db.model.ProductsExample;

import java.io.IOException;

@UtilityClass
public class DbUtils {

    private static  String resource = "mybatisConfig.xml";
    static Faker faker = new Faker();

    private static SqlSession getSqlSession() throws IOException {
        SqlSessionFactory sqlSessionFactory;
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        return sqlSessionFactory.openSession(true);
    }

    //    для бросания проверяемых исключений без их объявления в throws метода
    @SneakyThrows
    //    получаем необходимый маппер
    public static CategoriesMapper getCategoriesMapper(){
        return getSqlSession().getMapper(CategoriesMapper.class);
    }

    //    для бросания проверяемых исключений без их объявления в throws метода
    @SneakyThrows
    //    получаем необходимый маппер
    public static ProductsMapper getProductsMapper() {
        return getSqlSession().getMapper(ProductsMapper.class);
    }

    public static void createNewCategory(CategoriesMapper categoriesMapper) {
        Categories newCategory = new Categories();
        newCategory.setTitle(faker.food().dish());

        categoriesMapper.insert(newCategory);
    }

    public static void createNewProduct(ProductsMapper productsMapper) {
        Products newProduct = new Products();
        newProduct.setTitle(faker.food().dish());

        productsMapper.insert(newProduct);
    }

    public static Integer countCategories(CategoriesMapper categoriesMapper) {
        long categoriesCount = categoriesMapper.countByExample(new CategoriesExample());
        return Math.toIntExact(categoriesCount);
    }

    public static Integer countProducts(ProductsMapper productsMapper) {
        long products = productsMapper.countByExample(new ProductsExample());
        return Math.toIntExact(products);
    }
}
