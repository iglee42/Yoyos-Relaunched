package fr.iglee42.yoyos.resourcepack;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

import java.nio.file.Path;
import java.util.function.Consumer;

public class YoyosPackFinder implements RepositorySource {


	private final PackType type;

	public YoyosPackFinder(PackType type) {

		this.type = type;
	}

	@Override
	public void loadPacks(Consumer<Pack> consumer) {
		Path rootPath = PathConstant.ROOT_PATH;
		Pack pack = Pack.create("yoyos_"+type.getSuffix(),Component.literal("Yoyos Builtin Pack"),true,
				(t)-> new InMemoryPack(rootPath),new Pack.Info(Component.literal("Builtin resource pack for Yoyos"),12,12, FeatureFlagSet.of(FeatureFlags.VANILLA),true),type.getVanillaType(), Pack.Position.TOP,true, PackSource.BUILT_IN);
		if (pack != null) {
			consumer.accept(pack);
		}
	}
}