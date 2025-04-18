package org.botconfiguration.Listener;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandRegister extends ListenerAdapter {
    private List<CommandData> getCommandList(){
        List<CommandData> commandDataList = new ArrayList<>();
        commandDataList.add(
                Commands.slash("play", "Toca uma música do YouTube")
                        .addOption(OptionType.STRING, "link", "Link da música do YouTube", true)
        );
        commandDataList.add(Commands.slash("stop","/stop desconecta o bot"));
        commandDataList.add(Commands.slash("ping","/ping testa o tempo de resposta do bot"));
        commandDataList.add(Commands.slash("help","/help todos os comandos do bot"));
        return commandDataList;
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        event.getGuild().updateCommands().addCommands(getCommandList()).queue();
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(getCommandList()).queue();
    }
}
