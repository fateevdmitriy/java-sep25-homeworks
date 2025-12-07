package ru.otus.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.Log;

class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    @SuppressWarnings("unchecked")
    static <T> T createMyClass(T implClass) {
        InvocationHandler handler = new MyInvocationHandler<>(implClass);
        return (T) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    static class MyInvocationHandler<T> implements InvocationHandler {
        private final T targetClass;

        MyInvocationHandler(T targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isLogAnnotatedMethod(method)) {
                logger.info(
                        "executed method: {}, param: {}",
                        method.getName(),
                        Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", ")));
            }
            return method.invoke(targetClass, args);
        }

        private boolean isLogAnnotatedMethod(Method interfaceMethod) {
            try {
                return targetClass
                        .getClass()
                        .getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes())
                        .isAnnotationPresent(Log.class);
            } catch (NoSuchMethodException | SecurityException e) {
                throw new IllegalStateException(
                        "Method " + interfaceMethod.getName() + " is not found in the interface implementation.", e);
            }
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + " { interface implementation class = " + targetClass + " }";
        }
    }
}
