package com.apap.tutorial6.repository;

import com.apap.tutorial6.model.CarModel;
import com.apap.tutorial6.model.DealerModel;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CarDbTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarDb carDb;

    @Test
    public void whenFindByType_thenReturnCar(){
        DealerModel dealerModel = new DealerModel();
        dealerModel.setNama("Rendezvous");
        dealerModel.setAlamat("Elan Plateu");
        dealerModel.setNoTelp("0217382917");
        entityManager.persist(dealerModel);
        entityManager.flush();

        CarModel carModel = new CarModel();
        carModel.setBrand("MV Agusta");
        carModel.setType("F4 RC");
        carModel.setPrice(new Long("1500000000"));
        carModel.setAmount(13);
        carModel.setDealer(dealerModel);
        entityManager.persist(carModel);
        entityManager.flush();

        Optional<CarModel> found = carDb.findByType(carModel.getType());

        assertThat(found.get(), Matchers.notNullValue());
        assertThat(found.get(), Matchers.equalTo(carModel));
    }
}
