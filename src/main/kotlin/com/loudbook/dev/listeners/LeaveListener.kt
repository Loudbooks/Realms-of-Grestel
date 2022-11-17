package com.loudbook.dev.listeners

import com.loudbook.dev.api.PlayerManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent


class LeaveListener(private val playerManager: PlayerManager) : Listener {
    @EventHandler
    fun onLeave(e: PlayerQuitEvent) {
        playerManager.removePlayer(e.player)
    }
}