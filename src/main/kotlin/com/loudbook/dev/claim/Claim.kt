package com.loudbook.dev.claim

import com.loudbook.dev.api.GrestelPlayer
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.entity.Player
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class Claim(

    var owner: UUID,
    var numberOfChunksAvailable: Int,
    var name: String,
    private val claimManager: ClaimManager) : Serializable
{

    var chunks: MutableList<Pair<Double, Double>> = ArrayList()
    var players: MutableList<UUID> = ArrayList()


    fun addPlayer(grestelPlayer: GrestelPlayer) {
        players.add(grestelPlayer.player.uniqueId)
        if (players.size % 5 == 0) {
            numberOfChunksAvailable += 20
        } else {
            numberOfChunksAvailable++
        }
        claimManager.saveClaims()
    }

    fun removePlayer(grestelPlayer: GrestelPlayer) {
        val owner = Bukkit.getPlayer(owner)
        if (players.size % 5 == 0) {
            if (numberOfChunksAvailable < 20) {
                for (i in 0..20) {
                    if (numberOfChunksAvailable == 0) {
                        chunks.removeAt(chunks.size - 1)
                        owner?.sendMessage("A chunk at ${getChunk(chunks[chunks.size - 1])?.x}, ${getChunk(chunks[chunks.size - 1])?.z} has been removed from your claim!")
                    } else {
                        numberOfChunksAvailable--
                    }
                }
                owner?.sendMessage("You have lost 20 chunks, but you don't have that many available! Some random chunks were removed!")
            }
            numberOfChunksAvailable -= 20
        }
        if (numberOfChunksAvailable < 1) {
            chunks.removeAt(chunks.size - 1)
            owner?.sendMessage("A chunk at ${getChunk(chunks[chunks.size - 1])?.x}, ${getChunk(chunks[chunks.size - 1])?.z} has been removed from your claim!")
        }
        numberOfChunksAvailable--
        players.remove(grestelPlayer.player.uniqueId)
        for (player in players) {
            val possiblePlayer = Bukkit.getPlayer(player)
            possiblePlayer?.sendMessage("${grestelPlayer.player.name} has left the claim!")
        }
        claimManager.saveClaims()
    }

    fun getChunk(x: Double, z: Double): Chunk? {
        val world = Bukkit.getWorld("world")
        return world?.getChunkAt(Location(world, x, 0.0, z))
    }
    fun getChunk(pair: Pair<Double, Double>): Chunk? {
        val world = Bukkit.getWorld("world")
        return world?.getChunkAt(Location(world, pair.first, 0.0, pair.second))
    }
}