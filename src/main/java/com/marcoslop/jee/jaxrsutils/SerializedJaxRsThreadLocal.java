package com.marcoslop.jee.jaxrsutils;

public class SerializedJaxRsThreadLocal {

    private static ThreadLocal<IDefaultJaxRsSerializer> serializedJaxRsThreadLocale =
            new ThreadLocal<IDefaultJaxRsSerializer>();

    public static void remove() {
        serializedJaxRsThreadLocale.remove();
    }

    public static void set(IDefaultJaxRsSerializer element) {
        serializedJaxRsThreadLocale.set(element);
    }

    public static IDefaultJaxRsSerializer getElement() {
        return serializedJaxRsThreadLocale.get();
    }


}
