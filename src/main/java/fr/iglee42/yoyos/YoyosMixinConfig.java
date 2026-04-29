package fr.iglee42.yoyos;

import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class YoyosMixinConfig implements IMixinConfigPlugin{

    @Override
    public void acceptTargets(Set<String> arg0, Set<String> arg1) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void onLoad(String arg0) {
    }

    @Override
    public void postApply(String arg0, ClassNode arg1, String arg2, IMixinInfo arg3) {
    }

    @Override
    public void preApply(String arg0, ClassNode arg1, String arg2, IMixinInfo arg3) {
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
       if (targetClassName.contains("tconstruct")) return LoadingModList.get().getModFileById("tconstruct") != null;
       return true;
    }

}