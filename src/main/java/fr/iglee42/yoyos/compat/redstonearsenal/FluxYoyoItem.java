package fr.iglee42.yoyos.compat.redstonearsenal;

import fr.iglee42.yoyos.common.YoyoItem;
import fr.iglee42.yoyos.common.YoyoTier;

public class FluxYoyoItem extends YoyoItem/* implements IFluxItem*/ {


    protected final int maxEnergy = 800000;
    protected final int extract = 10000;
    protected final int receive = 10000;

    public FluxYoyoItem(YoyoTier tier) {
        super(tier);
        //ProxyUtils.registerItemModelProperty(this,new ResourceLocation("charged"),this::getChargedModelProperty);
    }

    /*@Override
    public Capability<? extends IEnergyStorage> getEnergyCapability() {
        return IFluxItem.super.getEnergyCapability();
    }

    protected String modId = "";

    @Override
    public ICoFHItem setModId(String modId) {
        this.modId = modId;
        return this;
    }

    @Override
    public String getCreatorModId(ItemStack itemStack) {

        return modId == null || modId.isEmpty() ? super.getCreatorModId(itemStack) : modId;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return IFluxItem.super.damageItem(stack, amount, entity, onBroken);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return IFluxItem.super.isBarVisible(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return IFluxItem.super.getBarColor(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return IFluxItem.super.getBarWidth(stack);
    }

    @Override
    public int getExtract(ItemStack container) {

        return extract;
    }

    @Override
    public int getReceive(ItemStack container) {

        return receive;
    }

    @Override
    public int getMaxEnergyStored(ItemStack container) {

        return getMaxStored(container, maxEnergy);
    }

    @Override
    public double getAttackDamage(ItemStack yoyo) {
        return hasEnergy(yoyo,false) ? super.getAttackDamage(yoyo) : 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            if (hasEnergy(stack, false)) {

                YoyoEntity yoyoEntity = YoyoEntity.CASTERS.get(player.getUUID());

                if (yoyoEntity == null) {
                    yoyoEntity = factory.create(level, player, hand);
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
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> tooltips, TooltipFlag p_41424_) {
        tooltipDelegate(stack, p_41422_, tooltips, p_41424_);
        super.appendHoverText(stack, p_41422_, tooltips, p_41424_);
    }*/
}
