package com.example.root.scroll;

/**
 * Created by zhanglei on 15/5/10.
 */
public class ScrollColorValue {
    private static final ColorRange[] colorRanges = {
            new ColorRange("ff000109", "ff112d43"), // 00:00
            new ColorRange("ff000109", "ff203f58"), // 01:00
            new ColorRange("ff051034", "ff4d5d98"), // 02:00
            new ColorRange("ff0f284b", "ff5e5aac"), // 03:00
            new ColorRange("ff0f325a", "ff9975bf"), // 04:00
            new ColorRange("ff0a5e8a", "ffc87ac5"), // 05:00
            new ColorRange("ff03869b", "ffe99ea8"), // 06:00
            new ColorRange("ff3ccbe9", "fffde4c6"), // 07:00
            new ColorRange("ff3cbee9", "ffcffbe1"), // 08:00
            new ColorRange("ff51bdeb", "ff8defec"), // 09:00
            new ColorRange("ff4b99e9", "ff8dd2ef"), // 10:00
            new ColorRange("ff2185f5", "ff82c8f5"), // 11:00
            new ColorRange("ff3992f6", "ff73c3f6"), // 12:00
            new ColorRange("ff4070a6", "ff76bdbc"), // 13:00
            new ColorRange("ff45525c", "ff979992"), // 14:00
            new ColorRange("ff373a3d", "ff70726d"), // 15:00
            new ColorRange("ff4070a6", "ff76bdbc"), // 16:00
            new ColorRange("ff51c3bc", "ffb8c691"), // 17:00
            new ColorRange("ff784f84", "ffef9786"), // 18:00
            new ColorRange("ff1b2d61", "ff62958d"), // 19:00
            new ColorRange("ff133246", "ffa6c2a3"), // 20:00
            new ColorRange("ff06113c", "ff669ea1"), // 21:00
            new ColorRange("ff070d32", "ff32526b"), // 22:00
            new ColorRange("ff000109", "ff203f58"), // 23:00
    };
    public static final ColorRange[] seaColorRanges = {
            new ColorRange("ff0d273d", "ff0d1a29"), // 00
            new ColorRange("ff193348", "ff0d1a29"), // 01
            new ColorRange("ff2c3a6b", "ff111d45"), // 02
            new ColorRange("ff42438d", "ff10294c"), // 03
            new ColorRange("ff625f88", "ff423f6e"), // 04
            new ColorRange("ffaf7fa4", "ff7e4e96"), // 05
            new ColorRange("ffe99ea8", "ffe99ea8"), // 06
            new ColorRange("ff9fd5d6", "ff9fd5d6"), // 07
            new ColorRange("ffa0e7d6", "ffa0e7d6"), // 08
            new ColorRange("ff70d7eb", "ff70d7eb"), // 09
            new ColorRange("ff74bced", "ff74bced"), // 10
            new ColorRange("ff4ca3f5", "ff4ca3f5"), // 11
            new ColorRange("ff4ca2f6", "ff4ca2f6"), // 12
            new ColorRange("ff74c0ca", "ff74c0ca"), // 13
            new ColorRange("ffa6aba6", "ffa6aba6"), // 14
            new ColorRange("ff737873", "ff737873"), // 15
            new ColorRange("ff70b3be", "ff70b3be"), // 16
            new ColorRange("ffb4c5aa", "ffb4c5aa"), // 17
            new ColorRange("fff59993", "fff59993"), // 18
            new ColorRange("ff466b8d", "ff466b8d"), // 19
            new ColorRange("ff63af9d", "ff218286"), // 20
            new ColorRange("ff578796", "ff37636d"), // 21
            new ColorRange("ff2e4968", "ff162445"), // 22
            new ColorRange("ff193348", "ff0d1a29"), // 23
    };

    private static final String[] windmillWallColors = {
      "ff03111f", // 00
      "ff071726", // 01
      "ff0f193f", // 02
      "ff191d4f", // 03
      "ff3a3857", // 04
      "ff9b84a6", // 05
      "fff8dbdf", // 06
      "fff4f1d5", // 07
      "fff4f0ec", // 08
      "fff3efee", // 09
      "fff3efee", // 10
      "fff3efee", // 11
      "fff3efee", // 12
      "ffd8d7d7", // 13
      "ffd8d7d7", // 14
      "ffbfbebe", // 15
      "ffd8d7d7", // 16
      "ffd9d1b1", // 17
      "fff1cfab", // 18
      "ff49767b", // 19
      "ff1e4e46", // 20
      "ff244c55", // 21
      "ff173047", // 22
      "ff071726", // 23
    };

    private static final String[] windmillProofColors = {
            "ff03111f", // 00
            "ff071726", // 01
            "ff0f193f", // 02
            "ff191d4f", // 03
            "ff3a3857", // 04
            "ffa6849b", // 05
            "ffc2989d", // 06
            "ffc0bb96", // 07
            "ffc59584",	// 08
            "ffc59584",	// 09
            "ffc59584",	// 10
            "ffc59584",	// 11
            "ffc59584",	// 12
            "ffaa938b",	// 13
            "ffaa938b",	// 14
            "ff978f8c",	// 15
            "ffaa938b",	// 16
            "ffd49a7a",	// 17
            "ffb86f6c",	// 18
            "ff49767b",	// 19
            "ff1e4e46",	// 20
            "ff244c55",	// 21
            "ff173047",	// 22
            "ff071726", // 23
    };
    public static String getWindmillProofC(int hour) {
        return windmillProofColors[hour];
    }

    public static String getWindmillWallC(int hour) {
        return windmillWallColors[hour];
    }


    public static ColorRange getSeaColorRange(int hour) {
        return seaColorRanges[hour];
    }

    public static ColorRange getColorRange(int hour) {
        return colorRanges[hour];
    }

    public static class ColorRange {
        private ColorRange(String start, String end) {
            this.start = start;
            this.end = end;
        }

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }

        private String start;
        private String end;
    }

}
