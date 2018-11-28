package productshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productshop.domain.entities.Product;
import productshop.domain.entities.User;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("" +
            "SELECT p " +
            "FROM productshop.domain.entities.Product AS p " +
            "WHERE p.buyer IS NOT NULL AND " +
            "p.buyer.firstName IS NOT NULL AND " +
            "p.seller.firstName IS NOT NULL " +
            "ORDER BY p.seller.lastName, p.seller.firstName" )
    List<Product> extractAllSuccessfullySoldProducts();

    List<Product> findAllByPriceBetweenAndBuyerOrderByPrice(BigDecimal more, BigDecimal less, User buyer);
}
