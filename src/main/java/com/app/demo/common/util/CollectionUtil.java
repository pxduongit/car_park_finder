package com.app.demo.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtil {

    public static <T> void splitAndConsume(Collection<T> list, int subSize, boolean ignoreEmpty, Consumer<Collection<T>> subListConsumer) {
        if(list.isEmpty() && ignoreEmpty) {
            return;
        }

        if(list.size() <= subSize) {
            subListConsumer.accept(list);
        }
        else {
            List<T> subList = new ArrayList<>(subSize);
            for(T element : list) {
                subList.add(element);
                if(subList.size() == subSize) {
                    subListConsumer.accept(subList);
                    subList = new ArrayList<>(subSize);
                }
            }
            if(!subList.isEmpty()) {
                subListConsumer.accept(subList);
            }
        }
    }

}
