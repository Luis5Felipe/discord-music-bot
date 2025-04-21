package org.botconfiguration.commands;

import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Ping extends ListenerAdapter {
    private volatile Long ping = -1L;

    @Override
    public void onGatewayPing(@NotNull GatewayPingEvent event) {
        this.ping = event.getNewPing();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;
        if (event.getUser().isBot()) return;
        if (!event.getName().equalsIgnoreCase("ping")) return;
        try {
            event.reply("Ping " + ping + "ms").queue();
        } catch (Exception e) {
            event.reply("Não foi possível obter o ping.").queue();
            e.printStackTrace();
        }
    }
}
