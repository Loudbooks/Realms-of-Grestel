package com.loudbook.dev.claim.tabcomplete

import com.loudbook.dev.PlayerManager
import com.loudbook.dev.claim.ClaimManager
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class ClaimTabComplete(private val playerManager: PlayerManager, private val claimManager: ClaimManager) : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (args.size == 1){
            val grestelPlayer = playerManager.getPlayer(sender as Player)
            val claim = claimManager.getClaimByPlayer(grestelPlayer!!)
            if (claim != null){
                return if (claim.owner == grestelPlayer){
                    mutableListOf("rename", "invite", "kick", "auto", "disband")
                } else {
                    mutableListOf("leave")
                }
            }  else {
                println("claim == null")
            }
        } else {
            print("args.size != 1")
        }
        return null
    }
}