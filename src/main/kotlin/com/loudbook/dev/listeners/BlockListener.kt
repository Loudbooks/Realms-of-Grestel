package com.loudbook.dev.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class BlockListener : Listener {
    @EventHandler
    fun onPlace(e: PlayerInteractEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun onBreak(e: BlockBreakEvent) {
        e.isCancelled = true
    }
    @EventHandler
    fun onSlap(e: EntityDamageByEntityEvent) {
        if (e.damager !is Player) return
        e.isCancelled = true
    }
}