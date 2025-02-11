package fr.iglee42.yoyos.common;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.client.YoyosKeybindings;
import fr.iglee42.yoyos.common.api.*;
import fr.iglee42.yoyos.common.init.YoyosEnchantments;
import fr.iglee42.yoyos.common.init.YoyosItems;
import fr.iglee42.yoyos.common.init.YoyosSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YoyoItem extends TieredItem implements IYoyo {

    private final Float defaultAttackDamage;
    protected final YoyoFactory factory;
    protected RenderOrientation renderOrientation = RenderOrientation.Vertical;
    protected List<EntityInteraction> entityInteractions = new ArrayList<>();
    protected List<BlockInteraction> blockInteractions = new ArrayList<>();
    protected YoyoTier yoyoTier;

    public YoyoItem(Properties properties, YoyoTier tier, YoyoFactory factory){
        super(tier.getTier(), properties);
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
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> tooltips, TooltipFlag p_41424_) {
        super.appendHoverText(stack, p_41422_, tooltips, p_41424_);
        tooltips.add(Component.translatable("tooltip.yoyos.weight", getWeight(stack)).withStyle(ChatFormatting.GRAY));
        tooltips.add(Component.translatable("tooltip.yoyos.length", getLength(stack)).withStyle(ChatFormatting.GRAY));

        int duration = getDuration(stack);
        if (duration < 0) tooltips.add(Component.translatable("tooltip.yoyos.duration.infinite").withStyle(ChatFormatting.GRAY));
        else tooltips.add(Component.translatable("tooltip.yoyos.duration", duration / 20f).withStyle(ChatFormatting.GRAY));
        if (getMaxCollectedDrops(stack) > 0) tooltips.add(Component.translatable("tooltip.yoyos.maxCollectedItems",getMaxCollectedDrops(stack) ,getMaxCollectedDrops(stack) / 64).withStyle(ChatFormatting.GRAY));
        tooltips.add(Component.translatable("tooltip.yoyos.openMenu",YoyosKeybindings.OPEN_CONFIG.getTranslatedKeyMessage()).withStyle(ChatFormatting.GRAY));

        tooltips.add(Component.literal(""));

        MutableComponent attackComp = Component.translatable("tooltip.yoyos.attack").withStyle(ChatFormatting.GRAY)
                .append(Component.literal( ": "))
                .append(Component.literal(isAttackEnable(stack) ? "✔" : "❌").withStyle(isAttackEnable(stack)? ChatFormatting.GREEN : ChatFormatting.RED));
        tooltips.add(attackComp);
        addEnchantmentToTooltip(stack,YoyosEnchantments.COLLECTING.get(),tooltips);
        addEnchantmentToTooltip(stack,YoyosEnchantments.BREAKING.get(),tooltips);
        addEnchantmentToTooltip(stack,YoyosEnchantments.CRAFTING.get(),tooltips);

        if (stack.isEnchanted())
            tooltips.add(Component.literal(""));
    }

    private static void addEnchantmentToTooltip(ItemStack stack,Enchantment ench,List<Component> tooltips){
        int level = stack.getEnchantmentLevel(ench);
        MutableComponent comp = Component.translatable(ench.getDescriptionId()).withStyle(ChatFormatting.GRAY)
                .append(Component.literal( ": "))
                .append(Component.literal(isEnchantmentEnable(stack,ForgeRegistries.ENCHANTMENTS.getKey(ench)) ? "✔" : "❌").withStyle(isEnchantmentEnable(stack,ForgeRegistries.ENCHANTMENTS.getKey(ench))? ChatFormatting.GREEN : ChatFormatting.RED));
        if (ench.getMaxLevel() > 1 && level > 0) comp = comp.append(Component.literal(" ("))
                .append(Component.translatable("enchantment.level."+level))
                .append(Component.literal(")"));
        if (ench.equals(YoyosEnchantments.CRAFTING.get()))
            comp.append(Component.literal(" (WIP, no usages)").withStyle(ChatFormatting.RED));
        tooltips.add(comp);
    }

    public static boolean isAttackEnable(ItemStack stack){
        return  !stack.getOrCreateTag().contains("attack") || stack.getOrCreateTag().getBoolean("attack");
    }
    public static void toggleAttack(ItemStack stack){
        stack.getOrCreateTag().putBoolean("attack",!isAttackEnable(stack));
    }


    public static boolean isEnchantmentEnable(ItemStack stack, RegistryObject<Enchantment> enchantment){
        return isEnchantmentEnable(stack,enchantment.getId());
    }

    public static boolean isEnchantmentEnable(ItemStack stack, ResourceLocation enchantment){
        if (enchantment.equals(YoyosEnchantments.COLLECTING.getId()) && !stack.isEmpty()){
            return ((YoyoItem)stack.getItem()).getMaxCollectedDrops(stack) > 0 && (!stack.getOrCreateTag().contains(enchantment.getPath())) ||
                    stack.getOrCreateTag().getBoolean(enchantment.getPath());
        }

        return stack.getEnchantmentLevel(ForgeRegistries.ENCHANTMENTS.getValue(enchantment)) > 0
                && ((!enchantment.equals(YoyosEnchantments.CRAFTING.getId()) &&
                !stack.getOrCreateTag().contains(enchantment.getPath())) ||
                stack.getOrCreateTag().getBoolean(enchantment.getPath()) &&
                        checkEnchantmentCompat(stack,enchantment));
    }

    private static boolean checkEnchantmentCompat(ItemStack stack, ResourceLocation enchantment) {
        if (enchantment.equals(YoyosEnchantments.BREAKING.getId())){
            return !isEnchantmentEnable(stack,YoyosEnchantments.CRAFTING);
        }
        if (enchantment.equals(YoyosEnchantments.CRAFTING.getId())){
            return !isEnchantmentEnable(stack,YoyosEnchantments.BREAKING);
        }
        return true;
    }

    public static void toggleEnchant(ItemStack stack, ResourceLocation enchant){
        stack.getOrCreateTag().putBoolean(enchant.getPath(),!isEnchantmentEnable(stack, enchant));
    }



    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.SWEEPING_EDGE) return false;
        return (interactsWithBlocks(stack) && (enchantment == Enchantments.BLOCK_FORTUNE || enchantment == Enchantments.SILK_TOUCH)) || enchantment.category == EnchantmentCategory.BREAKABLE || enchantment.category == EnchantmentCategory.WEAPON || enchantment.category == YoyosEnchantments.YOYOS_CATEGORY;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        if (TierSortingRegistry.isTierSorted(this.getTier())) {
            return TierSortingRegistry.isCorrectTierForDrops(this.getTier(), state);
        } else {
            int i = this.getTier().getLevel();
            if (i < 3 && state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
                return false;
            } else if (i < 2 && state.is(BlockTags.NEEDS_IRON_TOOL)) {
                return false;
            } else {
                return i >= 1 || !state.is(BlockTags.NEEDS_STONE_TOOL);
            }
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.is(YoyosItems.name("creative_yoyo")) || super.isFoil(stack);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && state.getDestroySpeed(level,pos) != 0.0F){
            stack.hurtAndBreak(1,entity,e->e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
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
        stack.hurtAndBreak(1,attacker,e->e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute,AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getAttackDamage(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4000000953674316, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    public double getAttackDamage(ItemStack yoyo) {
        return yoyoTier.getDamage();
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
    public int getMaxCollectedDrops(ItemStack yoyo) {
        return calculateMaxCollectedDrops(yoyo.getEnchantmentLevel(YoyosEnchantments.COLLECTING.
                get()));
    }


    @Override
    public <T extends LivingEntity> void damageItem(ItemStack yoyo, InteractionHand hand, int amount, T entity) {
        yoyo.hurtAndBreak(amount,entity,e->e.broadcastBreakEvent(hand));
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
