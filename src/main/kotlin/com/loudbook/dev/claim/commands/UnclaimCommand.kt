package com.loudbook.dev.claim.commands

import com.loudbook.dev.api.GrestelPlayer
import com.loudbook.dev.api.PlayerManager
import com.loudbook.dev.claim.ClaimManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class UnclaimCommand(private val claimManager: ClaimManager, private val playerManager: PlayerManager) : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (playerManager.getPlayer(sender as Player) == null) return true
        val grestelPlayer: GrestelPlayer = playerManager.getPlayer(sender)!!
        if (claimManager.getClaimByPlayer(grestelPlayer) != null){
            val claim = claimManager.getClaimByPlayer(grestelPlayer)!!
            if (claim.owner == grestelPlayer.player.uniqueId){
                if (claim.chunks.contains(Pair(sender.location.chunk.x.toDouble(), sender.location.chunk.z.toDouble()))) {
                    claim.chunks.remove(Pair(sender.location.chunk.x.toDouble(), sender.location.chunk.z.toDouble()))
                    claim.numberOfChunksAvailable++
                    sender.sendMessage("${ChatColor.GREEN}You have unclaimed this chunk!")
                } else {
                    sender.sendMessage("${ChatColor.RED}This chunk isn't claimed!")
                }
            } else {
                sender.sendMessage("${ChatColor.RED}You are not the owner of this claim!")
            }
        } else {
            sender.sendMessage("${ChatColor.RED}You are not in a claim!")
        }
        return true
    }
}