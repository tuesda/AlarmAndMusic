package com.example.root.otherComponent;

import android.graphics.Color;

import com.example.root.scroll.TimeInDay;

/**
 * Created by zhanglei on 15/5/24.
 */
public class ViewColorGenerator {
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


    private static final String[] windmillFanColors = {
            "ff03111f", // 00
            "ff071726",	// 01
            "ff0f193f",	// 02
            "ff191d4f",	// 03
            "ff312f4c",	// 04
            "ff666376", // 05
            "ff636566",	// 06
            "ff636566",	// 07
            "ff636566",	// 08
            "ff636566",	// 09
            "ff636566",	// 10
            "ff636566",	// 11
            "ff636566",	// 12
            "ff636566",	// 13
            "ff636566",	// 14
            "ff4a4645",	// 15
            "ff636566",	// 16
            "ff636566",	// 17
            "ff636566",	// 18
            "ff386267",	// 19
            "ff1e4e46",	// 20
            "ff244c55",	// 21
            "ff173047",	// 22
            "ff071726", // 23
    };

    public static String getWindmillFanColor(int hour) {
        return windmillFanColors[hour];
    }

    private static final String[] mountainFarColors = {
            "ff0e2033", // 00
            "ff0e263c", // 01
            "ff1e2c60", // 02
            "ff373b74", // 03
            "ff8481a8",	// 04
            "ff9766b1", // 05
            "ffd19fb1", // 06
            "ffd3d5be", // 07
            "ffd4ede7",	// 08
            "ff95f7f4",	// 09
            "ff95ebd6",	// 10
            "ff7ee899",	// 11
            "ff86de83",	// 12
            "ff7fc598", // 13
            "ffa6b3a1",	// 14
            "ff6c6f69",	// 15
            "ff7fc598", // 16
            "ffb6c19d",	// 17
            "fff2b29c",	// 18
            "ff689196",	// 19
            "ff86ae9d",	// 20
            "ff497781",	// 21
            "ff1c3750",	// 22
            "ff0e263c", // 23
    };

    public static String getMountainFarColor(int hour) {
        return mountainFarColors[hour];
    }

    private static final String[] mountainNearColors = {
            "ff091d30", // 00
            "ff091d30",	// 01
            "ff182554", // 02
            "ff2c3067", // 03
            "ff6c6992", // 04
            "ff8a59a3", // 05
            "ffc890a4",	// 06
            "ffc7c9b1", // 07
            "ffc3e2db", // 08
            "ff80e6e3", // 09
            "ff80d9c3", // 10
            "ff67d182", // 11
            "ff77cd74", // 12
            "ff6eb387",	// 13
            "ff8f9b8b", // 14
            "ff7b7f78", // 15
            "ff6eb387", // 16
            "ffa7b38c", // 17
            "ffe29e87", // 18
            "ff49767b", // 19
            "ff7aa492", // 20
            "ff3e6a74", // 21
            "ff173047", // 22
            "ff091d30", // 23
    };



    public static String getMountainNearColor(int hour) {
        return mountainNearColors[hour];
    }

    private static final String[] houseFrontColors = {
            "ff071726", // 0
            "ff071726", // 1
            "ff111c44", // 2
            "ff23275d", // 3
            "ff444262", // 4
            "ff9b84a6", // 5
            "fff8dbdf", // 6
            "fff4f1d5", // 7
            "fff3efee", // 8
            "fff3efee", // 9
            "fff3efee", // 10
            "fff3efee", // 11
            "fff3efee", // 12
            "ffd8d7d7", // 13
            "ffd8d7d7", // 14
            "ff90938e", // 15
            "ffd8d7d7", // 16
            "ffd9d1b1", // 17
            "ffe3b98d", // 18
            "ff538388", // 19
            "ff588576", // 20
            "ff3f6d77", // 21
            "ff173047", // 22
            "ff071726", // 23

    };

    public static String getHouseFron(int hour) {
        return houseFrontColors[hour];
    }

    private static final String[] windmillWindowColors = {
            "ff03111f", // 0
            "ff071726", // 1
            "ff071726", // 2
            "ff0c1f31", // 3
            "ff27263e", // 4
            "ff705b7a", // 5
            "ff4a4645", // 6
            "ff4a4645", // 7
            "ff4a4645", // 8
            "ff4a4645", // 9
            "ff4a4645", // 10
            "ff4a4645", // 11
            "ff4a4645", // 12
            "ff4a4645", // 13
            "ff4a4645", // 14
            "ff4a4645", // 15
            "ff4a4645", // 16
            "ff4a4645", // 17
            "ff4a4645", // 18
            "ffbeb17c", // 19
            "fff5e190", // 20
            "fff5e190", // 21
            "ff0c1f31", // 22
            "ff071726", // 23
    };

    public static String getWindmillWinColor(int hour) {
        return windmillWindowColors[hour];
    }


    private static final String[] waveColors = {
            "00000000", // 0
            "00000000", // 1
            "00000000", // 2
            "ff38427d", // 3
            "ff696695", // 4
            "ffb584ae", // 5
            "fffbdade", // 6
            "ffc6eff0", // 7
            "ffbdf3e6", // 8
            "ffafeefa", // 9
            "ff99d0f6", // 10
            "ff7ebcf9", // 11
            "ff7ebcf9", // 12
            "ff97d9e2", // 13
            "ffc2c9c2", // 14
            "ff8c918c", // 15
            "ff85c4ce", // 16
            "ffc4d4ba", // 17
            "fff8b6b2", // 18
            "ff6588a9", // 19
            "ff6cafa0", // 20
            "ff5d8d9a", // 21
            "ff304f75", // 22
            "00000000", // 23

    };

    public static String getWave(int hour) {
        return waveColors[hour];
    }

    private static final float[] starsAl = {
            1, // 00
            1, // 01
            1, // 02
            0.5f, // 03
            0.5f, // 04
            0.5f, // 05
            0.5f, // 06
            0.5f, // 07
            0, // 08
            0, // 09
            0, // 10
            0, // 11
            0, // 12
            0, // 13
            0, // 14
            0, // 15
            0, // 16
            0, // 17
            0, // 18
            0.5f, // 19
            0.5f, // 20
            0.5f, // 21
            0.5f, // 22
            0.5f, // 23
    };

    public static float getStarsAl(int hour) {
        return starsAl[hour];
    }

    private static final String[] houseSideColors = {
            "ff0a1d2f", // 0
            "ff0a1d2f", // 1
            "ff1c2959", // 2
            "ff32366c", // 3
            "ff535171", // 4
            "ffad97b8", // 5
            "ffe0b7bc", // 6
            "ffc0bb96", // 7
            "ffe1d3ce", // 8
            "ffe1d3ce", // 9
            "ffe1d3ce", // 10
            "ffe1d3ce", // 11
            "ffe1d3ce", // 12
            "ffc2c0c0", // 13
            "ffc2c0c0", // 14
            "ff858883", // 15
            "ffc2c0c0", // 16
            "ffeee8cf", // 17
            "fff1cfab", // 18
            "ff5f9095", // 19
            "ff4b7668", // 20
            "ff37636d", // 21
            "ff28425e", // 22
            "ff0a1d2f", // 23
    };

    public static String getHouseSideColor(int hour) {
        return houseSideColors[hour];
    }

    private static final String[] houseWindowColors = {
            "ff071726", // 0
            "ff071726", // 1
            "ff071726", // 2
            "ff0c1f31", // 3
            "ff27263e", // 4
            "ff705b7a", // 5
            "ff636566", // 6
            "ff636566", // 7
            "ff636566", // 8
            "ff636566", // 9
            "ff636566", // 10
            "ff636566", // 11
            "ff636566", // 12
            "ff636566", // 13
            "ff636566", // 14
            "ff636566", // 15
            "ff636566", // 16
            "ff636566", // 17
            "ff636566", // 18
            "ffbeb17c", // 19
            "fff5e190", // 20
            "fff5e190", // 21
            "ff0c1f31", // 22
            "ff091b2c", // 23
    };

    public static String getHouseWindowC(int hour) {
        return houseWindowColors[hour];
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




    /**
     * update view whenOfD
     * @param time
     */
    public static String getWhenInDay(TimeInDay time) {
        int hour = 0;
        if (time != null) hour = time.getHour();
        switch (hour) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 0:
                return "Evening";
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return "Midnight";
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return "Morning";
            case 12:
                return "Midday";
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                return "Afternoon";
            default:
                return "Wrong text";

        }
    }

    /**
     * Help method used by refreshBackground() method
     * @param time
     * @return
     */
    public static int[] getTopBtmColor(TimeInDay time) {

        ColorRange colorRange = getColorRange(time.getHour());
        ColorRange finalColorRange = getColorRange(time.getHour()+1 > 23 ? 0 : time.getHour()+1);
        Argb startTop = new Argb(colorRange.getStart());
        Argb startBottom = new Argb(colorRange.getEnd());

        Argb finalTop = new Argb(finalColorRange.getStart());
        Argb finalBottom = new Argb(finalColorRange.getEnd());



        int curTopA = ((finalTop.getA() - startTop.getA()) * time.getMin()) / 60 + startTop.getA();
        int curTopR = ((finalTop.getR() - startTop.getR()) * time.getMin()) / 60 + startTop.getR();
        int curTopG = ((finalTop.getG() - startTop.getG()) * time.getMin()) / 60 + startTop.getG();
        int curTopB = ((finalTop.getB() - startTop.getB()) * time.getMin()) / 60 + startTop.getB();

        int top = Color.argb(curTopA, curTopR, curTopG, curTopB);

        int curBtmA = ((finalBottom.getA() - startBottom.getA()) * time.getMin()) / 60 + startBottom.getA();
        int curBtmR = ((finalBottom.getR() - startBottom.getR()) * time.getMin()) / 60 + startBottom.getR();
        int curBtmG = ((finalBottom.getG() - startBottom.getG()) * time.getMin()) / 60 + startBottom.getG();
        int curBtmB = ((finalBottom.getB() - startBottom.getB()) * time.getMin()) / 60 + startBottom.getB();

        int btm = Color.argb(curBtmA, curBtmR, curBtmG, curBtmB);

        return new int[]{top, btm};
    }

    public static int getMiddleColor(TimeInDay timeInDay) {
        int[] colors = getTopBtmColor(timeInDay);
        int start_a = Color.alpha(colors[0]);
        int start_r = Color.red(colors[0]);
        int start_g = Color.green(colors[0]);
        int start_b = Color.blue(colors[0]);

        int end_a = Color.alpha(colors[1]);
        int end_r = Color.red(colors[1]);
        int end_g = Color.green(colors[1]);
        int end_b = Color.blue(colors[1]);

        int current = Color.argb((start_a + end_a) / 2, (start_r + end_r) / 2, (start_g + end_g) / 2, (start_b + end_b) / 2);
        return current;
    }
}
