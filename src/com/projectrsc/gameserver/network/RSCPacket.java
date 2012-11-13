package com.projectrsc.gameserver.network;

import com.projectrsc.shared.network.Packet;
import com.projectrsc.shared.network.Session;

public final class RSCPacket extends Packet {

   private final int packetId;

   public RSCPacket(Session session, int pID, byte[] pData) {
       this(session, pID, pData, false);
   }

   public RSCPacket(Session session, int pID, byte[] pData, boolean bare) {
       super(session, pData, bare);
       this.packetId = pID;
   }

   public int getID() {
       return packetId;
   }
   
   @Override
   public String toString() {
	   return "RSCPacket(" + packetId + ")";
   }
   
}