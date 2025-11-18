package ru.otus.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ru.otus.annotations.*;
import ru.otus.reflection.ReflectionHelper;

@SuppressWarnings({"java:S106", "java:S5411", "java:S112"})
public class TestRunner {

    private TestRunner() {}

    public static void run(Class<?> testClazz) {
        String testClassName = testClazz.getSimpleName();
        System.out.println("Starting to run tests from class " + testClassName);
        Set<Method> testMethods = getAnnotatedMethods(testClazz, Test.class);
        if (testMethods.isEmpty()) {
            System.out.println("No methods with @Test annotation found in class " + testClassName);
            return;
        }
        Set<Method> beforeMethods = getAnnotatedMethods(testClazz, Before.class);
        Set<Method> afterMethods = getAnnotatedMethods(testClazz, After.class);
        Map<String, Boolean> testResults = new HashMap<>();

        for (Method testMethod : testMethods) {
            Object testObject = null;
            boolean testPassed = true;
            try {
                testObject = ReflectionHelper.instantiate(testClazz);
                callAnnotatedMethods(beforeMethods, testObject);
                callMethod(testMethod, testObject);
            } catch (RuntimeException e) {
                testPassed = false;
            } finally {
                try {
                    callAnnotatedMethods(afterMethods, testObject);
                } catch (RuntimeException e) {
                    testPassed = false;
                }
                testResults.put(testMethod.getName(), testPassed);
            }
        }

        printResults(testResults);
    }

    private static Set<Method> getAnnotatedMethods(Class<?> type, Class<? extends Annotation> annotationClass) {
        Set<Method> annotatedMethods = new HashSet<>();
        Method[] methods = type.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    private static void callAnnotatedMethods(Set<Method> annotatedMethods, Object objectTest) throws RuntimeException {
        for (Method method : annotatedMethods) {
            callMethod(method, objectTest);
        }
    }

    private static void callMethod(Method method, Object object) throws RuntimeException {
        try {
            method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printResults(Map<String, Boolean> testResults) {
        StringBuilder sb = new StringBuilder("Test Results:");
        sb.append(System.lineSeparator())
                .append("------------------------------")
                .append(System.lineSeparator());
        testResults.forEach((methodName, result) -> sb.append("Test: '")
                .append(methodName)
                .append("': ")
                .append((result ? "SUCCESS" : "FAILED"))
                .append(System.lineSeparator()));
        sb.append("------------------------------").append(System.lineSeparator());
        sb.append("Tests total: ").append(testResults.size()).append(System.lineSeparator());
        sb.append("Successful tests: ")
                .append(testResults.values().stream().filter(result -> result).count())
                .append(System.lineSeparator());
        sb.append("Failed tests: ")
                .append(testResults.values().stream().filter(result -> !result).count())
                .append(System.lineSeparator());
        System.out.println(sb.toString());
    }
}
