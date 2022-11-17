package com.loudbook.dev.listeners

import com.loudbook.dev.PlayerManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener(private val playerManager: PlayerManager) : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        playerManager.addPlayer(e.player)
    }
}