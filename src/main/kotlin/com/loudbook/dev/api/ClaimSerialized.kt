package com.loudbook.dev.api

import java.io.Serializable
import java.util.*

class ClaimSerialized(
    val uuids: MutableList<UUID>,
    val owner: UUID,
    val chunks: MutableList<Pair<Double, Double>>,
    val name: String,
    val numberOfChunksAvailable: Int
    ) : Serializable {
}