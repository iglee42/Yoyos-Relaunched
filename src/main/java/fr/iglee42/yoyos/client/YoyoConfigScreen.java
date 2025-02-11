package fr.iglee42.yoyos.client;

import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import fr.iglee42.yoyos.common.init.YoyosEntities;
import fr.iglee42.yoyos.network.ToggleYoyoAttackC2S;
import fr.iglee42.yoyos.network.ToggleYoyoEnchantC2S;
import fr.iglee42.yoyos.network.YoyosNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;

public class YoyoConfigScreen extends Screen {
    protected YoyoConfigScreen() {
        super(Component.literal(""));
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new ExpandingWidget(width / 2 -1,height / 2 - 88,40,40,2,widget->{
            YoyosNetwork.CHANNEL.sendToServer(new ToggleYoyoAttackC2S(InteractionHand.MAIN_HAND));
        },()-> YoyoItem.isAttackEnable(minecraft.player.getMainHandItem()),Items.DIAMOND_SWORD,
                Component.translatable("tooltip.yoyos.attack"),()-> true));
        addRenderableWidget(new ExpandingWidget(width / 2 -1,height / 2 - 44,40,40,2,widget->{
            YoyosNetwork.CHANNEL.sendToServer(new ToggleYoyoEnchantC2S(YoyosEnchantments.COLLECTING.getId(), InteractionHand.MAIN_HAND));
        },()-> YoyoItem.isEnchantmentEnable(minecraft.player.getMainHandItem(), YoyosEnchantments.COLLECTING.getId()),Items.HOPPER,
                Component.translatable(YoyosEnchantments.COLLECTING.get().getDescriptionId()),()-> ((YoyoItem)Minecraft.getInstance().player.getMainHandItem().getItem()).getMaxCollectedDrops(Minecraft.getInstance().player.getMainHandItem()) > 0));
        addRenderableWidget(new ExpandingWidget(width / 2 -1,height / 2 + 1,40,40,2,widget->{
            YoyosNetwork.CHANNEL.sendToServer(new ToggleYoyoEnchantC2S(YoyosEnchantments.BREAKING.getId(), InteractionHand.MAIN_HAND));
        },()-> YoyoItem.isEnchantmentEnable(minecraft.player.getMainHandItem(), YoyosEnchantments.BREAKING.getId()), Items.DIAMOND_PICKAXE,
                Component.translatable(YoyosEnchantments.BREAKING.get().getDescriptionId()),()-> Minecraft.getInstance().player.getMainHandItem().getEnchantmentLevel(YoyosEnchantments.BREAKING.get()) > 0 && !YoyoItem.isEnchantmentEnable(minecraft.player.getMainHandItem(), YoyosEnchantments.CRAFTING.getId())));
        addRenderableWidget(new ExpandingWidget(width / 2 -1,height / 2 + 46,40,40,2,widget->{
            YoyosNetwork.CHANNEL.sendToServer(new ToggleYoyoEnchantC2S(YoyosEnchantments.CRAFTING.getId(), InteractionHand.MAIN_HAND));
        },()-> YoyoItem.isEnchantmentEnable(minecraft.player.getMainHandItem(), YoyosEnchantments.CRAFTING.getId()), Items.CRAFTING_TABLE,
                Component.translatable(YoyosEnchantments.CRAFTING.get().getDescriptionId()),()-> Minecraft.getInstance().player.getMainHandItem().getEnchantmentLevel(YoyosEnchantments.CRAFTING.get()) > 0 && !YoyoItem.isEnchantmentEnable(minecraft.player.getMainHandItem(), YoyosEnchantments.BREAKING.getId())));
    }

    @Override
    public void tick() {
        super.tick();
        children().stream().filter(w-> w instanceof ExpandingWidget).map(w->((ExpandingWidget)w)).forEach(ExpandingWidget::tick);
    }

    @Override
    public void mouseMoved(double p_94758_, double p_94759_) {
        children().stream().filter(w-> w instanceof ExpandingWidget).map(w->((ExpandingWidget)w)).forEach(w->w.mouseMoved(p_94758_, p_94759_));

        super.mouseMoved(p_94758_, p_94759_);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int p_96552_, int p_96553_, int p_96554_) {
        if (YoyosKeybindings.OPEN_CONFIG.getKey().getValue() == p_96552_){
            onClose();
            return true;
        }
        return super.keyPressed(p_96552_, p_96553_, p_96554_);
    }
}
