package com.tistory.jaimemin.springtx.apply;

import lombok.RequiredArgsConstructor;
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
public class ExampleV2Test {

    @Autowired
    NonTransactionService nonTransactionService;

    @Test
    void nonTransactionalFunc() {
        nonTransactionService.nonTransactionalFunc();
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        NonTransactionService nonTransactionService() {
            return new NonTransactionService(transactionService());
        }

        @Bean
        TransactionService transactionService() {
            return new TransactionService();
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    static class NonTransactionService {

        private final TransactionService transactionService;

        public void nonTransactionalFunc() {
            log.info("call nonTransactionalFunc");
            printTxInfo();
            transactionService.transactionalFunc();
        }

        private void printTxInfo() {
            log.info("transaction active={}", TransactionSynchronizationManager.isActualTransactionActive());
            log.info("transaction readOnly={}", TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        }
    }

    @Slf4j
    static class TransactionService {

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
