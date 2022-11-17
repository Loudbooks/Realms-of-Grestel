package com.loudbook.dev.listeners

import com.loudbook.dev.api.PlayerManager
import com.loudbook.dev.claim.ClaimManager
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class MoveListener(private val claimManager: ClaimManager, private val playerManager: PlayerManager) : Listener {
    @EventHandler
    fun onMove(e: PlayerMoveEvent){
        val claim = claimManager.getClaimByChunk(e.player.location.chunk)
        if (claim == null){
            if (claimManager.autos.contains(playerManager.getPlayer(e.player)!!)){
                val playerClaim = claimManager.getClaimByPlayer(playerManager.getPlayer(e.player)!!)
                if (playerClaim!!.numberOfChunksAvailable <= 0){
                    e.player.sendMessage("${ChatColor.RED}You have no more chunks available, auto mode has been disabled.")
                    claimManager.autos.remove(playerManager.getPlayer(e.player)!!)
                    return
                }
                if (!playerClaim.chunks.contains(Pair(e.player.location.chunk.x.toDouble(), e.player.location.chunk.z.toDouble()))){
                    playerClaim.numberOfChunksAvailable--
                    playerClaim.chunks.add(Pair(e.player.location.chunk.x.toDouble(), e.player.location.chunk.z.toDouble()))
                    e.player.sendMessage("${ChatColor.GREEN}You have claimed this chunk!")
                }
            }
        }
    }
}