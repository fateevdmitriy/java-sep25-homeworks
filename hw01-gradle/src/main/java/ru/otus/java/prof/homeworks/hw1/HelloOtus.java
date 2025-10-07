package ru.otus.java.prof.homeworks.hw1;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("java:S2629")
public class HelloOtus {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(HelloOtus.class.getName());
        List<Object> mixedList = Lists.newArrayList(2, "test", -4, false, 15.2, 38);
        Iterable<Integer> onlyIntegers = Iterables.filter(mixedList, Integer.class);

        logger.info("Mixed list: {}" + mixedList);
        logger.info("Filtered list - integers only: " + onlyIntegers);
    }
}
