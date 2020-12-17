package it.polimi.db2.services;

import it.polimi.db2.entities.Product;
import it.polimi.db2.exception.ProductAlreadyExistingException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Stateless
public class ProductService {
    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    public ProductService() {
    }

    public Product createProduct(String name, String description, Float price, LocalDate date, byte[] image) throws ProductAlreadyExistingException {
        if (!em.createNamedQuery("Product.findByDate").setParameter("date", date).getResultList().isEmpty()) {
            throw new ProductAlreadyExistingException("A product is already created for this date!");
        }
        Product product = new Product(name, description, price, date, image);
        em.persist(product);
        return product;
    }

    public Product findProductByDate(LocalDate date) {
        List<Product> p = em.createNamedQuery("Product.findByDate", Product.class).setParameter("date", date).getResultList();
        if (p.isEmpty()) return null;
        else return p.get(0);
    }

    public Product findProductById(int id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAllProducts() {
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        LocalDate today = LocalDate.now(zoneId);
        List<Product> p = em.createNamedQuery("Product.findAllByPreviousDate", Product.class).setParameter("date", today).getResultList();
        if (p.isEmpty()) return null;
        return p;

    }

}
