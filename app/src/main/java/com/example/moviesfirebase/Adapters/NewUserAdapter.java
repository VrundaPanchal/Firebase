package com.example.moviesfirebase.Adapters;

public class NewUserAdapter {
    private String uEmail;
    private String uPassword;

    // Default constructor
    public NewUserAdapter() {}

    // Parameterized constructor
    public NewUserAdapter(String uEmail, String uPassword) {
        this.uEmail = uEmail;
        this.uPassword = uPassword;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }
}

