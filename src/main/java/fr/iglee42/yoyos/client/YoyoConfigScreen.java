package fr.iglee42.yoyos.client;

import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import fr.iglee42.yoyos.network.YoyosNetwork;
import fr.iglee42.yoyos.network.payloads.ToggleAttackC2SPayload;
import fr.iglee42.yoyos.network.payloads.ToggleEnchantmentC2SPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.network.PacketDistributor;

public class YoyoConfigScreen extends Screen {
    protected YoyoConfigScreen() {
        super(Component.literal(""));
    }

    @Override
    protected void init() {
        super.init();
        HolderLookup.Provider provider = minecraft.player.registryAccess();
        HolderLookup.RegistryLookup<Enchantment> enchantments = provider.lookupOrThrow(Registries.ENCHANTMENT);
        addRenderableWidget(new ExpandingWidget(width / 2 -1,height / 2 - 88,40,40,2,widget->{
            PacketDistributor.sendToServer(new ToggleAttackC2SPayload(InteractionHand.MAIN_HAND));
        },()-> YoyoItem.isAttackEnable(minecraft.player.getMainHandItem()),Items.DIAMOND_SWORD,
                Component.translatable("tooltip.yoyos.attack"),()-> true));

        addRenderableWidget(new ExpandingWidget(width / 2 -1,height / 2 - 44,40,40,2,widget->{
            PacketDistributor.sendToServer(new ToggleEnchantmentC2SPayload(InteractionHand.MAIN_HAND, YoyosEnchantments.COLLECTING.location()));
        },()-> YoyoItem.isEnchantmentEnable(minecraft.player.getMainHandItem(), YoyosEnchantments.COLLECTING, provider),Items.HOPPER,
                enchantments.getOrThrow(YoyosEnchantments.COLLECTING).value().description().copy(),()-> ((YoyoItem)Minecraft.getInstance().player.getMainHandItem().getItem()).getMaxCollectedDrops(Minecraft.getInstance().player.getMainHandItem(),minecraft.player.registryAccess()) > 0));

        addRenderableWidget(new ExpandingWidget(width / 2 -1,height / 2 + 1,40,40,2,widget->{
            PacketDistributor.sendToServer(new ToggleEnchantmentC2SPayload(InteractionHand.MAIN_HAND, YoyosEnchantments.BREAKING.location()));
        },()-> YoyoItem.isEnchantmentEnable(minecraft.player.getMainHandItem(), YoyosEnchantments.BREAKING, provider), Items.DIAMOND_PICKAXE,
                Enchantment.getFullname(enchantments.getOrThrow(YoyosEnchantments.BREAKING),1),()-> Minecraft.getInstance().player.getMainHandItem().getEnchantmentLevel(enchantments.getOrThrow(YoyosEnchantments.BREAKING)) > 0 && !YoyoItem.isEnchantmentEnable(minecraft.player.getMainHandItem(), YoyosEnchantments.CRAFTING,minecraft.player.registryAccess())));

        addRenderableWidget(new ExpandingWidget(width / 2 -1,height / 2 + 46,40,40,2,widget->{
            PacketDistributor.sendToServer(new ToggleEnchantmentC2SPayload(InteractionHand.MAIN_HAND, YoyosEnchantments.CRAFTING.location()));
        },()-> YoyoItem.isEnchantmentEnable(minecraft.player.getMainHandItem(), YoyosEnchantments.CRAFTING, provider), Items.CRAFTING_TABLE,
                Enchantment.getFullname(enchantments.getOrThrow(YoyosEnchantments.CRAFTING),1),()-> Minecraft.getInstance().player.getMainHandItem().getEnchantmentLevel(enchantments.getOrThrow(YoyosEnchantments.CRAFTING)) > 0 && !YoyoItem.isEnchantmentEnable(minecraft.player.getMainHandItem(), YoyosEnchantments.BREAKING,minecraft.player.registryAccess())));
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
