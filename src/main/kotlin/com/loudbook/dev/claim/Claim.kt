package com.loudbook.dev.claim

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player

class Claim(
    pos1: Location,
    pos2: Location,
    owner: Player,
    players: List<Player>) {

    val blocks: List<Block> = ArrayList()

}