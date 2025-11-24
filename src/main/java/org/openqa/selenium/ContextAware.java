package org.openqa.selenium;

import java.util.Set;

/**
 * Temporary shim for compatibility with Appium client expecting Selenium's legacy ContextAware API.
 * Remove when upgrading to a Selenium version that reintroduces this type or when Appium removes the dependency.
 */
public interface ContextAware {

    WebDriver context(String name);

    Set<String> getContextHandles();

    String getContext();
}
