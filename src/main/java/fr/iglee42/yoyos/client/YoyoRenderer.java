package fr.iglee42.yoyos.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.common.api.RenderOrientation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Random;

public class YoyoRenderer extends EntityRenderer<YoyoEntity> {

    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    private final Random random = new Random();

    public YoyoRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public boolean shouldRender(YoyoEntity p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(YoyoEntity yoyo) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public void render(YoyoEntity entity, float p_114486_, float pt, PoseStack stack, MultiBufferSource p_114489_, int p_114490_) {
        Minecraft.getInstance().getProfiler().push("renderYoyo");
        stack.pushPose();
        //stack.translate(entity.getX(),entity.getY() + entity.getBbHeight() / 2,entity.getZ());
        stack.scale(0.5f,0.5f,0.5f);
        Vec3 pointTo = entity.getPlayerHandPos(pt).subtract(entity.getX(), entity.getY(), entity.getZ()).normalize();

        stack.pushPose();

        if (entity.hasYoyo() && entity.getYoyo().getRenderOrientation(entity.getYoyoStack()) == RenderOrientation.Horizontal){
            stack.mulPose(Axis.XP.rotationDegrees(-90));
        } else {
            stack.mulPose(Axis.YP.rotationDegrees(270 - Minecraft.getInstance().cameraEntity.getYRot()));
        }

        stack.mulPose(Axis.ZP.rotation(((float) entity.getRemainingTime() / entity.getMaxTime())* 2 * 360));

        itemRenderer.renderStatic(entity.getYoyoStack(), ItemDisplayContext.NONE,0,0,stack,p_114489_,Minecraft.getInstance().level,p_114490_);
        stack.popPose();

        if (entity.isCollecting() && !entity.getCollectedDrops().isEmpty()) {
            renderCollectedItems(entity, pt,stack,p_114489_,p_114490_);
        }

        stack.popPose();

        renderChord(entity,entity.getX(),entity.getY(),entity.getZ(),pt);


        Minecraft.getInstance().getProfiler().pop();
    }




    public void renderChord(YoyoEntity entity, double x, double y, double z, float partialTicks) {
        if (!entity.hasThrower()) return;
        Player thrower = (Player) entity.getThrower();
        if (thrower == null) return;

        y -= (1.6 - thrower.getBbHeight()) * 0.5;
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();

        boolean rightHand = thrower.getMainArm() == HumanoidArm.RIGHT && entity.getHand() == InteractionHand.MAIN_HAND;

        Vec3 handPos;

        if (Minecraft.getInstance().options.getCameraType().ordinal() != 0 || thrower.getId() != Minecraft.getInstance().player.getId()) {
            double posX = interpolateValue(thrower.xo, thrower.getX(), partialTicks);
            double posY = interpolateValue(thrower.yo, thrower.getY(), partialTicks) + 1.272;
            double posZ = interpolateValue(thrower.zo, thrower.getZ(), partialTicks);
            double bodyRotation = interpolateValue(thrower.yRotO, thrower.getYRot(), partialTicks);

            bodyRotation = Math.toRadians(bodyRotation);

            double rotation = bodyRotation;
            if (rightHand) rotation += Math.PI;

            double shoulderRadius = 0.347;
            posX += Math.cos(rotation) * shoulderRadius;
            posZ += Math.sin(rotation) * shoulderRadius;

            double f = 1.0;

            if (thrower.getPose() == Pose.FALL_FLYING) {
                f = thrower.getDeltaMovement().lengthSqr() / 0.2f;
                f = f * f * f;
            }

            if (f < 1.0) f = 1.0;

            float limbSwing = (float) thrower.swingTime;
            float limbSwingAmount = thrower.getSwimAmount(partialTicks);

            double pitch = Math.cos(limbSwing * 0.6662 + (rightHand ? 0.0 : Math.PI)) * 2.0 * limbSwingAmount * 0.5 / f;
            pitch *= 0.5 - Math.PI / 10.0;

            if (thrower.isCrouching()) {
                pitch += 0.4;
                posY -= 0.4;
            }

            pitch += (rightHand ? -1.0 : 1.0) * Math.sin((thrower.tickCount + partialTicks) * 0.067) * 0.05;

            double roll = Math.PI;
            double yaw = bodyRotation + Math.cos((thrower.tickCount + partialTicks) * 0.09) * 0.05 + 0.05;

            posX += -1.0 * Math.sin(roll) * Math.cos(yaw) - Math.cos(roll) * Math.sin(pitch) * Math.sin(yaw);
            posY += Math.cos(pitch) * Math.cos(roll);
            posZ += Math.sin(roll) * Math.sin(yaw) - Math.cos(roll) * Math.sin(pitch) * Math.cos(yaw);

            handPos = new Vec3(posX, posY, posZ);
        } else {
            double posX = interpolateValue(thrower.xo, thrower.getX(), partialTicks);
            double posY = interpolateValue(thrower.yo, thrower.getY(), partialTicks) + 1.1;
            double posZ = interpolateValue(thrower.zo, thrower.getZ(), partialTicks);

            double rotationYaw = Math.toRadians(interpolateValue(thrower.yRotO, thrower.getYRot(), partialTicks));
            double rotationPitch = Math.toRadians(interpolateValue(thrower.xRotO, thrower.getXRot(), partialTicks));

            double mirror = rightHand ? -1 : 1;
            double radius = 0.1;

            double v = -Math.sin(rotationPitch) * mirror * mirror * radius;
            double angle = rotationYaw + Math.PI * 0.5;
            posX += Math.cos(rotationYaw) * mirror * radius + Math.cos(angle) * v;
            posY += Math.sin(-rotationPitch) * radius;
            posZ += Math.sin(rotationYaw) * mirror * radius + Math.sin(angle) * v;

            handPos = new Vec3(posX, posY, posZ);
        }

        double yoyoPosX = interpolateValue(entity.xo, entity.getX(), partialTicks);
        double yoyoPosY = interpolateValue(entity.yo, entity.getY(), partialTicks) - entity.getBbHeight();
        double yoyoPosZ = interpolateValue(entity.zo, entity.getZ(), partialTicks);

        double xDiff = handPos.x - yoyoPosX;
        double yDiff = handPos.y - yoyoPosY;
        double zDiff = handPos.z - yoyoPosZ;

        int color = 0xDDDDDD;
        if (entity.hasYoyo()) {
            color = entity.getYoyo().getCordColor(entity.getYoyoStack(), entity.tickCount + partialTicks);
        }

        float stringR = (color >> 16 & 255) / 255f;
        float stringG = (color >> 8 & 255) / 255f;
        float stringB = (color & 255) / 255f;

        for (int i = 0; i <= 1; i++) {
            bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
            for (int j = 0; j <= 24; j++) {
                float r = stringR;
                float g = stringG;
                float b = stringB;

                if (j % 2 == 0) {
                    r *= 0.7f;
                    g *= 0.7f;
                    b *= 0.7f;
                }

                double segment = j / 24.0;
                double zag = 0.0125 * (i % 2 * 2 - 1);
                double x1 = x + xDiff * segment;
                double y1 = y + yDiff * (segment * segment + segment) * 0.5;
                double z1 = z + zDiff * segment;

                bufferBuilder.vertex(x1 - 0.0125, y1, z1 + zag).color(r, g, b, 1.0f).endVertex();
                bufferBuilder.vertex(x1 + 0.0125, y1, z1 - zag).color(r, g, b, 1.0f).endVertex();
            }
            tessellator.end();
        }

    }


    private void renderCollectedItems(YoyoEntity entity, float pt,PoseStack poseStack,MultiBufferSource source,int light) {
        var boundTexture = false;


        for (int i = 0; i < entity.getCollectedDrops().size(); i++) {
            ItemStack stack = entity.getCollectedDrops().get(i);
            int count = stack.getCount();
            int maxCount = stack.getMaxStackSize();
            while (count > 0){
                renderItem(i,entity,stack,pt,poseStack,source, light);
                count -= maxCount;
            }
        }
    }

    private void renderItem(int i, YoyoEntity yoyo, ItemStack itemStack, float partialTicks, PoseStack poseStack, MultiBufferSource source, int light) {
        poseStack.pushPose();

        long seed = ((Item.getId(itemStack.getItem()) * 31L + i) * 31 + itemStack.getCount());

        this.random.setSeed(seed);

        BakedModel bakedModel = this.itemRenderer.getItemModelShaper().getItemModel(itemStack);
        int modelCount = this.transformModelCount(yoyo, itemStack, partialTicks, bakedModel,poseStack);
        boolean gui3d = bakedModel.isGui3d();

        if (!gui3d) {
            float f3 = -0.0f * (modelCount - 1) * 0.5f;
            float f4 = -0.0f * (modelCount - 1) * 0.5f;
            float f5 = -0.09375f * (modelCount - 1) * 0.5f;
            poseStack.translate((double) f3, (double) f4, (double) f5);
        }

        for (int k = 0; k < modelCount; k++) {
            poseStack.pushPose();
            if (gui3d) {

                if (k > 0) {
                    float f7 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    float f9 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    float f6 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    poseStack.translate((double) f7, (double) f9, (double) f6);
                }

                this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.GROUND,light,OverlayTexture.NO_OVERLAY,poseStack,source,Minecraft.getInstance().level, 0);
                poseStack.popPose();
            } else {

                if (k > 0) {
                    float f8 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    float f10 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    poseStack.translate((double) f8, (double) f10, 0.0);
                }

                this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.GROUND,light,OverlayTexture.NO_OVERLAY,poseStack,source,Minecraft.getInstance().level, 0);
                poseStack.popPose();
                poseStack.translate(0.0, 0.0, 0.09375);
            }
        }

        poseStack.popPose();
    }
    private double interpolateValue(double start, double end, double pct) {
        return start + (end - start) * pct;
    }

    private float interpolateValue(float start, float end, float pct) {
        return start + (end - start) * pct;
    }



    private int transformModelCount(YoyoEntity yoyo, ItemStack itemStack, float partialTicks, BakedModel model,PoseStack stack) {
        boolean gui3d = model.isGui3d();
        int count = this.getModelCount(itemStack);

        double bob = Math.sin((random.nextDouble() + yoyo.tickCount + partialTicks) / 10.0
                + random.nextDouble() * Math.PI * 2.0) * 0.1 + 0.1;

        double scale = model.getTransforms()
                .getTransform(ItemDisplayContext.GROUND)
                .scale.y;
        stack.translate(0.0, bob + 0.25 * scale, 0.0);

        if (gui3d ) {
            double angle = ((random.nextDouble() + yoyo.tickCount + partialTicks) / 20.0
                    + random.nextDouble() * Math.PI * 2.0) * (180.0 / Math.PI);
            stack.mulPose(Axis.YP.rotationDegrees((float) angle));
        }


        return count;
    }

    private int getModelCount(ItemStack stack) {
        if (stack.getCount() > 48) {
            return 5;
        } else if (stack.getCount() > 32) {
            return 4;
        } else if (stack.getCount() > 16) {
            return 3;
        } else if (stack.getCount() > 1) {
            return 2;
        } else {
            return 1;
        }
    }

}
