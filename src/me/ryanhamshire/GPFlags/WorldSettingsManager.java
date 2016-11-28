package me.ryanhamshire.GPFlags;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.World;

class WorldSettingsManager
{
    private ConcurrentHashMap<String, WorldSettings> nameToSettingsMap = new ConcurrentHashMap<String, WorldSettings>();
    final String OtherWorldsKey = "Other Worlds";
    
    WorldSettingsManager()
    {
        this.nameToSettingsMap.put(this.OtherWorldsKey, new WorldSettings());
    }
    
    void Set(World world, WorldSettings settings)
    {
        this.Set(world.getName(), settings);
    }
    
    public void Set(String key, WorldSettings settings)
    {
        this.nameToSettingsMap.put(key, settings);
    }
    
    WorldSettings Get(World world)
    {
        return this.Get(world.getName());
    }
    
    WorldSettings Get(String key)
    {
        WorldSettings settings = this.nameToSettingsMap.get(key);
        if(settings != null)return settings;
        return this.nameToSettingsMap.get(this.OtherWorldsKey);
    }

    public WorldSettings Create(String worldName)
    {
        WorldSettings settings = new WorldSettings();
        this.nameToSettingsMap.put(worldName, settings);
        return settings;
    }
}
