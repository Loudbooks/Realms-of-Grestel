package com.loudbook.dev

import com.loudbook.dev.api.GrestelPlayer
import org.bukkit.entity.Player

class PlayerManager {
    var players: MutableMap<Player, GrestelPlayer> = HashMap()
    fun getPlayer(player: Player): GrestelPlayer? {
        if (players.containsKey(player)) {
            return players[player]
        } else {
            println("PLAYER IS NULL!")
        }
        return null
    }
    fun addPlayer(player: Player) {
        players[player] = GrestelPlayer(player)
    }
    fun removePlayer(player: Player) {
        players.remove(player)
    }
}