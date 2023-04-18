package com.tistory.jaimemin.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class ExampleTest {

    @Autowired
    ExampleService exampleService;

    @Test
    void transactionalFunc() {
        exampleService.transactionalFunc();
    }

    @Test
    void nonTransactionalFunc() {
        exampleService.nonTransactionalFunc();
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        ExampleService exampleService() {
            return new ExampleService();
        }
    }

    @Slf4j
    static class ExampleService {

        public void nonTransactionalFunc() {
            log.info("call nonTransactionalFunc");
            printTxInfo();
            transactionalFunc();
        }

        @Transactional
        public void transactionalFunc() {
            log.info("call transactionalFunc");

            printTxInfo();
        }

        private void printTxInfo() {
            log.info("transaction active={}", TransactionSynchronizationManager.isActualTransactionActive());
            log.info("transaction readOnly={}", TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        }
    }
}
