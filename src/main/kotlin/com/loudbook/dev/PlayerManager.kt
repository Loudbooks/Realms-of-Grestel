package com.loudbook.dev

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
        println(players.size)
        players[player] = GrestelPlayer(player)
        println(players.size)
    }
    fun removePlayer(player: Player) {
        players.remove(player)
    }
}