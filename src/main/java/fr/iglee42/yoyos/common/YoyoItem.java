package fr.iglee42.yoyos.common;

import fr.iglee42.yoyos.client.YoyosKeybindings;
import fr.iglee42.yoyos.common.api.*;
import fr.iglee42.yoyos.common.init.YoyosDataComponents;
import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import fr.iglee42.yoyos.common.init.YoyosItems;
import fr.iglee42.yoyos.common.init.YoyosSounds;
import fr.iglee42.yoyos.network.payloads.HandSyncS2CPayload;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class YoyoItem extends TieredItem implements IYoyo {

    private final Float defaultAttackDamage;
    protected final YoyoFactory factory;
    protected RenderOrientation renderOrientation = RenderOrientation.Vertical;
    protected List<EntityInteraction> entityInteractions = new ArrayList<>();
    protected List<BlockInteraction> blockInteractions = new ArrayList<>();
    protected YoyoTier yoyoTier;

    public YoyoItem(Properties properties, YoyoTier tier, YoyoFactory factory){
        super(tier.getTier(), properties
                .component(DataComponents.TOOL,
                        new Tool(List.of(Tool.Rule.deniesDrops(tier.getTier().getIncorrectBlocksForDrops()),
                                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, tier.getTier().getSpeed()),
                                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_AXE, tier.getTier().getSpeed()),
                                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_HOE, tier.getTier().getSpeed()),
                                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_SHOVEL, tier.getTier().getSpeed())
                        ), 1.0F, 1)));
        this.yoyoTier = tier;
        this.defaultAttackDamage = tier.getTier().getAttackDamageBonus() + 3.0f;
        this.factory = factory;
    }

    public YoyoItem(Properties properties, YoyoTier tier){
        this(properties, tier, YoyoEntity::new);
    }
    public YoyoItem(YoyoTier tier){
        this(new Properties().stacksTo(1), tier);
    }

    public YoyoItem addBlockInteraction(BlockInteraction... interaction){
        Collections.addAll(blockInteractions,interaction);
        return this;
    }

    public YoyoItem addEntityInteraction(EntityInteraction... interaction){
        Collections.addAll(entityInteractions,interaction);
        return this;
    }

    public YoyoItem setRenderOrientation(RenderOrientation renderOrientation) {
        this.renderOrientation = renderOrientation;
        return this;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable TooltipContext p_41422_, List<Component> tooltips, TooltipFlag p_41424_) {
        super.appendHoverText(stack, p_41422_, tooltips, p_41424_);
        tooltips.add(Component.translatable("tooltip.yoyos.weight", getWeight(stack)).withStyle(ChatFormatting.GRAY));
        tooltips.add(Component.translatable("tooltip.yoyos.length", getLength(stack)).withStyle(ChatFormatting.GRAY));

        int duration = getDuration(stack);
        if (duration < 0) tooltips.add(Component.translatable("tooltip.yoyos.duration.infinite").withStyle(ChatFormatting.GRAY));
        else tooltips.add(Component.translatable("tooltip.yoyos.duration", duration / 20f).withStyle(ChatFormatting.GRAY));
        if (getMaxCollectedDrops(stack,p_41422_.registries()) > 0) tooltips.add(Component.translatable("tooltip.yoyos.maxCollectedItems",getMaxCollectedDrops(stack,p_41422_.registries()) ,getMaxCollectedDrops(stack,p_41422_.registries()) / 64).withStyle(ChatFormatting.GRAY));
        tooltips.add(Component.translatable("tooltip.yoyos.openMenu",YoyosKeybindings.OPEN_CONFIG.getTranslatedKeyMessage()).withStyle(ChatFormatting.GRAY));

        tooltips.add(Component.literal(""));

        MutableComponent attackComp = Component.translatable("tooltip.yoyos.attack").withStyle(ChatFormatting.GRAY)
                .append(Component.literal( ": "))
                .append(Component.literal(isAttackEnable(stack) ? "✔" : "❌").withStyle(isAttackEnable(stack)? ChatFormatting.GREEN : ChatFormatting.RED));
        tooltips.add(attackComp);
        addEnchantmentToTooltip(stack,YoyosEnchantments.COLLECTING,tooltips, p_41422_);
        addEnchantmentToTooltip(stack,YoyosEnchantments.BREAKING,tooltips, p_41422_);
        addEnchantmentToTooltip(stack,YoyosEnchantments.CRAFTING,tooltips, p_41422_);

        if (stack.isEnchanted())
            tooltips.add(Component.literal(""));
    }

    private static void addEnchantmentToTooltip(ItemStack stack, ResourceKey<Enchantment> ench, List<Component> tooltips,TooltipContext ctx) {
        addEnchantmentToTooltip(stack,ctx.registries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ench),tooltips,ctx);
    }

        private static void addEnchantmentToTooltip(ItemStack stack, Holder<Enchantment> ench, List<Component> tooltips,TooltipContext ctx) {
        int level = stack.getEnchantmentLevel(ench);
        MutableComponent comp = ench.value().description().copy().withStyle(ChatFormatting.GRAY)
                .append(Component.literal( ": "))
                .append(Component.literal(isEnchantmentEnable(stack,ench,ctx.registries()) ? "✔" : "❌").withStyle(isEnchantmentEnable(stack, ench.getKey(),ctx.registries())? ChatFormatting.GREEN : ChatFormatting.RED));
        if (ench.value().getMaxLevel() > 1 && level > 0) comp = comp.append(Component.literal(" ("))
                .append(Component.translatable("enchantment.level."+level))
                .append(Component.literal(")"));
        if (ench.equals(YoyosEnchantments.CRAFTING))
            comp.append(Component.literal(" (WIP, no usages)").withStyle(ChatFormatting.RED));
        tooltips.add(comp);
    }

    public static boolean isAttackEnable(ItemStack stack){
        return stack.getOrDefault(YoyosDataComponents.ATTACK,true);
    }
    public static void toggleAttack(ItemStack stack){
        stack.set(YoyosDataComponents.ATTACK,!isAttackEnable(stack));
    }


    public static boolean isEnchantmentEnable(ItemStack stack, Holder<Enchantment> enchantment, HolderLookup.Provider provider){
        return isEnchantmentEnable(stack,enchantment.getKey(),provider);
    }

    public static boolean isEnchantmentEnable(ItemStack stack, ResourceKey<Enchantment> enchantment,HolderLookup.Provider provider){
        return stack.getOrDefault(YoyosDataComponents.ENCHANTMENTS,EnchantmentsState.EMPTY).isEnchantmentActivate(stack, enchantment, provider);
    }

    public static void toggleEnchant(ItemStack stack, ResourceKey<Enchantment> enchant, HolderLookup.Provider provider){
        EnchantmentsState state = stack.getOrDefault(YoyosDataComponents.ENCHANTMENTS,EnchantmentsState.EMPTY);
        state.toggleEnchantment(enchant,stack,provider);
        stack.set(YoyosDataComponents.ENCHANTMENTS,state);
    }
    public static void toggleEnchant(ItemStack stack, ResourceKey<Enchantment> enchant, HolderLookup.Provider provider, UUID player){
        EnchantmentsState state = stack.getOrDefault(YoyosDataComponents.ENCHANTMENTS,EnchantmentsState.EMPTY);
        Player p = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(player);
        InteractionHand hand = p.getItemInHand(InteractionHand.MAIN_HAND).equals(stack) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        state.toggleEnchantment(enchant,stack,provider);
        stack.set(YoyosDataComponents.ENCHANTMENTS,state);
        PacketDistributor.sendToPlayer((ServerPlayer) p,new HandSyncS2CPayload(hand,stack));
    }



    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.equals(Enchantments.SWEEPING_EDGE)) return false;
        return super.supportsEnchantment(stack,enchantment);
    }


    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.is(YoyosItems.name("creative_yoyo")) || super.isFoil(stack);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && state.getDestroySpeed(level,pos) != 0.0F){
            stack.hurtAndBreak(1,entity,EquipmentSlot.MAINHAND);
        }

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide){
            if (stack.getDamageValue() <= stack.getMaxDamage()){

                YoyoEntity yoyoEntity = YoyoEntity.CASTERS.get(player.getUUID());

                if (yoyoEntity == null) {
                    yoyoEntity = factory.create(level,player, hand);
                    level.addFreshEntity(yoyoEntity);
                    level.playSound(null, yoyoEntity.getX(), yoyoEntity.getY(), yoyoEntity.getZ(), YoyosSounds.THROW.get(), SoundSource.NEUTRAL, 0.5f, 0.4f / (level.random.nextFloat() * 0.4f + 0.8f));


                    player.causeFoodExhaustion(0.05f);
                } else {
                    yoyoEntity.setRetracting(!yoyoEntity.isRetracting());
                }

            }
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1,attacker,EquipmentSlot.MAINHAND);
        return true;
    }


    public double getAttackDamage(ItemStack yoyo) {
        return yoyoTier.getDamage();
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID,getAttackDamage(stack),AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)
                .add(Attributes.ATTACK_SPEED,new AttributeModifier(BASE_ATTACK_DAMAGE_ID,-2.4000000953674316, AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.HAND).build();
    }

    @Override
    public double getWeight(ItemStack yoyo) {
        return yoyoTier.getWeight();
    }

    @Override
    public double getLength(ItemStack yoyo) {
        return yoyoTier.getLength();
    }

    @Override
    public int getDuration(ItemStack yoyo) {
        return yoyoTier.getDuration();
    }

    @Override
    public int getAttackInterval(ItemStack yoyo) {
        return 10;
    }

    @Override
    public int getMaxCollectedDrops(ItemStack yoyo, HolderLookup.Provider provider) {
        return calculateMaxCollectedDrops(yoyo.getEnchantmentLevel(provider.holderOrThrow(YoyosEnchantments.COLLECTING)));
    }


    @Override
    public <T extends LivingEntity> void damageItem(ItemStack yoyo, InteractionHand hand, int amount, T entity) {
        yoyo.hurtAndBreak(amount,entity,hand == InteractionHand.OFF_HAND ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND);
    }

    @Override
    public void entityInteraction(ItemStack yoyoStack, Player player, InteractionHand hand, YoyoEntity yoyo, Entity target) {
        if (target.level().isClientSide) return;
        entityInteractions.forEach(i->i.apply(yoyoStack, player, hand, yoyo, target));

    }

    @Override
    public boolean interactsWithBlocks(ItemStack yoyo) {
        return !blockInteractions.isEmpty();
    }

    @Override
    public void blockInteraction(ItemStack yoyoStack, Player player, Level world, BlockPos pos, BlockState state, Block block, YoyoEntity yoyo) {
        if (world.isClientSide) return;
        blockInteractions.forEach(i->i.apply(yoyoStack, player, pos, state, block, yoyo));
    }

    @Override
    public RenderOrientation getRenderOrientation(ItemStack yoyo) {
        return renderOrientation;
    }

    private int calculateMaxCollectedDrops(int level) {
        if (level == 0) return 0;

        var mult = 1;

        for (int i = 0; i < level; i++) mult *= 2;

        return 64 /*YoyosConfig.general.collectingBase.get()*/ * mult;
    }
}
