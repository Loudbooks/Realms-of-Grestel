package com.loudbook.dev

import com.loudbook.dev.claim.ClaimListener
import com.loudbook.dev.listeners.BlockListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Grestel : JavaPlugin() {
    override fun onEnable() {
        println("Grestel has started!")
        Bukkit.getPluginManager().registerEvents(BlockListener(), this)
        Bukkit.getPluginManager().registerEvents(ClaimListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}