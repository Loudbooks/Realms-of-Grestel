package com.loudbook.dev.api

import com.loudbook.dev.claim.Claim
import org.bukkit.entity.Player
import java.io.Serializable

class GrestelPlayer(val player: Player) : Serializable{
    var claim: Claim? = null
}