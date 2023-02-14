package net.valkyrienmissiles;

public class Main {
    public static final String MOD_ID = "valkyrienmissiles";
    private static final boolean loggingEnabled = true;

    public static void init() {
        VMBlocks.Register();
        VMItems.Register();

        Info("Initialized!");
    }

    public static void Info(String message) {
        if (!loggingEnabled) return;

        System.out.println("[INFO] (" + MOD_ID +  ")" + message);
    }
    public static void Error(String message) {
        if (!loggingEnabled) return;

        System.out.println("[ERROR] (" + MOD_ID +  ")" + message);
    }
}
