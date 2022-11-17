package com.loudbook.dev.listeners

import com.loudbook.dev.PlayerManager
import com.loudbook.dev.claim.ClaimManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class BlockListener(private val claimManager: ClaimManager, private val playerManager: PlayerManager) : Listener {
    @EventHandler
    fun onPlace(e: PlayerInteractEvent) {
        if (e.clickedBlock == null) return
        if (claimManager.getClaimByBlock(e.clickedBlock!!) != null){
            if (!claimManager.getClaimByBlock(e.clickedBlock!!)!!.players.contains(playerManager.getPlayer(e.player)!!)){
                e.isCancelled = true
            }
        }
    }
    @EventHandler
    fun onBreak(e: BlockBreakEvent) {
        if (claimManager.getClaimByBlock(e.block) != null){
            if (!claimManager.getClaimByBlock(e.block)!!.players.contains(playerManager.getPlayer(e.player)!!)){
                e.isCancelled = true
            }
        }
    }
    @EventHandler
    fun onSlap(e: EntityDamageByEntityEvent) {
        if (e.damager !is Player) return
        if (e.damager !is Player) return

        if (claimManager.getClaimByBlock(e.damager.location.block) != null){
            if (!claimManager.getClaimByBlock(e.damager.location.block)!!.players.contains(playerManager.getPlayer(e.damager as Player)!!)){

                e.isCancelled = true
            }
        }
        if (e.entity is Player){
            if (!claimManager.getClaimByBlock(e.entity.location.block)!!.players.contains(playerManager.getPlayer(e.entity as Player)!!)){
                e.isCancelled = true
            }
        }
    }
}