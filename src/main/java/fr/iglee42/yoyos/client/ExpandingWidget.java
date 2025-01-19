package fr.iglee42.yoyos.client;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.iglee42.igleelib.api.utils.MouseUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ExpandingWidget extends AbstractWidget {


    private final int expandedWidth;
    private final int expandedHeight;
    private final int animationDurationTicks;
    private int ticksSinceOpen;
    private int closingTicks;
    private boolean collapsing;
    private final OnClick onClick;
    private final Supplier<Boolean> toggled;
    private final Supplier<Boolean> isActivate;
    private final Item item;

    public ExpandingWidget(int x, int y, int width, int height, int animationDurationTicks, OnClick onClick, Supplier<Boolean> toggled, Item item,Component message,Supplier<Boolean> isActivate) {
        super(x, y, 0, 0, message);
        this.onClick = onClick;
        this.expandedWidth = width;
        this.expandedHeight = height;
        this.animationDurationTicks = animationDurationTicks;
        this.ticksSinceOpen = 0;
        this.collapsing = false;
        this.toggled = toggled;
        this.item = item;
        this.isActivate = isActivate;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        active = isActivate.get();
        isHovered = mouseX >= getX() - width / 2 && mouseX <= getX() + width /2 && mouseY >= getY() - height /2 && mouseY <= getY() + height / 2;
        if (ticksSinceOpen <= animationDurationTicks || collapsing) {
            updateSize();
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.fill( getX() - this.width / 2,getY() - this.height / 2, getX() + this.width / 2, getY() + this.height / 2 ,
                toggled.get() ? new Color(114, 114, 114, 100).getRGB() :new Color(0,0,0,100).getRGB());

        if (ticksSinceOpen < animationDurationTicks) return;



        graphics.pose().pushPose();
        graphics.pose().scale(0.75f,0.75f,0.75f);
        graphics.drawCenteredString(Minecraft.getInstance().font,getMessage(), (int) (getX() / 0.75f), (int) ((getY() + 6) / 0.75f), toggled.get() ? ChatFormatting.GREEN.getColor():ChatFormatting.RED.getColor());
        graphics.pose().popPose();

        if(!(ticksSinceOpen <= animationDurationTicks) && !collapsing) {
            graphics.pose().pushPose();
            graphics.pose().scale(1.5f, 1.5f, 1.5f);
            graphics.renderItem(new ItemStack(item), (int) (getX() / 1.5 - 7.5), (int) ((getY() - ( (float) height /2) + 1) / 1.5f));
            graphics.pose().popPose();
        }
        graphics.pose().pushPose();
        graphics.pose().scale(3,3,3);
        graphics.pose().translate(0.5,0,80);
        String check = "❌";
        int widthCheck = Minecraft.getInstance().font.width(check);
        if (!toggled.get())graphics.drawString(Minecraft.getInstance().font,check, (getX()) / 3 - widthCheck / 2,(getY() - height / 2 + 2) / 3  , ChatFormatting.RED.getColor(),false);
        graphics.pose().popPose();

        if (isHovered && isActive()){
            graphics.fill(getX() - this.width / 2,getY() - this.height / 2,getX() + width / 2 , getY() - this.height / 2 + 1,new Color(255,255,255).getRGB());
            graphics.fill(getX() - this.width / 2,getY() + this.height / 2,getX() + width / 2 , getY() + this.height / 2 - 1,new Color(255,255,255).getRGB());
            graphics.fill(getX() - this.width / 2,getY() - this.height / 2 + 1,getX() - width / 2 + 1 , getY() + this.height / 2 - 1,new Color(255,255,255).getRGB());
            graphics.fill(getX() + this.width / 2,getY() - this.height / 2 + 1,getX() + width / 2 - 1 , getY() + this.height / 2 - 1,new Color(255,255,255).getRGB());
        }

        if (!isActive()){
            graphics.pose().pushPose();
            graphics.pose().translate(0,0,2000);
            graphics.fill(getX() - this.width / 2,getY() - this.height / 2,getX() + width / 2, getY() + height / 2, new Color(0,0,0, 155).getRGB());
            graphics.pose().popPose();
        }


        //graphics.drawCenteredString( Minecraft.getInstance().font, Component.literal("hello man"), this.getX() , this.getY() - 4, 0xFFFFFF);
    }

    private void updateSize() {
        if (ticksSinceOpen >= animationDurationTicks && !collapsing) {
            this.width = expandedWidth;
            this.height = expandedHeight;
            return;
        }
        if (ticksSinceOpen <= animationDurationTicks) {


            int pixelPerTick = expandedWidth / animationDurationTicks;
            int progress = animationDurationTicks + (ticksSinceOpen - animationDurationTicks) ;
            this.width = pixelPerTick * progress;
            this.height = pixelPerTick * progress;


        }
        if (collapsing) {
            if (closingTicks >= animationDurationTicks) {
                this.width = 0;
                this.height = 0;
                this.collapsing = false;
                return;
            }

            int pixelPerTick = expandedWidth / animationDurationTicks;
            int progress = animationDurationTicks - closingTicks;
            this.width = pixelPerTick * progress;
            this.height = pixelPerTick * progress;
        }


    }

    public void tick() {
        ticksSinceOpen++;
        if (collapsing){
            closingTicks++;
        }
    }

    @Override
    public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }



    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        /*Minecraft.getInstance().player.sendSystemMessage(Component.literal(mouseX + ";" + mouseY));
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(getX() / 2 + ";" + getY() / 2));
        if (isActive())isHovered = mouseX >= (double) getX() / 2 - (double) width / 2 && mouseX <= (double) getX() / 2 + (double) width / 2
                && mouseY >= (double) getY() / 2 - (double) height / 2 && mouseY <= (double) getY() / 2 + (double) height / 2;*/
    }

    @Override
    public boolean mouseClicked(double p_93641_, double p_93642_, int p_93643_) {
        if (isHovered && isActive()){
            onClick.onClick(this);
        }
        return isHovered;
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnClick {
        void onClick(ExpandingWidget widget);
    }
}
