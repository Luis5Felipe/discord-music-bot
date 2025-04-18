package org.botconfiguration.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MusicBotOn extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;
        if (event.getUser().isBot()) return;
        String command = event.getName();
        if (command.equalsIgnoreCase("play")) {
            GuildVoiceState voiceState = event.getMember().getVoiceState();
            if (voiceState == null || !voiceState.inAudioChannel()) {
                event.reply("Por favor, se conecte a um canal de áudio para utilizar o bot.").queue();
                return;
            }
            AudioChannel channel = voiceState.getChannel();
            Guild guild = event.getGuild();
            guild.getAudioManager().openAudioConnection(channel);
            event.reply("Entrei no canal de voz: " + channel.getName()).queue();
        }
    }
}
