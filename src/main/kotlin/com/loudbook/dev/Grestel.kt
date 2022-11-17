package com.loudbook.dev

import com.loudbook.dev.api.ClaimSerialized
import com.loudbook.dev.claim.Claim
import com.loudbook.dev.claim.commands.ClaimCommand
import com.loudbook.dev.claim.ClaimManager
import com.loudbook.dev.claim.commands.AcceptCommand
import com.loudbook.dev.claim.commands.UnclaimCommand
import com.loudbook.dev.claim.tabcomplete.ClaimTabComplete
import com.loudbook.dev.listeners.BlockListener
import com.loudbook.dev.listeners.JoinListener
import com.loudbook.dev.listeners.LeaveListener
import com.loudbook.dev.listeners.MoveListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

class Grestel : JavaPlugin() {
    private val playerManager = PlayerManager()
    private val claimManager = ClaimManager()
    override fun onEnable() {
        println("Grestel has started!")



        Bukkit.getPluginManager().registerEvents(BlockListener(claimManager, playerManager), this)
        Bukkit.getPluginManager().registerEvents(JoinListener(playerManager), this)
        Bukkit.getPluginManager().registerEvents(LeaveListener(playerManager), this)
        Bukkit.getPluginManager().registerEvents(MoveListener(claimManager, playerManager), this)


        this.getCommand("claim")!!.setExecutor(ClaimCommand(claimManager, playerManager))
        this.getCommand("accept")!!.setExecutor(AcceptCommand(claimManager, playerManager))
        this.getCommand("unclaim")!!.setExecutor(UnclaimCommand(claimManager, playerManager))
        this.getCommand("claim")!!.tabCompleter = ClaimTabComplete(playerManager, claimManager)
        val fileInputStream = FileInputStream(File("plugins/Grestel/claims.db"))
        val objectInputStream = ObjectInputStream(fileInputStream)
        try {
            val claims: MutableList<ClaimSerialized> = objectInputStream.readObject() as ArrayList<ClaimSerialized>
            println("   ")
            println("-------------------------")
            println("Loading Claims...")
            println("   ")
            for (claim in claims) {
                val finalClaim = Claim(claim.owner, claim.numberOfChunksAvailable, claim.name, claimManager)
                finalClaim.players = claim.uuids
                finalClaim.chunks = claim.chunks
                claimManager.claims.add(finalClaim)
                println(" - Added claim ${claim.name} to claimManager")
            }
            println("   ")
            println("All claims have been loaded.")
            println("-------------------------")
            println("   ")
        } catch (e: EOFException) {
            println("Done importing!")
        }

        println("\n" +
                "   _____               _       _ \n" +
                "  / ____|             | |     | |\n" +
                " | |  __ _ __ ___  ___| |_ ___| |\n" +
                " | | |_ | '__/ _ \\/ __| __/ _ \\ |\n" +
                " | |__| | | |  __/\\__ \\ ||  __/ |\n" +
                "  \\_____|_|  \\___||___/\\__\\___|_|\n" +
                "                                 \n" +
                "                                 \n")
        println("By Loudbook")
    }

    override fun onDisable() {
        claimManager.saveClaims()
    }

}