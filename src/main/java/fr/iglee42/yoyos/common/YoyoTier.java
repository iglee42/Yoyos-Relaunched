package fr.iglee42.yoyos.common;

import com.google.gson.JsonObject;
import fr.iglee42.igleelib.api.utils.JsonHelper;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ForgeConfigSpec;

public class YoyoTier {
    private String name;
    private double weight;
    private double length;
    private int duration;
    private double damage;
    private Tier tier;

    public YoyoTier(String name, double weight, double length, int duration, double damage, Tier tier) {
        this.name = name;
        this.weight = weight;
        this.length = length;
        this.duration = duration;
        this.damage = damage;
        this.tier = tier;
    }

    public static YoyoTier fromJson(JsonObject json, YoyoTier oldTier) {
        String name = oldTier.getName();
        Tier tier = oldTier.getTier();

        double weight = json.has("weight") ? json.get("weight").getAsDouble() : oldTier.getWeight();
        double length = json.has("length") ? json.get("length").getAsDouble() : oldTier.getLength();
        int duration = JsonHelper.getIntOrDefault(json, "duration",oldTier.getDuration());
        double damage = json.has("damage") ? json.get("damage").getAsDouble() : oldTier.getDamage();

        return new YoyoTier(name, weight, length, duration, damage, tier);
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

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
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
