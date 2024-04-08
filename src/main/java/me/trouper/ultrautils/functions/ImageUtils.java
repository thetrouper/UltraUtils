package me.trouper.ultrautils.functions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageUtils {

    public static List<Component> imageToLarge(String URL) {
        try {
            URL url = new URL(URL);
            BufferedImage img = ImageIO.read(url);
            List<Component> lines = new ArrayList<>();
            Component message = Component.text("");

            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int rgb = img.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    String hex = String.format("#%02x%02x%02x", red, green, blue);
                    message = message.append(Component.text("█").color(TextColor.fromHexString(hex)));
                }
                lines.add(message);
                message = Component.text(""); // Reset builder for next line
            }
            return lines;
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return new ArrayList<>();
        }
    }


    public static List<Component> imageToComps(String URL) {
        try {
            URL url = new URL(URL);
            BufferedImage img = ImageIO.read(url);
            List<Component> lines = new ArrayList<>();
            Component message = Component.text("");
            int width = 0;

            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int rgb = img.getRGB(x, y);
                    Color color = Color.fromARGB(rgb);
                    String hex = color.toString().replaceAll("Color:\\[argb0xFF", "").replaceAll("\\]", "");
                    message = message.append(Component.text("█").color(TextColor.fromHexString("#" + hex)));

                    if ((width++) >= 7) {
                        lines.add(message);
                        message = Component.text("");
                        width = 0;
                    }
                }
            }
            return lines;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static List<String> imageToList(String URL) {
        try {
            URL url = new URL(URL);
            BufferedImage img = ImageIO.read(url);
            List<String> lines = new ArrayList<>();
            StringBuilder message = new StringBuilder();
            int width = 0;

            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int rgb = img.getRGB(x, y);
                    Color color = Color.fromARGB(rgb);
                    String hex = color.toString().replaceAll("Color:\\[argb0xFF", "").replaceAll("\\]", "");
                    ChatColor chat = ChatColor.of("#" + hex);
                    message.append(chat).append("█");

                    if ((width++) >= 7) {
                        lines.add(message.toString());
                        message = new StringBuilder();
                        width = 0;
                    }
                }
            }
            return lines;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
