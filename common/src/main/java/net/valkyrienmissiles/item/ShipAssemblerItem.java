package net.valkyrienmissiles.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.valkyrienmissiles.ShipAssembler;

public class ShipAssemblerItem extends Item {
    public ShipAssemblerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        ServerLevel level = ShipAssembler.getServerLevel(useOnContext.getLevel());
        if (level == null) return super.useOn(useOnContext);

        ShipAssembler.createShipFromBlocks(level, useOnContext.getClickedPos());

        return InteractionResult.SUCCESS;
    }

    @Override
    public Component getDescription() {
        return Component.nullToEmpty("Flood-fills an area starting from the right clicked block.\nIgnores air.");
    }
}
