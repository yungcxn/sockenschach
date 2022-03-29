package de.can.sockenschach.util;

public class Logger {

    public static void log(String str){
        safePrintln(str);
    }

    public static void safePrintln(String s){
        synchronized (System.out) {
            System.out.println(s);
        }
    }

    public static void logWithPrefix(String pre, String str){
        log("[" + pre + "]" + " " + str);
    }

    public static void error(String msg, String stacktrace){
        log("[ERROR] " + msg + "\n" + stacktrace);
    }

    public static void error(String stacktrace){
        error(stacktrace, "");
    }

    public static void netLog(String str){
        logWithPrefix("Network", str);
    }

    public static void gfxLog(String str){
        logWithPrefix("GFX", str);
    }

    public static void gameLog(String str){
        logWithPrefix("Game", str);
    }

}
