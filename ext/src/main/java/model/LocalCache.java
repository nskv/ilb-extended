package main.java.model;

import java.util.Collection;
import java.util.ConcurrentModificationException;

public class LocalCache {
    private Collection<Entity> cache;
    private static LocalCache localCache;

    private void LocalCache(){
        if(localCache!=null){
            throw new ConcurrentModificationException("LocalCache is singleton");
        }
        //TODO: invoke Spring Data

    }

    public static LocalCache getAccess(){
        if(localCache==null){
            localCache = new LocalCache();
        }
        return localCache;
    }

    public Collection<Entity> getData(){
        return cache;
    }
}
