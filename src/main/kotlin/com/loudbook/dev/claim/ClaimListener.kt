package com.loudbook.dev.claim

import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class ClaimListener : Listener {
    fun onClick(e: PlayerInteractEvent){
        if (e.player.inventory.itemInMainHand.type == Material.STICK) {
            if (e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK) {
                e.player.sendMessage("Left click")
            } else if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
                e.player.sendMessage("Right click")
            }
        }
    }
}