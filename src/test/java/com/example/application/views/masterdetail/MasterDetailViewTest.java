package com.example.application.views.masterdetail;

import java.util.List;

import com.example.application.data.entity.SamplePerson;
import com.example.application.data.generator.DataGenerator;
import com.example.application.data.service.SamplePersonRepository;
import com.example.application.test.InMemorySamplePersonRepository;
import com.example.application.test.TestInstantiatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridWrap;
import com.vaadin.testbench.unit.UIUnitTest;

@ExtendWith({ SpringExtension.class })
@Import(MasterDetailViewTest.TestConfig.class)
class MasterDetailViewTest extends UIUnitTest {

    @Autowired
    SamplePersonRepository repository;

    List<SamplePerson> people;

    @BeforeEach
    @Override
    protected void initVaadinEnvironment() {
        super.initVaadinEnvironment(TestInstantiatorFactory.class);
    }

    @BeforeEach
    void fetchTestData() {
        people = repository.findAll(Pageable.ofSize(10)).toList();
    }

    @Test
    void gridContainsData() {
        GridWrap<Grid<SamplePerson>, SamplePerson> grid_ = $(Grid.class)
                .first();
        Assertions.assertTrue(grid_.size() > 20,
                "With the generated data, there should be at least twenty rows in the grid, but found "
                        + grid_.size());

        SamplePerson person = people.get(0);
        Assertions.assertEquals(person.getFirstName(), grid_.getCellText(0, 0));
        Assertions.assertEquals(person.getLastName(), grid_.getCellText(0, 1));
        Assertions.assertEquals(person.getEmail(), grid_.getCellText(0, 2));

    }

    @Configuration
    @ComponentScan("com.example.application.data")
    @Import(TestInstantiatorFactory.class)
    static class TestConfig {

        @Bean
        SamplePersonRepository samplePersonRepository(
                DataGenerator dataGenerator) throws Exception {
            InMemorySamplePersonRepository repository = new InMemorySamplePersonRepository();
            dataGenerator.loadData(repository).run();
            return repository;
        }
    }
}