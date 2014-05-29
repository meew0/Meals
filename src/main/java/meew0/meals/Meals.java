package meew0.meals;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import meew0.meals.util.JsonConfigLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.List;

@Mod(modid = Meals.MODID, version = Meals.VERSION)
public class Meals
{
    public static final String MODID = "meals";
    public static final String VERSION = "0.01";

    public static List<MealBean> foodConfig;
    public static Configuration modConfig;

    public static boolean shouldLoadFood = true;
    public static boolean displayMealInfo = true;
    public static boolean wolvesLikeMeals = true;
    public static String mealUnlocalizedName = "meal";
    public static String foodConfigFileName = "Meals_FoodConfig.json";

    public static Item meal;

    public static CreativeTabs mealsTab = new CreativeTabs("meals") {
        @Override
        public Item getTabIconItem() {
            return meal;
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        modConfig = new Configuration(event.getSuggestedConfigurationFile());

        modConfig.load();

        shouldLoadFood = modConfig.get("general", "shouldLoadFood", true, "Determines whether the food configuration file should be loaded.").getBoolean(true);
        mealUnlocalizedName = modConfig.get("general", "mealUnlocalizedName", "meal", "The unlocalized name of the meal item. This is similar to the ID in 1.6 and should only be changed once.").getString();
        foodConfigFileName = modConfig.get("general", "foodConfigFileName", "Meals_FoodConfig.json", "The file name of the food config. Should be in the config/ directory.").getString();
        displayMealInfo = modConfig.get("general", "displayMealInfo", true, "Determines whether additional information should be displayed below a meal's item name.").getBoolean(true);
        wolvesLikeMeals = modConfig.get("general", "wolvesLikeMeals", true, "Determines whether wolves will eat meals.").getBoolean(true);

        modConfig.save();

        File foodCfgFile = new File(event.getModConfigurationDirectory(), foodConfigFileName);
        foodConfig = new JsonConfigLoader(foodCfgFile).load();

        for(MealBean m : foodConfig) {
            System.out.println(m);
        }

        meal = new ItemMeal().setUnlocalizedName("meal").setHasSubtypes(true).setTextureName("meals:default").setCreativeTab(mealsTab);

        GameRegistry.registerItem(meal, "meal");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
