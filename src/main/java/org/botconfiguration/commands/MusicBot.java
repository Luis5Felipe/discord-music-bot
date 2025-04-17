package org.botconfiguration.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class MusicBot extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String comando = event.getMessage().getContentRaw();
        System.out.println("o comando chegou aqui"+ comando);
        if (!event.isFromGuild()) return;
        if (!event.getMessage().getContentRaw().startsWith("!play")) {
            System.out.println("Bot foi chamado");
            return;
        }
        if (!event.getAuthor().isBot());
        Guild guild = event.getGuild();

        VoiceChannel channel = guild.getVoiceChannels().getFirst();
        AudioManager manager = guild.getAudioManager();
       //manager.setSendingHandler(new MySendHandler());
        manager.openAudioConnection(channel);

    }
}
