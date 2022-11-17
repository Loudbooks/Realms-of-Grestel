package com.loudbook.dev.claim.commands

import com.github.javafaker.Faker
import com.loudbook.dev.api.GrestelPlayer
import com.loudbook.dev.PlayerManager
import com.loudbook.dev.claim.Claim
import com.loudbook.dev.claim.ClaimManager
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class ClaimCommand(private val claimManager: ClaimManager, private val playerManager: PlayerManager) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (playerManager.getPlayer(sender as Player) == null) return true

        if (args.isNotEmpty()){
            if (args[0] == "kick"){
                val grestelPlayer: GrestelPlayer = playerManager.getPlayer(sender)!!
                if (claimManager.getClaimByPlayer(grestelPlayer) != null && claimManager.getClaimByPlayer(grestelPlayer)!!.owner == grestelPlayer.player.uniqueId){
                    val claim = claimManager.getClaimByPlayer(grestelPlayer)!!
                    val player = Bukkit.getPlayer(args[0])
                    if (player == null || !playerManager.players.containsKey(player)) {
                        sender.sendMessage("${ChatColor.RED}That player is not online/valid!")
                        return true
                    }
                    return if (claim.players.contains(playerManager.getPlayer(player)?.player?.uniqueId)){
                        claim.removePlayer(playerManager.getPlayer(player)!!)
                        sender.sendMessage("${ChatColor.GREEN}You have kicked ${player.name} from your claim!")
                        player.sendMessage("${ChatColor.RED}You have been kicked from ${claim.name}!")
                        for (player1 in claim.players) {
                            if (player1 != grestelPlayer.player.uniqueId) {
                                val possiblePlayer = Bukkit.getPlayer(player1)
                                possiblePlayer?.sendMessage("${ChatColor.GREEN}${player.name} has been kicked from ${claim.name}!")
                            }
                        }
                        true
                    } else {
                        sender.sendMessage("${ChatColor.RED}That player is not in your claim!")
                        true
                    }
                }
            } else if (args[0] == "rename"){
                val grestelPlayer: GrestelPlayer = playerManager.getPlayer(sender)!!
                if (claimManager.getClaimByPlayer(grestelPlayer) != null && claimManager.getClaimByPlayer(grestelPlayer)!!.owner == grestelPlayer.player.uniqueId){
                    val claim = claimManager.getClaimByPlayer(grestelPlayer)!!
                    return if (args[1].length < 15){
                        claim.name = args[1]
                        sender.sendMessage("${ChatColor.GREEN}You have renamed your claim to \"${claim.name}\"!")
                        for (player in claim.players) {
                            if (player != grestelPlayer.player.uniqueId) {
                                val possiblePlayer = Bukkit.getPlayer(player)
                                possiblePlayer?.sendMessage("${ChatColor.GREEN}${sender.name} has renamed the claim to \"${claim.name}\"!")
                            }
                        }
                        true
                    } else {
                        sender.sendMessage("${ChatColor.RED}That name is too long!")
                        true
                    }
                }
            } else if (args[0] == "invite"){
                val grestelPlayer: GrestelPlayer = playerManager.getPlayer(sender)!!
                if (claimManager.getClaimByPlayer(grestelPlayer) != null && claimManager.getClaimByPlayer(grestelPlayer)!!.owner == grestelPlayer.player.uniqueId) {
                    val claim = claimManager.getClaimByPlayer(grestelPlayer)!!
                    val player = Bukkit.getPlayer(args[1])
                    if (player == null || playerManager.getPlayer(player) == null) {
                        sender.sendMessage("${ChatColor.RED}That player is not online/valid!")
                        return true
                    }
                    if (claim.players.contains(playerManager.getPlayer(player)?.player?.uniqueId)){
                        sender.sendMessage("${ChatColor.RED}That player is already in your claim!")
                        return true
                    }
                    player.sendMessage("${ChatColor.GREEN}You have been invited to ${claim.name} by ${sender.name}!")
                    val id = UUID.randomUUID()
                    val textComponent = TextComponent("${ChatColor.GREEN}[Accept]")
                    textComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept $id")
                    player.spigot().sendMessage(textComponent)
                    claimManager.invites[id] = claim
                    return true
                }
            } else if (args[0] == "leave"){
                val grestelPlayer: GrestelPlayer = playerManager.getPlayer(sender)!!
                if (claimManager.getClaimByPlayer(grestelPlayer) != null){
                    val claim = claimManager.getClaimByPlayer(grestelPlayer)!!
                    if (claim.owner == grestelPlayer.player.uniqueId){
                        sender.sendMessage("${ChatColor.RED}You cannot leave your own claim!")
                        return true
                    }
                    claim.removePlayer(grestelPlayer)
                    sender.sendMessage("${ChatColor.GREEN}You have left ${claim.name}!")
                    for (player in claim.players) {
                        if (player != grestelPlayer.player.uniqueId) {
                            val possiblePlayer = Bukkit.getPlayer(player)
                            possiblePlayer?.sendMessage("${ChatColor.GREEN}${sender.name} has left ${claim.name}!")
                        }
                    }
                    return true
                }
            } else if (args[0] == "disband"){
                val grestelPlayer: GrestelPlayer = playerManager.getPlayer(sender)!!
                if (claimManager.getClaimByPlayer(grestelPlayer) != null && claimManager.getClaimByPlayer(grestelPlayer)!!.owner == grestelPlayer.player.uniqueId){
                    val claim = claimManager.getClaimByPlayer(grestelPlayer)!!
                    claimManager.claims.remove(claim)
                    sender.sendMessage("${ChatColor.GREEN}You have disbanded your claim!")
                    for (player in claim.players) {
                        if (player != grestelPlayer.player.uniqueId) {
                            val possiblePlayer = Bukkit.getPlayer(player)
                            possiblePlayer?.sendMessage("${ChatColor.GREEN}${sender.name} has disbanded ${claim.name}!")
                        }
                    }
                    return true
                }
            } else if (args[0] == "auto"){
                val grestelPlayer: GrestelPlayer = playerManager.getPlayer(sender)!!
                if (claimManager.getClaimByPlayer(grestelPlayer) != null && claimManager.getClaimByPlayer(grestelPlayer)!!.owner == grestelPlayer.player.uniqueId){
                    if (claimManager.autos.contains(grestelPlayer)){
                        claimManager.autos.remove(grestelPlayer)
                        sender.sendMessage("${ChatColor.GREEN}You have disabled auto claim.")
                        return true
                    }
                    val claim = claimManager.getClaimByPlayer(grestelPlayer)!!
                    if (claim.numberOfChunksAvailable > 0) {
                        claimManager.autos.add(grestelPlayer)
                    } else {
                        sender.sendMessage("${ChatColor.RED}You do not have any chunks available to auto claim!")
                    }
                    return true
                }
            }
        } else {
            val grestelPlayer: GrestelPlayer = playerManager.getPlayer(sender)!!
            if (claimManager.getClaimByPlayer(grestelPlayer) != null) {
                val claim = claimManager.getClaimByPlayer(grestelPlayer)!!
                if (claim.owner == grestelPlayer.player.uniqueId) {
                    if (claim.numberOfChunksAvailable == 0) {
                        sender.sendMessage("${ChatColor.RED}You have no chunks available to claim!")
                        return true
                    }
                    if (claimManager.getClaimByBlock(sender.location.block) != null) {
                        sender.sendMessage("${ChatColor.RED}You are already in a claim!")
                        return true
                    }
                    claim.chunks.add(Pair(sender.location.block.x.toDouble(), sender.location.block.z.toDouble()))
                    claim.numberOfChunksAvailable--
                    sender.sendMessage("${ChatColor.GREEN}You have claimed this chunk in the name of ${claim.name}! (${claim.numberOfChunksAvailable} chunks left)")
                } else {
                    sender.sendMessage("${ChatColor.RED}You are not the owner of this claim!")
                }
            } else {
                val faker = Faker()
                val name = faker.funnyName().name()
                val claim = Claim(grestelPlayer.player.uniqueId, 5, name, claimManager)
                claim.chunks.add(Pair(sender.location.block.x.toDouble(), sender.location.block.z.toDouble()))
                claim.numberOfChunksAvailable--
                claimManager.claims.add(claim)
                sender.sendMessage("${ChatColor.GREEN}You have claimed this chunk in the name of \"${claim.name}\"! You can rename it with /claim rename <name>.")
                claim.addPlayer(grestelPlayer)
            }
        }
        return true
    }

}