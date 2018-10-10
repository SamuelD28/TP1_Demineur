package com.example.a1738253.tp1;

class Settings {
    private static final Settings ourInstance = new Settings();

    static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {
    }
}
