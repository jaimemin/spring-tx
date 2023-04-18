package com.tistory.jaimemin.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;

@SpringBootTest
public class InitTransactionTest {

    @Autowired
    Init init;

    @Test
    void initialize() {

    }

    @TestConfiguration
    static class InitTxTestConfig {

        @Bean
        Init init() {
            return new Init();
        }
    }

    @Slf4j
    static class Init {

        @PostConstruct
        @Transactional
        public void transactionNotApplied() {
            log.info("@PostConstruct transaction active={}", TransactionSynchronizationManager.isActualTransactionActive());
        }

        @Transactional
        @EventListener(ApplicationReadyEvent.class)
        public void transactionApplied() {
            log.info("ApplicationReadyEvent transaction active={}", TransactionSynchronizationManager.isActualTransactionActive());
        }
    }
}
