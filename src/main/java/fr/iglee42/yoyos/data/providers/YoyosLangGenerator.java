package fr.iglee42.yoyos.data.providers;

import fr.iglee42.igleelib.api.utils.ModsUtils;
import fr.iglee42.yoyos.Yoyos;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class YoyosLangGenerator extends LanguageProvider {
    public YoyosLangGenerator(PackOutput output) {
        super(output, Yoyos.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ForgeRegistries.ITEMS.getKeys().stream().filter(rs->rs.getNamespace().equals(Yoyos.MODID)).forEach(rs->{
            addItem(ForgeRegistries.ITEMS.getDelegateOrThrow(rs), ModsUtils.getUpperName(rs.getPath(),"_"));
        });
    }
}
