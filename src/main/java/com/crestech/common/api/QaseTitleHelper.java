// file: src/main/java/com/crestech/common/api/QaseTitleHelper.java
package com.crestech.common.api;

import io.qameta.allure.Epic;

public class QaseTitleHelper {

    // returns epic value or fallback
    public static String getEpicDescription(Class<?> testClass) {
        if (testClass == null) {
            return "Generic Automation Test Run";
        }
        Epic epicAnnotation = testClass.getAnnotation(Epic.class);
        if (epicAnnotation != null) {
            return epicAnnotation.value();
        }
        return "Generic Automation Test Run";
    }
}
