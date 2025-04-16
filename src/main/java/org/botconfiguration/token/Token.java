package org.botconfiguration.token;
import io.github.cdimascio.dotenv.Dotenv;

public class Token {
    private static final Dotenv dotenv = Dotenv.load();

    public static String get(){
        return dotenv.get("BOT_TOKEN");
    }

}
