import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.botconfiguration.ReadyListener;
import org.botconfiguration.commands.Ping;
import org.botconfiguration.token.Token;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JDA jda = JDABuilder.createDefault(Token.get())
                .enableIntents(
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MEMBERS
                )
                .setActivity(Activity.playing("/help"))
                .addEventListeners(new ReadyListener(), new Ping())
                .build();
        jda.awaitReady();

    }
}
