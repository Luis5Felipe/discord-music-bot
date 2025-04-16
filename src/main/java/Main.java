import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.botconfiguration.ReadyListener;
import org.botconfiguration.token.Token;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JDA jda = JDABuilder.createDefault(Token.get())
                .addEventListeners(new ReadyListener())
                .build();
        jda.awaitReady();
    }
}
