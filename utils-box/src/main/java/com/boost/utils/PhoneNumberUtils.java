package com.boost.utils;

import java.util.regex.Pattern;

/**
 * @author: qdj
 * @date: 2019-11-13 11:39
 **/
public class PhoneNumberUtils {

    /**
     * ————————————————————————————————————————————————————————————————————————————————————————————
     * 移动号段：
     * 134 135 136 137 138 139 147 148 150 151 152 157 158 159 165 172 178 182 183 184 187 188 198
     *
     * 联通号段：
     * 130 131 132 145 146 155 156 166 171 175 176 185 186
     *
     * 电信号段：
     * 133 149 153 173 174 177 180 181 189 191 199
     *
     * 虚拟运营商:
     * 170
     * —————————————————————————————————————————————————————————————————————————————————————————————
     */
    private static final String SIMPLE_REGEX = "^1[3-9][0-9]\\d{8}$";
    private static final String STRICT_REGEX = "^((13[0-9])|(14[5-8])|(15([0-3]|[5-9]))|(17[0-8])|(18[0-9])|(19[189]))\\d{8}$";
    private static final Pattern SIMPLE_PATTERN = Pattern.compile(SIMPLE_REGEX);
    private static final Pattern STRICT_PATTERN = Pattern.compile(STRICT_REGEX);

    public class CheckLevel{
        public static final int SIMPLE = 0;
        public static final int STRICT = 1;
    }

    public static boolean isPhoneNumber(String phoneNumber, int pattern){
        if (pattern == CheckLevel.SIMPLE){
            return SIMPLE_PATTERN.matcher(phoneNumber).matches();
        } else if (pattern == CheckLevel.STRICT){
            return STRICT_PATTERN.matcher(phoneNumber).matches();
        } else {
            throw new IllegalArgumentException("invalid pattern!");
        }
    }

}