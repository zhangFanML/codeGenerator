package com.git.easyloan.utils.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    private static final Map<String, String> XML = new HashMap();
    private static final Random RANDOM;
    static Pattern three;
    static Pattern two;

    public StringHelper() {
    }

    public static String removeCrlf(String str) {
        return str == null ? null : join((Object[])tokenizeToStringArray(str, "\t\n\r\f"), " ");
    }

    public static String unescapeXml(String str) {
        if (str == null) {
            return null;
        } else {
            String key;
            String value;
            for(Iterator i$ = XML.keySet().iterator(); i$.hasNext(); str = replace(str, "&" + key + ";", value)) {
                key = (String)i$.next();
                value = (String)XML.get(key);
            }

            return str;
        }
    }

    public static String escapeXml(String str) {
        if (str == null) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                String escapedStr = getEscapedStringByChar(c);
                if (escapedStr == null) {
                    sb.append(c);
                } else {
                    sb.append(escapedStr);
                }
            }

            return sb.toString();
        }
    }

    public static String escapeXml(String str, String escapeChars) {
        if (str == null) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                if (escapeChars.indexOf(c) < 0) {
                    sb.append(c);
                } else {
                    String escapedStr = getEscapedStringByChar(c);
                    if (escapedStr == null) {
                        sb.append(c);
                    } else {
                        sb.append(escapedStr);
                    }
                }
            }

            return sb.toString();
        }
    }

    private static String getEscapedStringByChar(char c) {
        String escapedStr = null;
        Iterator i$ = XML.keySet().iterator();

        while(i$.hasNext()) {
            String key = (String)i$.next();
            String value = (String)XML.get(key);
            if (c == value.charAt(0)) {
                escapedStr = "&" + key + ";";
            }
        }

        return escapedStr;
    }

    public static String removePrefix(String str, String prefix) {
        return removePrefix(str, prefix, false);
    }

    public static String removePrefix(String str, String prefix, boolean ignoreCase) {
        if (str == null) {
            return null;
        } else if (prefix == null) {
            return str;
        } else {
            if (ignoreCase) {
                if (str.toLowerCase().startsWith(prefix.toLowerCase())) {
                    return str.substring(prefix.length());
                }
            } else if (str.startsWith(prefix)) {
                return str.substring(prefix.length());
            }

            return str;
        }
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getExtension(String str) {
        if (str == null) {
            return null;
        } else {
            int i = str.lastIndexOf(46);
            return i >= 0 ? str.substring(i + 1) : null;
        }
    }

    public static String insertBefore(String content, String compareToken, String insertString) {
        if (content.indexOf(insertString) >= 0) {
            return content;
        } else {
            int index = content.indexOf(compareToken);
            if (index >= 0) {
                return (new StringBuffer(content)).insert(index, insertString).toString();
            } else {
                throw new IllegalArgumentException("not found insert location by compareToken:" + compareToken + " content:" + content);
            }
        }
    }

    public static String insertAfter(String content, String compareToken, String insertString) {
        if (content.indexOf(insertString) >= 0) {
            return content;
        } else {
            int index = content.indexOf(compareToken);
            if (index >= 0) {
                return (new StringBuffer(content)).insert(index + compareToken.length(), insertString).toString();
            } else {
                throw new IllegalArgumentException("not found insert location by compareToken:" + compareToken + " content:" + content);
            }
        }
    }

    public static int countOccurrencesOf(String str, String sub) {
        if (str != null && sub != null && str.length() != 0 && sub.length() != 0) {
            int count = 0;

            int idx;
            for(int pos = 0; (idx = str.indexOf(sub, pos)) != -1; pos = idx + sub.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    public static boolean contains(String str, String... keywords) {
        if (str == null) {
            return false;
        } else if (keywords == null) {
            throw new IllegalArgumentException("'keywords' must be not null");
        } else {
            String[] arr$ = keywords;
            int len$ = keywords.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String keyword = arr$[i$];
                if (str.contains(keyword.toLowerCase())) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String defaultString(Object value) {
        return value == null ? "" : value.toString();
    }

    public static String defaultIfEmpty(Object value, String defaultValue) {
        return value != null && !"".equals(value) ? value.toString() : defaultValue;
    }

    public static String makeAllWordFirstLetterUpperCase(String sqlName) {
        String[] strs = sqlName.toLowerCase().split("_");
        String result = "";
        String preStr = "";

        for(int i = 0; i < strs.length; ++i) {
            if (preStr.length() == 1) {
                result = result + strs[i];
            } else {
                result = result + capitalize(strs[i]);
            }

            preStr = strs[i];
        }

        return result;
    }

    public static int indexOfByRegex(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.find() ? m.start() : -1;
    }

    public static String toJavaVariableName(String str) {
        return uncapitalize(toJavaClassName(str));
    }

    public static String toJavaClassName(String str) {
        return makeAllWordFirstLetterUpperCase(toUnderscoreName(str));
    }

    public static String getJavaClassSimpleName(String clazz) {
        if (clazz == null) {
            return null;
        } else {
            return clazz.lastIndexOf(".") >= 0 ? clazz.substring(clazz.lastIndexOf(".") + 1) : clazz;
        }
    }

    public static String removeMany(String inString, String... keywords) {
        if (inString == null) {
            return null;
        } else {
            String[] arr$ = keywords;
            int len$ = keywords.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String k = arr$[i$];
                inString = replace(inString, k, "");
            }

            return inString;
        }
    }

    public static void appendReplacement(Matcher m, StringBuffer sb, String replacement) {
        replacement = replace(replacement, "$", "\\$");
        m.appendReplacement(sb, replacement);
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (inString == null) {
            return null;
        } else if (oldPattern != null && newPattern != null) {
            StringBuffer sbuf = new StringBuffer();
            int pos = 0;
            int index = inString.indexOf(oldPattern);

            for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                sbuf.append(inString.substring(pos, index));
                sbuf.append(newPattern);
                pos = index + patLen;
            }

            sbuf.append(inString.substring(pos));
            return sbuf.toString();
        } else {
            return inString;
        }
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str != null && str.length() != 0) {
            StringBuffer buf = new StringBuffer(str.length());
            if (capitalize) {
                buf.append(Character.toUpperCase(str.charAt(0)));
            } else {
                buf.append(Character.toLowerCase(str.charAt(0)));
            }

            buf.append(str.substring(1));
            return buf.toString();
        } else {
            return str;
        }
    }

    public static String randomNumeric(int count) {
        return random(count, false, true);
    }

    public static String random(int count, boolean letters, boolean numbers) {
        return random(count, 0, 0, letters, numbers);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers) {
        return random(count, start, end, letters, numbers, (char[])null, RANDOM);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        } else {
            if (start == 0 && end == 0) {
                end = 123;
                start = 32;
                if (!letters && !numbers) {
                    start = 0;
                    end = 2147483647;
                }
            }

            char[] buffer = new char[count];
            int gap = end - start;

            while(true) {
                while(true) {
                    while(count-- != 0) {
                        char ch;
                        if (chars == null) {
                            ch = (char)(random.nextInt(gap) + start);
                        } else {
                            ch = chars[random.nextInt(gap) + start];
                        }

                        if (letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters && !numbers) {
                            if (ch >= '\udc00' && ch <= '\udfff') {
                                if (count == 0) {
                                    ++count;
                                } else {
                                    buffer[count] = ch;
                                    --count;
                                    buffer[count] = (char)('\ud800' + random.nextInt(128));
                                }
                            } else if (ch >= '\ud800' && ch <= '\udb7f') {
                                if (count == 0) {
                                    ++count;
                                } else {
                                    buffer[count] = (char)('\udc00' + random.nextInt(128));
                                    --count;
                                    buffer[count] = ch;
                                }
                            } else if (ch >= '\udb80' && ch <= '\udbff') {
                                ++count;
                            } else {
                                buffer[count] = ch;
                            }
                        } else {
                            ++count;
                        }
                    }

                    return new String(buffer);
                }
            }
        }
    }

    public static String toUnderscoreName(String name) {
        if (name == null) {
            return null;
        } else {
            String filteredName = name;
            if (name.indexOf("_") >= 0 && name.equals(name.toUpperCase())) {
                filteredName = name.toLowerCase();
            }

            if (filteredName.indexOf("_") == -1 && filteredName.equals(filteredName.toUpperCase())) {
                filteredName = filteredName.toLowerCase();
            }

            StringBuffer result = new StringBuffer();
            if (filteredName != null && filteredName.length() > 0) {
                result.append(filteredName.substring(0, 1).toLowerCase());

                for(int i = 1; i < filteredName.length(); ++i) {
                    String preChart = filteredName.substring(i - 1, i);
                    String c = filteredName.substring(i, i + 1);
                    if (c.equals("_")) {
                        result.append("_");
                    } else if (preChart.equals("_")) {
                        result.append(c.toLowerCase());
                    } else if (c.matches("\\d")) {
                        result.append(c);
                    } else if (c.equals(c.toUpperCase())) {
                        result.append("_");
                        result.append(c.toLowerCase());
                    } else {
                        result.append(c);
                    }
                }
            }

            return result.toString();
        }
    }

    public static String removeEndWiths(String inputString, String... endWiths) {
        String[] arr$ = endWiths;
        int len$ = endWiths.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String endWith = arr$[i$];
            if (inputString.endsWith(endWith)) {
                return inputString.substring(0, inputString.length() - endWith.length());
            }
        }

        return inputString;
    }


    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        for(int j = 0; j < substring.length(); ++j) {
            int i = index + j;
            if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
                return false;
            }
        }

        return true;
    }

    public static String[] tokenizeToStringArray(String str, String seperators) {
        if (str == null) {
            return new String[0];
        } else {
            StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
            ArrayList result = new ArrayList();

            while(tokenlizer.hasMoreElements()) {
                Object s = tokenlizer.nextElement();
                result.add(s);
            }

            return (String[])((String[])result.toArray(new String[result.size()]));
        }
    }

    public static String join(List list, String seperator) {
        return join(list.toArray(new Object[0]), seperator);
    }

    public static String replace(int start, int end, String str, String replacement) {
        String before = str.substring(0, start);
        String after = str.substring(end);
        return before + replacement + after;
    }

    public static String join(Object[] array, String seperator) {
        if (array == null) {
            return null;
        } else {
            StringBuffer result = new StringBuffer();

            for(int i = 0; i < array.length; ++i) {
                result.append(array[i]);
                if (i != array.length - 1) {
                    result.append(seperator);
                }
            }

            return result.toString();
        }
    }

    public static int containsCount(String string, String keyword) {
        if (string == null) {
            return 0;
        } else {
            int count = 0;

            int indexOf;
            for(int i = 0; i < string.length(); i = indexOf + 1) {
                indexOf = string.indexOf(keyword, i);
                if (indexOf < 0) {
                    break;
                }

                ++count;
            }

            return count;
        }
    }

    public static String getByRegex(String str, String regex) {
        return getByRegex(str, regex, 0);
    }

    public static String getByRegex(String str, String regex, int group) {
        if (regex == null) {
            throw new NullPointerException();
        } else if (group < 0) {
            throw new IllegalArgumentException();
        } else if (str == null) {
            return null;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            return m.find() ? m.group(group) : null;
        }
    }

    public static String removeIbatisOrderBy(String sql) {
        String orderByRemovedSql = sql.replaceAll("(?si)<\\w+[^>]*?>\\s*order\\s+by\\s+[^<]+?</\\w+>", "").replaceAll("(?i)<\\w+[\\w\\s='\"]+prepend[\\w\\s='\"]*?order\\s+by\\s*['\"][^>]*?>[^<]+</\\w+>", "").replaceAll("(?i)\\s*order\\s+by\\s+.*", "");
        return removeXmlTagIfBodyEmpty(removeXmlTagIfBodyEmpty(removeXmlTagIfBodyEmpty(removeXmlTagIfBodyEmpty(orderByRemovedSql))));
    }

    public static String removeXmlTagIfBodyEmpty(String sql) {
        return sql.replaceAll("<\\w+[^>]*?>\\s+</\\w+>", "");
    }

    public static void main(String[] args) {
        System.out.println(randomNumeric(6));
    }

    static {
        XML.put("apos", "'");
        XML.put("quot", "\"");
        XML.put("amp", "&");
        XML.put("lt", "<");
        XML.put("gt", ">");
        RANDOM = new Random();
        three = Pattern.compile("(.*)\\((.*),(.*)\\)");
        two = Pattern.compile("(.*)\\((.*)\\)");
    }
}
