package fr.iglee42.yoyos.common;

import com.google.gson.JsonObject;
import fr.iglee42.igleelib.api.utils.JsonHelper;
import fr.iglee42.yoyos.Yoyos;
import fr.iglee42.yoyos.common.api.BlockInteraction;
import fr.iglee42.yoyos.common.api.EntityInteraction;
import fr.iglee42.yoyos.compat.YoyoPluginHelper;
import net.minecraft.world.item.Tier;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class YoyoTier {
    private final String name;
    private double weight = 1.0;
    private double length = 3.0;
    private int duration = 100;
    private double damage = 1.0;
    private final Tier tier;
    private final String plugin;
    private final List<BlockInteraction> blockInteractions = new ArrayList<>(List.of(Interaction::breakBlocks,Interaction::craftWithBlock));
    private final List<EntityInteraction> entityInteractions = new ArrayList<>(List.of(Interaction::attackEntity,Interaction::collectItem));
    private Constructor<? extends YoyoItem> customConstructor = null;
    private String customItem = "";
    private String customCord = "";
    private String mod = "";

    public YoyoTier(String name,Tier tier,String plugin){
        this.name = name;
        this.tier = tier;
        this.plugin = plugin;
    }

    protected YoyoTier(String name, double weight, double length, int duration, double damage, Tier tier) {
        this(name,tier,"minecraft");
        this.weight = weight;
        this.length = length;
        this.duration = duration;
        this.damage = damage;
    }


    public static YoyoTier fromJson(JsonObject json, YoyoTier oldTier) {

        double weight = json.has("weight") ? json.get("weight").getAsDouble() : oldTier.getWeight();
        double length = json.has("length") ? json.get("length").getAsDouble() : oldTier.getLength();
        int duration = JsonHelper.getIntOrDefault(json, "duration",oldTier.getDuration());
        double damage = json.has("damage") ? json.get("damage").getAsDouble() : oldTier.getDamage();

        return new YoyoTier(oldTier.getName(), oldTier.getTier(), oldTier.plugin)
                .setWeight(weight)
                .setLength(length)
                .setDuration(duration)
                .setDamage(damage)
                .setMod(oldTier.getMod())
                .setCustomConstructor(oldTier.getCustomConstructor())
                .setCustomCord(oldTier.getCustomCord())
                .setCustomItem(oldTier.getCustomItem())
                .addBlockInteraction(oldTier.getBlockInteractions().toArray(new BlockInteraction[]{}))
                .addEntityInteraction(oldTier.getEntityInteractions().toArray(new EntityInteraction[]{}));
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("weight", this.weight);
        json.addProperty("length", this.length);
        json.addProperty("duration", this.duration);
        json.addProperty("damage", this.damage);
        return json;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getLength() {
        return length;
    }

    public int getDuration() {
        return duration;
    }

    public double getDamage() {
        return damage;
    }

    public Tier getTier() {
        return tier;
    }

    public List<BlockInteraction> getBlockInteractions() {
        return blockInteractions;
    }

    public List<EntityInteraction> getEntityInteractions() {
        return entityInteractions;
    }

    public @Nullable Constructor<? extends YoyoItem> getCustomConstructor() {
        if (Yoyos.getPluginHelper() == null) return null;
        return customConstructor;
    }

    public boolean hasCustomItem(){
        return !customItem.isEmpty();
    }
    public boolean hasCustomCord(){
        return !customCord.isEmpty();
    }
    public boolean hasCustomMod(){
        return !mod.isEmpty();
    }

    public String getCustomItem() {
        return customItem;
    }

    public String getCustomCord() {
        return customCord;
    }

    public String getMod() {
        return mod;
    }

    public String getPlugin() {
        return plugin;
    }

    // Setters

    public YoyoTier setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public YoyoTier setLength(double length) {
        this.length = length;
        return this;
    }

    public YoyoTier setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public YoyoTier setDamage(double damage) {
        this.damage = damage;
        return this;
    }

    public YoyoTier addBlockInteraction(BlockInteraction... interactions){
        blockInteractions.addAll(List.of(interactions));
        return this;
    }
    public YoyoTier addEntityInteraction(EntityInteraction... interactions){
        entityInteractions.addAll(List.of(interactions));
        return this;
    }

    public YoyoTier setCustomConstructor(Constructor<? extends YoyoItem> constructor){
        if (constructor == null) return this;
        this.customConstructor = constructor;
        return this;
    }

    public YoyoTier setCustomItem(String customItem){
        this.customItem = customItem;
        return this;
    }
    public YoyoTier setCustomCord(String customCord){
        this.customCord = customCord;
        return this;
    }
    public YoyoTier setMod(String mod){
        this.mod = mod;
        return this;
    }

    @Override
    public String toString() {
        return "YoyoTier{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", length=" + length +
                ", duration=" + duration +
                ", damage=" + damage +
                ", tier=" + tier +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YoyoTier yoyoTier = (YoyoTier) o;

        if (Double.compare(yoyoTier.weight, weight) != 0) return false;
        if (Double.compare(yoyoTier.length, length) != 0) return false;
        if (duration != yoyoTier.duration) return false;
        if (Double.compare(yoyoTier.damage, damage) != 0) return false;
        if (!name.equals(yoyoTier.name)) return false;
        return tier.equals(yoyoTier.tier);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(length);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + duration;
        temp = Double.doubleToLongBits(damage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + tier.hashCode();
        return result;
    }
}
