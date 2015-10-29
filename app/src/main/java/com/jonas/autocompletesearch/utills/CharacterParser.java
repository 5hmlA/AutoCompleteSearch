/*
 * Filename	CharacterParser.java
 * Company	上海乐问-浦东分公司。
 * @author	LuRuihui
 * @version	0.1
 */
package com.jonas.autocompletesearch.utills;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * jonas 15/10/29
 * Java汉字转换为拼音
 */
public class CharacterParser {
    private static int[] pyvalue = new int[]{-20319, -20317, -20304, -20295, -20292, -20283, -20265, -20257, -20242, -20230, -20051, -20036, -20032, -20026, -20002, -19990, -19986, -19982, -19976, -19805, -19784, -19775, -19774, -19763, -19756, -19751, -19746, -19741, -19739, -19728, -19725, -19715, -19540, -19531, -19525, -19515, -19500, -19484, -19479, -19467, -19289, -19288, -19281, -19275, -19270, -19263, -19261, -19249, -19243, -19242, -19238, -19235, -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006, -19003, -18996, -18977, -18961, -18952, -18783, -18774, -18773, -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697, -18696, -18526, -18518, -18501, -18490, -18478, -18463, -18448, -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201, -18184, -18183, -18181, -18012, -17997, -17988, -17970, -17964, -17961, -17950, -17947, -17931, -17928, -17922, -17759, -17752, -17733, -17730, -17721, -17703, -17701, -17697, -17692, -17683, -17676, -17496, -17487, -17482, -17468, -17454, -17433, -17427, -17417, -17202, -17185, -16983, -16970, -16942, -16915, -16733, -16708, -16706, -16689, -16664, -16657, -16647, -16474, -16470, -16465, -16459, -16452, -16448, -16433, -16429, -16427, -16423, -16419, -16412, -16407, -16403, -16401, -16393, -16220, -16216, -16212, -16205, -16202, -16187, -16180, -16171, -16169, -16158, -16155, -15959, -15958, -15944, -15933, -15920, -15915, -15903, -15889, -15878, -15707, -15701, -15681, -15667, -15661, -15659, -15652, -15640, -15631, -15625, -15454, -15448, -15436, -15435, -15419, -15416, -15408, -15394, -15385, -15377, -15375, -15369, -15363, -15362, -15183, -15180, -15165, -15158, -15153, -15150, -15149, -15144, -15143, -15141, -15140, -15139, -15128, -15121, -15119, -15117, -15110, -15109, -14941, -14937, -14933, -14930, -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902, -14894, -14889, -14882, -14873, -14871, -14857, -14678, -14674, -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429, -14407, -14399, -14384, -14379, -14368, -14355, -14353, -14345, -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135, -14125, -14123, -14122, -14112, -14109, -14099, -14097, -14094, -14092, -14090, -14087, -14083, -13917, -13914, -13910, -13907, -13906, -13905, -13896, -13894, -13878, -13870, -13859, -13847, -13831, -13658, -13611, -13601, -13406, -13404, -13400, -13398, -13395, -13391, -13387, -13383, -13367, -13359, -13356, -13343, -13340, -13329, -13326, -13318, -13147, -13138, -13120, -13107, -13096, -13095, -13091, -13076, -13068, -13063, -13060, -12888, -12875, -12871, -12860, -12858, -12852, -12849, -12838, -12831, -12829, -12812, -12802, -12607, -12597, -12594, -12585, -12556, -12359, -12346, -12320, -12300, -12120, -12099, -12089, -12074, -12067, -12058, -12039, -11867, -11861, -11847, -11831, -11798, -11781, -11604, -11589, -11536, -11358, -11340, -11339, -11324, -11303, -11097, -11077, -11067, -11055, -11052, -11045, -11041, -11038, -11024, -11020, -11019, -11018, -11014, -10838, -10832, -10815, -10800, -10790, -10780, -10764, -10587, -10544, -10533, -10519, -10331, -10329, -10328, -10322, -10315, -10309, -10307, -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254};
    public static String[] pystr = new String[]{"a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian", "biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can", "cang", "cao", "ce", "ceng", "cha", "chai", "chan", "chang", "chao", "che", "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", "cuan", "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", "de", "deng", "di", "dian", "diao", "die", "ding", "diu", "dong", "dou", "du", "duan", "dui", "dun", "duo", "e", "en", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo", "ha", "hai", "han", "hang", "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun", "huo", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan", "lang", "lao", "le", "lei", "leng", "li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv", "luan", "lue", "lun", "luo", "ma", "mai", "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu", "mo", "mou", "mu", "na", "nai", "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao", "nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan", "nue", "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu", "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao", "re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha", "shai", "shan", "shang", "shao", "she", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng", "ti", "tian", "tiao", "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya", "yan", "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang", "zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};
    private StringBuilder buffer;
    private String resource;
    private static CharacterParser characterParser = new CharacterParser();

    public static CharacterParser getInstance(){
        return characterParser;
    }

    public String getResource(){
        return resource;
    }

    public void setResource(String resource){
        this.resource = resource;
    }

    /** 汉字转成ASCII码 * * @param chs * @return */
    private int getChsAscii(String chs){
        int asc = 0;
        try {
            byte[] bytes = chs.getBytes("gb2312");
            if(bytes == null || bytes.length>2 || bytes.length<=0) {
                throw new RuntimeException("illegal resource string");
            }
            if(bytes.length == 1) {
                asc = bytes[0];
            }
            if(bytes.length == 2) {
                int hightByte = 256+bytes[0];
                int lowByte = 256+bytes[1];
                asc = ( 256*hightByte+lowByte )-256*256;
            }
        }catch(Exception e) {
            System.out.println("ERROR:ChineseSpelling.class-getChsAscii(String chs)"+e);
        }
        return asc;
    }

    /** 单字解析 * * @param str * @return */
    public String convert(String str){
        String result = "#";
        int ascii = getChsAscii(str);
        if(ascii>0 && ascii<160) {
            result = String.valueOf((char)ascii);
        }else {
            for(int i = ( pyvalue.length-1 ); i>=0; i--) {
                if(pyvalue[i]<=ascii) {
                    result = pystr[i];
                    break;
                }
            }
        }
        return result;
    }

    /** 词组解析 * * @param chs * @return */
    public String getSelling(String chs){
        String key, value;
        buffer = new StringBuilder();
        for(int i = 0; i<chs.length(); i++) {
            key = chs.substring(i, i+1);
            if(key.getBytes().length>=2) {
                value = (String)convert(key);
                if(value == null) {
                    value = "unknown";
                }
            }else {
                value = key;
            }
            buffer.append(value);
        }
        return buffer.toString();
    }

    /**
     * 返回 词组首字母组合
     */
    public String getAcronym(String chs){
        char[] chars = chs.trim().toCharArray();
        StringBuilder acronym = new StringBuilder();
        for(char aChar : chars) {
            acronym.append(convert(aChar+"").charAt(0));
        }
        return acronym.toString();
    }

    public String getSpelling(){
        return this.getSelling(this.getResource());
    }

    /**
     * 支持全拼音 连续拆分匹配
     * 支持汉子 连续拆分匹配
     *
     * @param str
     *         需要匹配的 字符串
     * @param key
     *         匹配的关键字 可以是英文
     * @param partMatch
     *         是否支持拆分匹配
     * @return 支持匹配的关键字 集合
     */
    public List<String> getMatchString(String str, String key, boolean partMatch){
        str = str.toLowerCase();
        key = key.toLowerCase();
        ArrayList<String> matchss = new ArrayList<>();
        //找出匹配的字符
        if(key.matches("[a-zA-Z]+")) {
            //关键字全为英文
            //1,连续匹配 可能有多个
            String acronym = getAcronym(str);//将需要匹配的字符串转为首字母字符串
            int indexOf = 0;
            int keyLength = key.length();
            String acronymSub = acronym;
            String strSub = str;
            while(indexOf+keyLength<=acronymSub.length()) {//可能有多个（中国总过中国中国）--zg...//也满足单匹配 z
                if(acronymSub.contains(key)) {
                    indexOf = acronymSub.indexOf(key);
                    String match = strSub.substring(indexOf, indexOf+keyLength);
                    if(!matchss.contains(match)) {//如果匹配的集合里面没有就加到里面
                        matchss.add(match);
                    }
                }
                acronymSub = acronymSub.substring(indexOf+keyLength, acronymSub.length());
                strSub = strSub.substring(indexOf+keyLength, strSub.length());
            }
            //2，拆开 分开匹配  (中国国中工作)--zg
            if(partMatch) {
                ArrayList<String> matchssTemp = new ArrayList<>();
                HashSet machedChar = new HashSet();
                char[] charsKey = key.toCharArray();
                for(int i = 0; i<charsKey.length; i++) {
                    char[] chars = str.toCharArray();
                    if(machedChar.size()<i) {
                        break;
                    }
                    for(char aChar : chars) {
                        String convert = convert(aChar+"");//小写拼音
                        if(convert.startsWith(charsKey[i]+"")) {
                            machedChar.add(charsKey[i]);//有匹配则 添加到已匹配set中
                            if(!matchssTemp.contains(aChar+"")) {
                                matchssTemp.add(aChar+"");
                            }
                        }
                    }
                    if(i == charsKey.length-1 && machedChar.size() == charsKey.length) {//当拆开的每个字母都有匹配时
                        matchss.addAll(matchssTemp);
                    }
                }
            }
        }else {
            //非全英文
            //连续匹配
            if(str.contains(key)) {
                matchss.add(key);
            }
            if(partMatch) {
                //拆分匹配
                ArrayList<String> matchssTemp = new ArrayList<>();
                HashSet machedChar = new HashSet();
                char[] charsKey = key.toCharArray();
                for(int i = 0; i<charsKey.length; i++) {
                    char[] chars = str.toCharArray();
                    if(machedChar.size()<i) {
                        break;
                    }
                    for(char aChar : chars) {
                        if(( aChar+"" ).equals(charsKey[i]+"")) {
                            machedChar.add(charsKey[i]);//有匹配则 添加到已匹配set中
                            if(!matchssTemp.contains(aChar+"")) {
                                matchssTemp.add(aChar+"");
                            }
                        }
                    }
                    if(i == charsKey.length-1 && machedChar.size() == charsKey.length) {//当拆开的每个字母都有匹配时
                        matchss.addAll(matchssTemp);
                    }
                }
            }
        }
        return matchss;
    }

    public void textHighLighting(TextView tv, String str, String search, String color,boolean partMatch){

        List<String> matchss = new ArrayList<>();
        SpannableString spanStr = new SpannableString(str);
        //        matchss.addAll(getMatchString(str, search, matchss)};
        matchss = getMatchString(str, search, partMatch);

        for(String matchs : matchss) {
            //关键字高亮
            String args = matchs;//需要高亮的关键字
            Pattern p = Pattern.compile(matchs, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(spanStr);
            while(m.find()) {
                int start = m.start();
                int end = m.end();
                spanStr.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        tv.setText(spanStr);
    }

}
