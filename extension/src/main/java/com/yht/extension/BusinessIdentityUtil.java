package com.yht.extension;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class BusinessIdentityUtil {

    /**
     * identity attr's default value
     */
    public static final String IDENTITY_DEFAULT_VALUE = "default";

    /**
     * scene id delimiter in application layer
     */
    private static final String APP_SCENE_ID_DELIMITER = "-";

    /**
     * tmf biz code delimiter in domain layer
     */
    private static final String TMF_BIZ_CODE_DELIMITER = ".";

    /**
     * identity string format's part number
     */
    private static final int IDENTITY_STRING_PARTS = 3;

    /**
     * generate scene ID in application layer
     *
     * @param identityDO scene identity
     * @return identity string
     */
    public static String generatePageSceneId(IdentityDO identityDO) {
        StringBuilder sb = new StringBuilder();
        sb.append(identityDO.getLevel1()).append(APP_SCENE_ID_DELIMITER).append(
                identityDO.getLevel2()).append(APP_SCENE_ID_DELIMITER).append(identityDO.getLevel3());
        return sb.toString().toLowerCase();
    }

    /**
     * generate biz code in TMF
     *
     * @param identityDO identity
     * @return TMF biz code
     */
    public static String generateBizCode(IdentityDO identityDO) {
        return generatePageSceneId(identityDO).replaceAll(APP_SCENE_ID_DELIMITER, TMF_BIZ_CODE_DELIMITER);
    }

    /**
     * generate default biz code list
     *
     * @param identityDO
     * @return default biz code list
     */
    public static List<String> getDefaultBizCodeList(IdentityDO identityDO) {
        List<String> defaultBizCodeList = new ArrayList<>(3);
        IdentityDO defaultDO = identityDO.clone();
        defaultDO.setLevel3(IDENTITY_DEFAULT_VALUE);
        defaultBizCodeList.add(defaultDO.toBizCodeString());
        defaultDO.setLevel2(IDENTITY_DEFAULT_VALUE);
        defaultBizCodeList.add(defaultDO.toBizCodeString());
        defaultDO.setLevel1(IDENTITY_DEFAULT_VALUE);
        defaultBizCodeList.add(defaultDO.toBizCodeString());
        return defaultBizCodeList;
    }

    /**
     * parse identity from string format
     *
     * @param identity
     * @return
     */
    public static IdentityDO parseIdentity(String identity) {
        if (StringUtils.isEmpty(identity)) {
            return null;
        }
        String[] identitys = identity.split(APP_SCENE_ID_DELIMITER);
        if (identitys.length != IDENTITY_STRING_PARTS) {
            return null;
        }
        IdentityDO identityDO = new IdentityDO();
        identityDO.setLevel1(identitys[0]);
        identityDO.setLevel2(identitys[1]);
        identityDO.setLevel3(identitys[2]);
        return identityDO;
    }

    /**
     * @return default identity
     */
    public static IdentityDO getDefaultIdentity() {
        //每次创建一个新的目的是防止某个线程对该对象的修改
        return new IdentityDO(IDENTITY_DEFAULT_VALUE, IDENTITY_DEFAULT_VALUE,
                IDENTITY_DEFAULT_VALUE);
    }

    public static List<String> getAllDefaultIdentity(String identity) {
        String[] sceneParts = identity.split("-");

        Map<Integer, String> nonDefaultPartMap = new HashMap<>();
        int index = 0;
        for (int i = 0; i < sceneParts.length; i++) {
            if (!IDENTITY_DEFAULT_VALUE.equalsIgnoreCase(sceneParts[i])) {
                nonDefaultPartMap.put(index, sceneParts[i]);
                index++;
            }
        }

        // 列出全部可能
        List<List<String>> identityPartsList = new ArrayList<>();
        double count = Math.pow(2, nonDefaultPartMap.size());
        for (long i = 0; i < count; i++) {
            String l = Long.toBinaryString(i);
            StringBuilder binarySb = new StringBuilder();
            for (int k = 0; k < nonDefaultPartMap.size() - l.length(); k++) {
                binarySb.append("0");
            }
            l = binarySb.append(l).toString();
            List<String> identityParts = new ArrayList<>(4);
            StringBuilder sb = new StringBuilder();
            int appendDefaultCount = 0;
            for (int j = 0; j < l.length(); j++) {
                if (l.charAt(j) == '1') {
                    identityParts.add(IDENTITY_DEFAULT_VALUE);
                } else {
                    identityParts.add(nonDefaultPartMap.get(j));
                }
            }
            identityPartsList.add(identityParts);
        }

        // 补全identity数据
        for (List<String> nonDefaultParts:identityPartsList){
            for (int k = 0; k < sceneParts.length; k ++){
                if (IDENTITY_DEFAULT_VALUE.equals(sceneParts[k])){
                    nonDefaultParts.add(k, IDENTITY_DEFAULT_VALUE);
                }
            }
        }

        // 排序
        identityPartsList.sort(new Comparator<List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                int count1 = Collections.frequency(o1, IDENTITY_DEFAULT_VALUE);
                int count2 = Collections.frequency(o2, IDENTITY_DEFAULT_VALUE);
                if (count1 != count2) {
                    return count1 < count2 ? -1 : 1;
                }
                int lastIndex11 = o1.lastIndexOf(IDENTITY_DEFAULT_VALUE);
                int lastIndex21 = o2.lastIndexOf(IDENTITY_DEFAULT_VALUE);
                if (lastIndex11 != lastIndex21) {
                    return lastIndex11 > lastIndex21 ? -1 : 1;
                }
                int lastIndex12 = o1.subList(0, lastIndex11).lastIndexOf(IDENTITY_DEFAULT_VALUE);
                int lastIndex22 = o2.subList(0, lastIndex21).lastIndexOf(IDENTITY_DEFAULT_VALUE);
                return lastIndex12 > lastIndex22 ? -1 : 1;
            }
        });

        // 将第三部分及后面的尾部连续默认值进行去重
        for (List<String> parts : identityPartsList) {
            for (int i = parts.size() - 1; i > 2; i--) {
                if (IDENTITY_DEFAULT_VALUE.equals(parts.get(i))) {
                    parts.remove(i);
                } else {
                    break;
                }
            }
        }

        // 转换为string
        List<String> result = new ArrayList<>();
        for (List<String> parts : identityPartsList){
            result.add(StringUtils.join(parts, "-"));
        }

        return result;
    }

}
