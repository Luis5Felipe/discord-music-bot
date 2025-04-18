package org.botconfiguration.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class MusicBotOff extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;
        if (event.getUser().isBot()) return;
        String command = event.getName();
        if (command.equalsIgnoreCase("stop")) {
            AudioManager audioManager = event.getGuild().getAudioManager();
            if (!audioManager.isConnected()) {
                event.reply("Eu preciso está em um Canal de voz para realizar esté comando"
                            + "\n PorFavor Digite /play para ligar o bot").queue();
                return;
            }
            Guild guild = event.getGuild();
            guild.getAudioManager().closeAudioConnection();
            event.reply("Sai do canal de voz").queue();
        }
    }
}
