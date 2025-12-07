package ru.otus.aop.proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createMyClass(new TestLogging());

        testLogging.calculation(10);

        testLogging.calculation(11, 21);

        testLogging.calculation(22, 33, "Hello World!");

        testLogging.calculation(22, 33, "Hello World!", true);
    }
}
