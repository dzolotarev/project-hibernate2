package com.hiberproject2;

import com.hiberproject2.dao.*;
import com.hiberproject2.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class App {
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RatingDAO ratingDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    private SessionFactory sessionFactory;

    public App() {
        Properties properties = getProperties();

        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Feature.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rating.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .buildSessionFactory();

        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        ratingDAO = new RatingDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        return properties;
    }

    public static void main(String[] args) {
        App app = new App();
        Customer customer = app.createCustomer();
        app.returnFilmToStore();
        app.customerRentFilm(customer);
        app.newFilmIsNowAvailable();
    }

    private void newFilmIsNowAvailable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Language language = languageDAO.getItems(0, 20).stream().unordered().findAny().get();
            List<Category> categories = categoryDAO.getItems(0, 5);
            List<Actor> actors = actorDAO.getItems(0, 20);

            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setCategories(new HashSet<>(categories));
            film.setLanguage(language);
            film.setOriginalLanguage(language);
            film.setLength((short) 123);
            film.setSpecialFeatures(Set.of(Feature.TRAILERS, Feature.DELETED_SCENES, Feature.BEHIND_THE_SCENES));
            film.setRating(Rating.NC17);
            film.setReplacementCost(BigDecimal.valueOf(321.12));
            film.setRentalRate(BigDecimal.ZERO);
            film.setTitle("Funny movie");
            film.setDescription("New superb movie");
            film.setRentalDuration((byte) 12);
            film.setYear(Year.of(2023));
            filmDAO.save(film);

            FilmText filmText = new FilmText();
            filmText.setId(film.getId());
            filmText.setFilm(film);
            filmText.setTitle("Funny movie");
            filmText.setDescription("New superb movie bla-bla-bla...");
            filmTextDAO.save(filmText);

            session.getTransaction().commit();
        }
    }

    private void customerRentFilm(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = filmDAO.getFirstAvailableFilmForRent();
            Store store = storeDAO.getItems(0, 1).get(0);

            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            Staff staff = store.getStaff();

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rental.setRentalDate(LocalDateTime.now());
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setStaff(staff);
            payment.setRental(rental);
            payment.setAmount(BigDecimal.valueOf(123.45));
            payment.setPaymentDate(LocalDateTime.now());
            paymentDAO.save(payment);

            session.getTransaction().commit();
        }
    }

    private void returnFilmToStore() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDAO.getUnreturnedFilm();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Store store = storeDAO.getItems(0, 1).get(0);
            City city = cityDAO.getByName("London");
            Address address = new Address();
            address.setCity(city);
            address.setAddress("Brodrick Grove");
            address.setAddress2("house  10 room 1");
            address.setPostalCode("123456789");
            address.setDistrict("Abbey Wood");
            address.setPhone("+1-234-567-89-01");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setFirstName("John");
            customer.setLastName("Smith");
            customer.setEmail("john_smith@email.com");
            customer.setAddress(address);
            customer.setStore(store);
            customer.setIsActve(true);
            customerDAO.save(customer);

            session.getTransaction().commit();

            return customer;
        }
    }
}
