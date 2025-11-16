package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

@SuppressWarnings({"java:S106", "java:S125", "java:S112"})
public class TestFirst {

    @Before
    public static void prepareTestingFirst() {
        System.out.println(TestFirst.class.getSimpleName() + " first method with annotation @Before");
    }

    @Before
    public static void prepareTestingSecond() {
        System.out.println(TestFirst.class.getSimpleName() + " second method with annotation @Before");
        // throw new RuntimeException();
    }

    @Test
    public void testFirst() {
        System.out.println(TestFirst.class.getSimpleName() + " first test");
    }

    @Test
    public void testSecond() {
        System.out.println(TestFirst.class.getSimpleName() + " second test");
    }

    @Test
    public void testThird() {
        System.out.println(TestFirst.class.getSimpleName() + " third test");
        // throw new RuntimeException();
    }

    @After
    public static void finalizeTestingFirst() {
        System.out.println(TestFirst.class.getSimpleName() + " first method with annotation @After");
    }

    @After
    public static void finalizeTestingSecond() {
        System.out.println(TestFirst.class.getSimpleName() + " second method with annotation @After");
    }

    @After
    public static void finalizeTestingThird() {
        System.out.println(TestFirst.class.getSimpleName() + " third method with annotation @After");
        // throw new RuntimeException();
    }
}
