import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.botconfiguration.Listener.CommandRegister;
import org.botconfiguration.token.ReadyListener;
import org.botconfiguration.commands.MusicBotOff;
import org.botconfiguration.commands.MusicBotOn;
import org.botconfiguration.commands.Ping;
import org.botconfiguration.token.Token;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        JDA jda = JDABuilder.createDefault(Token.get())
                .enableIntents(
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MEMBERS
                )
                .setActivity(Activity.playing("/help"))
                .addEventListeners(new ReadyListener(),
                        new CommandRegister(),
                        new Ping(),
                        new MusicBotOn(playerManager),
                        new MusicBotOff()
                )
                .build();
        jda.awaitReady();

    }
}
