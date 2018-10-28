package com.lianjia.devext.utils;

import java.util.Collection;

public class CollectionUtil {

    public static boolean isCollectionEmpty(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }
}