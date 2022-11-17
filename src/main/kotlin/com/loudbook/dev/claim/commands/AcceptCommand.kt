package com.loudbook.dev.claim.commands

import com.loudbook.dev.api.PlayerManager
import com.loudbook.dev.claim.ClaimManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class AcceptCommand(private val claimManager: ClaimManager, private val playerManager: PlayerManager) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return true
        val id = args[0]
        if (claimManager.invites.containsKey(UUID.fromString(id))){
            val claim = claimManager.invites[UUID.fromString(id)]!!
            if (playerManager.getPlayer(sender as Player)!!.claim != null){
                sender.sendMessage("${ChatColor.RED}You are already in a claim!")
                return true
            }
            claim.addPlayer((playerManager.getPlayer(sender)!!))
            claimManager.invites.remove(UUID.fromString(id))
            sender.sendMessage("${ChatColor.GREEN}You have joined ${claim.name}!")
            for (player in claim.players) {
                val possiblePlayer = Bukkit.getPlayer(player)
                possiblePlayer?.sendMessage("${ChatColor.GREEN}${sender.name} has joined ${claim.name}!")
            }
        } else {
            sender.sendMessage("${ChatColor.RED}This invite isn't valid! If this is a mistake, please contact an admin!")
        }
        return true
    }
}