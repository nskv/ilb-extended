package main.java.karchevsky.apache.ilb.service;

import karchevsky.apache.ilb.model.EntityGraphVertex;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Transactional
@Service
public class LocalCache {
    private Collection<EntityGraphVertex> cache;

    public Collection<EntityGraphVertex> getData(){
        return cache;
    }
}
