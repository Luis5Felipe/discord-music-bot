package org.botconfiguration.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.botconfiguration.commands.MusicPlay.GuildMusicManager;
import org.botconfiguration.commands.MusicPlay.Music;
import org.botconfiguration.commands.MusicPlay.TrackScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MusicBotOn extends ListenerAdapter {
    private final AudioPlayerManager playerManager;
    private TrackScheduler scheduler;
    private Queue<String> musicList = new LinkedList<>();
    private SlashCommandInteractionEvent lastEvent;
    private String nextMusic = null;

    public MusicBotOn(AudioPlayerManager playerManager) {
        AudioSourceManagers.registerRemoteSources(playerManager);
        this.playerManager = playerManager;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("play")) return;

        GuildVoiceState voiceState = event.getMember().getVoiceState();
        if (voiceState == null || !voiceState.inAudioChannel()) {
            event.reply("Entre em um canal de voz primeiro").queue();
            return;
        }

        String link = event.getOption("link").getAsString();
        event.reply("✅ Música adicionada à fila! Posição: " + musicList.size()).queue();

        event.deferReply().queue();
        loadTrack(link, event);
        connectToVoiceChannel(voiceState.getChannel(), event.getGuild());
    }

    private void connectToVoiceChannel(AudioChannel channel, Guild guild) {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);
        AudioManager audioManager = guild.getAudioManager();
        audioManager.setSendingHandler(new Music(musicManager.player));
        audioManager.openAudioConnection(channel);
    }

    private void loadTrack(String url, SlashCommandInteractionEvent event) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
        playerManager.loadItem(url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicManager.scheduler.queue(audioTrack);
                event.getHook().sendMessage("🎵 Adicionado a playlist: **" + audioTrack.getInfo().title + "**").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                AudioTrack firstTrack = audioPlaylist.getSelectedTrack() != null ? audioPlaylist.getSelectedTrack() : audioPlaylist.getTracks().get(0);
                musicManager.scheduler.queue(firstTrack);
                event.getHook().sendMessage("🎶 Tocando da playlist: **" + firstTrack.getInfo().title + "**").queue();
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
    private final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();
    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }
        return musicManager;
    }
}