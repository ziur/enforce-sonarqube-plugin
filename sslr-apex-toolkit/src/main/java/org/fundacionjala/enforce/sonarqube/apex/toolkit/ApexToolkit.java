package org.fundacionjala.enforce.sonarqube.apex.toolkit;

import org.sonar.sslr.toolkit.Toolkit;

public final class ApexToolkit {

    private ApexToolkit() {
    }

    public static void main(String[] args) {
        Toolkit toolkit = new Toolkit("SSLR :: Apex :: Toolkit", new ApexConfigurationModel());
        toolkit.run();
    }
}