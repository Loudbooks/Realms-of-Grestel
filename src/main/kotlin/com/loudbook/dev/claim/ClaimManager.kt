package com.loudbook.dev.claim

import com.loudbook.dev.api.ClaimSerialized
import com.loudbook.dev.api.GrestelPlayer
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.block.Block
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ClaimManager {
    val claims: MutableList<Claim> = ArrayList()
    val invites: MutableMap<UUID, Claim> = HashMap()
    val autos: MutableList<GrestelPlayer> = ArrayList()
    fun getClaimByBlock(block: Block): Claim? {
        for (claim in claims) {
            if (claim.chunks.contains(Pair(block.x.toDouble(), block.z.toDouble()))) {
                return claim
            }
        }
        return null
    }

    fun getClaimByPlayer(grestelPlayer: GrestelPlayer): Claim?{
        for (claim in claims) {
            if (claim.players.contains(grestelPlayer.player.uniqueId) || (claim.owner == grestelPlayer.player.uniqueId)) {
                return claim
            }
        }

        return null
    }

    fun getClaimByChunk(chunk: Chunk): Claim? {
        for (claim in claims) {
            if (claim.chunks.contains(Pair(chunk.x.toDouble(), chunk.z.toDouble()))) {
                return claim
            }
        }
        return null
    }
    fun saveClaims() {
        val fileOutputStream = FileOutputStream("plugins/Grestel/claims.db")
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        val claimsSerialized: MutableList<ClaimSerialized> = ArrayList()
        for (claim in this.claims) {
            val claimSerialized = ClaimSerialized(claim.players, claim.owner, claim.chunks, claim.name, claim.numberOfChunksAvailable)
            claimsSerialized.add(claimSerialized)
        }
        objectOutputStream.writeObject(claimsSerialized)
        objectOutputStream.close()
        fileOutputStream.close()
        println("Saved claims!")

    }
    fun getChunk(x: Int, z: Int): Chunk? {
        val world = Bukkit.getWorld("world")
        return world?.getChunkAt(Location(world, x.toDouble(), 0.0, z.toDouble()))
    }
}