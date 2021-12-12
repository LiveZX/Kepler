package org.alexdev.kepler.messages.incoming.rooms.teleporter;

import org.alexdev.kepler.dao.mysql.ItemDao;
import org.alexdev.kepler.game.item.Item;
import org.alexdev.kepler.game.item.base.ItemBehaviour;
import org.alexdev.kepler.game.player.Player;
import org.alexdev.kepler.game.room.Room;
import org.alexdev.kepler.game.room.RoomManager;
import org.alexdev.kepler.messages.outgoing.rooms.items.BROADCAST_TELEPORTER;
import org.alexdev.kepler.messages.types.MessageEvent;
import org.alexdev.kepler.server.netty.streams.NettyRequest;

public class DOORGOIN implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        int itemId = Integer.parseInt(reader.contents());
        var item = player.getRoomUser().getRoom().getItemManager().getByVirtualId(itemId);

        if (item == null) {
            return;
        }

        if (!player.getRoomUser().getAuthenticateTelporterId().equals(item.getDatabaseId())) {
            return;
        }

        player.getRoomUser().getRoom().send(new BROADCAST_TELEPORTER(item, player.getDetails().getName(), false));
    }
}
