package it.polimi.db2.services;

import it.polimi.db2.entities.Product;
import it.polimi.db2.exception.ProductAlreadyExistingException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.Date;

@Stateless
public class ProductService {
    private EntityManager em;

    public ProductService(){}

    public void createProduct(String name, String description, Float price, Date date, byte[] image) throws ProductAlreadyExistingException{
        if(!em.createNamedQuery("Product.findByDate").setParameter("date",date).getResultList().isEmpty()) {
            throw new ProductAlreadyExistingException();
        }
        Product product = new Product(name, description, price, date, image);
        em.persist(product);
    }

    public Product findProductById(int productId) {
        Product product = em.find(Product.class, productId);
        return product;
    }


}
