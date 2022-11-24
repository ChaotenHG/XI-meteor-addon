package xyz.chaoten.ximeteor.utils

import meteordevelopment.meteorclient.MeteorClient
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
import xyz.chaoten.ximeteor.utils.maxin.ClientConnectionInvoker

object PacketHelper {

    val conn : ClientConnectionInvoker = MeteorClient.mc.player!!.networkHandler.connection as ClientConnectionInvoker
}
