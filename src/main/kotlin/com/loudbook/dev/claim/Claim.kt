package com.loudbook.dev.claim

import com.loudbook.dev.GrestelPlayer
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import java.io.Serializable

class Claim(

    var owner: GrestelPlayer,
    var numberOfChunksAvailable: Int,
    var name: String) : Serializable
{

    var chunks: MutableList<Pair<Double, Double>> = ArrayList()
    var players: MutableList<GrestelPlayer> = ArrayList()


    fun addPlayer(grestelPlayer: GrestelPlayer) {
        players.add(grestelPlayer)
        if (players.size % 5 == 0) {
            numberOfChunksAvailable += 20
        } else {
            numberOfChunksAvailable++
        }
    }

    fun removePlayer(grestelPlayer: GrestelPlayer) {
        if (players.size % 5 == 0) {
            if (numberOfChunksAvailable < 20) {
                for (i in 0..20) {
                    if (numberOfChunksAvailable == 0) {
                        chunks.removeAt(chunks.size - 1)
                        owner.player.sendMessage("A chunk at ${getChunk(chunks[chunks.size - 1])?.x}, ${getChunk(chunks[chunks.size - 1])?.z} has been removed from your claim!")
                    } else {
                        numberOfChunksAvailable--
                    }
                }
                owner.player.sendMessage("You have lost 20 chunks, but you don't have that many available! Some random chunks were removed!")
            }
            numberOfChunksAvailable -= 20
        }
        if (numberOfChunksAvailable < 1) {
            chunks.removeAt(chunks.size - 1)
            owner.player.sendMessage("A chunk at ${getChunk(chunks[chunks.size - 1])?.x}, ${getChunk(chunks[chunks.size - 1])?.z} has been removed from your claim!")
        }
        numberOfChunksAvailable--
        players.remove(grestelPlayer)
        for (player in players) {
            player.player.sendMessage("${grestelPlayer.player.name} has left the claim!")
        }
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