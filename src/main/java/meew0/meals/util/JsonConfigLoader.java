package meew0.meals.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import meew0.meals.MealBean;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.Constants;

import static net.minecraftforge.common.config.Configuration.UnicodeInputStreamReader;

import java.io.*;
import java.util.*;

/**
 * Created by meew0 on 29.05.14.
 */
public class JsonConfigLoader {
    private File file;

    private static final String DEFAULT_ENCODING = Configuration.DEFAULT_ENCODING;

    private String encoding = DEFAULT_ENCODING;

    public JsonConfigLoader(File file) {
        this.file = file;
    }

    public List<MealBean> load() {
        BufferedReader buffer = null;
        Configuration.UnicodeInputStreamReader input = null;
        List<MealBean> list = new ArrayList<MealBean>();
        try
        {
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            if (!file.exists())
            {
                if(file.createNewFile()) {
                    writeDefaultTagList(file);
                } else {
                    throw new IOException("Cannot write to file");
                }
            }

            if (file.canRead())
            {
                input = new UnicodeInputStreamReader(new FileInputStream(file), encoding);
                encoding = input.getEncoding();
                buffer = new BufferedReader(input);

                String line;

                String data = "";

                while (true)
                {
                    line = buffer.readLine();

                    if (line == null) break;

                    data += (line.trim());
                }

                list = new Gson().fromJson(data, new TypeToken<List<MealBean>>(){}.getType());



            } else {
                throw new IOException("Cannot read from file");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (buffer != null)
            {
                try
                {
                    buffer.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (input != null)
            {
                try
                {
                    input.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return list;

    }

    private void writeDefaultTagList(File file) {
        try
        {
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            if (!file.exists() && !file.createNewFile())
            {
                throw new IOException("Cannot create file");
            }

            if (file.canWrite())
            {
                FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos, encoding));

                buffer.write("{\"list\":[\n");
                buffer.write("\t{\n");
                buffer.write("\t\t\"name\":\"Test Food 1\",\n");
                buffer.write("\t\t\"hunger\":\"5\",\n");
                buffer.write("\t\t\"saturation\":\"0.2\",\n");
                buffer.write("\t\t\"useDuration\":\"20\",\n");
                buffer.write("\t\t\"texture\":\"minecraft:diamond\",\n");
                buffer.write("\t\t\"recipe\":\"ccc,cic,ccc,c,coal,i,iron_ingot\"\n");
                buffer.write("\t\t\"recipeAmount\":\"1\",\n");
                buffer.write("\t},\n");
                buffer.write("\t{\n");
                buffer.write("\t\t\"name\":\"Test Food 2\",\n");
                buffer.write("\t\t\"hunger\":\"1\",\n");
                buffer.write("\t\t\"saturation\":\"1.0\",\n");
                buffer.write("\t\t\"useDuration\":\"100\"\n");
                buffer.write("\t\t\"texture\":\"meals:test\",\n");
                buffer.write("\t\t\"recipe\":\"ccc,cdc,sss,c,coal,d,dye:2,s,stone\"\n");
                buffer.write("\t\t\"recipeAmount\":\"8\",\n");
                buffer.write("\t}\n");
                buffer.write("]}");

                buffer.close();
                fos.close();
            } else {
                throw new IOException("Cannot write defaults to file");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
