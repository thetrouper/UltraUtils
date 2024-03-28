package functions;


import me.trouper.ultrautils.UltraUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text {

    public static String regexHighlighter(String input, String regex, String startString, String endString) {
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(input);

        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(result, startString + matcher.group() + endString);
        }

        matcher.appendTail(result);

        return result.toString();
    }

    public static final char SECTION_SYMBOL = (char)167;

    public static String color(String msg) {
        return msg.replace('&', SECTION_SYMBOL);
    }
    public static String prefix(String text) {
        String prefix = UltraUtils.config.prefix;
        return color(prefix + text);
    }
    public static String removeFirstColor(String input) {
        if (input.startsWith("\u00a7")) {
            if (input.length() > 2) {
                return input.substring(2);
            } else {
                return "";
            }
        } else {
            return input;
        }
    }
    public static String replaceRepeatingLetters(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder simplifiedText = new StringBuilder();
        char currentChar = input.charAt(0);
        int count = 1;

        for (int i = 1; i < input.length(); i++) {
            char nextChar = input.charAt(i);

            if (Character.toLowerCase(nextChar) == Character.toLowerCase(currentChar)) {
                count++;
            } else {
                simplifiedText.append(currentChar);

                if (count > 1) {
                    simplifiedText.append(currentChar);
                }

                currentChar = nextChar;
                count = 1;
            }
        }

        simplifiedText.append(currentChar);

        if (count > 1) {
            simplifiedText.append(currentChar);
        }

        return simplifiedText.toString();
    }

    public static String cleanName(String type) {
        return type.replaceAll("_"," ").toLowerCase();
    }
}
