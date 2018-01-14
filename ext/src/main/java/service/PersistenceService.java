package main.java.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Slf4j
@Component
class PersistenceService {

    @PersistenceContext
    private EntityManager em;

    /**
     * ВНИМАНИЕ! для корректной работы требует заполнения всех полей name в анотации @Column
     * <p>
     * Batch insert вставка в БД
     */
    @Transactional
    public void batchInsert(Collection<?> entities) {
        BatchInsertUtil.batchInsert(em, entities);
    }
}
