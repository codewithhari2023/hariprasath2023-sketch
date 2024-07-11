package org.example.view;

import org.example.util.StringUtils;

public class RegisterPage {
    public static void printRegistrationSuccessful() {
        try {
          Thread.sleep(2000);

            System.out.println(StringUtils.REGISTRATION_SUCCESSFUL);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void passwordMissMatch() {
        try {
            System.out.println("#---------------------#");
            System.out.println(StringUtils.PASSWORD_MISMATCH);
            System.out.println("#---------------------#");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
