package org.botconfiguration.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.botconfiguration.commands.MusicPlay.Music;
import org.botconfiguration.commands.MusicPlay.TrackScheduler;
import org.jetbrains.annotations.NotNull;

public class MusicBotOn extends ListenerAdapter {
    private final AudioPlayerManager playerManager;

    public MusicBotOn(AudioPlayerManager playerManager) {
        AudioSourceManagers.registerRemoteSources(playerManager);
        this.playerManager = playerManager;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;
        if (event.getUser().isBot()) return;

        String command = event.getName();

        if (command.equalsIgnoreCase("play")) {
            String link = event.getOption("link").getAsString();

            GuildVoiceState voiceState = event.getMember().getVoiceState();
            if (voiceState == null || !voiceState.inAudioChannel()) {
                event.reply("Por favor, se conecte a um canal de áudio para utilizar o bot.").queue();
                return;
            }

            Guild guild = event.getGuild();
            AudioChannel channel = voiceState.getChannel();
            AudioManager manager = guild.getAudioManager();

            AudioPlayer player = playerManager.createPlayer();
            TrackScheduler scheduler = new TrackScheduler(player);
            player.addListener(scheduler);

            manager.setSendingHandler(new Music(player));
            guild.getAudioManager().openAudioConnection(channel);

            playerManager.loadItem(link, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack audioTrack) {
                    scheduler.queue(audioTrack);
                    event.reply("🎵 Tocando: **" + audioTrack.getInfo().title + "**").queue();
                }

                @Override
                public void playlistLoaded(AudioPlaylist audioPlaylist) {
                    AudioTrack firstTrack = audioPlaylist.getSelectedTrack() != null ? audioPlaylist.getSelectedTrack() : audioPlaylist.getTracks().get(0);
                    scheduler.queue(firstTrack);
                    event.reply("🎶 Tocando da playlist: **" + firstTrack.getInfo().title + "**").queue();
                }

                @Override
                public void noMatches() {
                    event.reply("❌ Nenhuma música encontrada para esse link.").queue();
                }

                @Override
                public void loadFailed(FriendlyException e) {
                    e.printStackTrace();
                    event.reply("⚠️ Erro ao carregar a música: " + e.getMessage()).queue();
                }

            });
        }
    }
}