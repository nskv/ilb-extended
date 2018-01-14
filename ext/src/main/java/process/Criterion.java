package main.java.process;


import main.java.model.Entity;
import main.java.model.LocalCache;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Criterion {
    private LocalCache cache;
    abstract boolean match(Entity e1, Entity e2);

    protected List<Entity> getData(Predicate<? super Entity> predicate){
        return cache.getData()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public void injectLocalCacheReference(LocalCache localCache){
        cache=localCache;
    }
}
