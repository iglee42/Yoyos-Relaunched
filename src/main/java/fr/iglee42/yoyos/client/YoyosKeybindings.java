package fr.iglee42.yoyos.client;

import com.mojang.blaze3d.platform.InputConstants;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.YoyoEntity;
import fr.iglee42.yoyos.common.YoyoItem;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD,modid = Yoyos.MODID)
public class YoyosKeybindings {

    private static List<KeyMapping> keyMappings = new ArrayList<>();

    public static final KeyMapping OPEN_CONFIG = createBinding("open_config", GLFW.GLFW_KEY_0);

    private static KeyMapping createBinding(String name, int key) {
        KeyMapping keyBinding = new KeyMapping(getKey(name), key, getKey("category"));
        keyMappings.add(keyBinding);
        return keyBinding;
    }


    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        keyMappings.forEach(event::register);
    }



    private static String getKey(String name) {
        return String.join(".", "key", Yoyos.MODID, name);
    }

    public static void handleEventInput(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return;

        if (YoyosKeybindings.OPEN_CONFIG.consumeClick() && mc.player.getMainHandItem().getItem() instanceof YoyoItem){
            mc.setScreen(new YoyoConfigScreen());
        }
    }
}
