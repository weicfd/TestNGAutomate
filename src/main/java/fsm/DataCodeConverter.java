package fsm;

import entity.Method;

import java.util.List;

public class DataCodeConverter {
    final static int mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0;

    /**
     * 使用哈希函数将数据的信息转化为半数据码
     * @param mServiceName 服务名
     * @param attribute 数据的属性（实体名+参数名）
     * @return 半数据码
     */
    public static long castAttrToCode(String mServiceName, String attribute) {
//        System.out.println(attribute);
//        System.out.println("cast:" + mServiceName + ", " + attribute);
        if (mServiceName == null || attribute == null) {
            System.err.println("attribute format error");
            System.exit(0);
        }
        long code = 0;
        code += hash(mServiceName);
//        System.out.println("hashing " + mServiceName + " to " + code);
        code *= 1000;
        String[] attr = attribute.split("\\.");
        if (attr.length < 1 || attr.length > 2) {
//            System.err.println("attribute format error for " + mServiceName);
            return 0;
        }
        code += hash(attr[0]);
//        System.out.println("hashing " + attr[0] + " to " + code % 1000);
        code *= 1000;
        code += attr.length == 1 ? 0 : hash(attr[1]);
//        System.out.println("hashing " + attr[1] + " to " + code % 1000);
        code *= 1000;

//        System.out.println("code:" + code);
        return code;
    }

    /**
     * suppose the max size is 3 bit
     *
     * @param str
     * @return
     */
    private static int hash(String str) {
        int hash = 7;
        for (int i = 0; i < str.length(); i++) {
            hash = hash*31 + str.charAt(i);
        }
        return Math.abs(hash % 1000);
    }

    /**
     * 根据Method列表来判断当前序列可以生成的用例编号（可扩展）
     * @param methods 操作序列列表
     * @return 用例标号的数组
     */
    public static int[] getCases(List<Method> methods) {
        int temp = methods.size() - 2;
        int mType = methods.get(temp).getmType(), m_last_type = -1;
        long m_p_star = methods.get(temp).getLocator();
        // find the m'
        for (int i = temp - 1; i >= 0; i--) {
            if (methods.get(i).getDataCodeMap().containsKey(m_p_star))
                m_last_type = methods.get(i).getmType();
        }
        switch (mType) {
            case mADD:
                if (m_last_type == mADD) return new int[]{2};
                else return new int[]{1};
            case mUPDATE:
                if (m_last_type == mADD) return new int[]{3};
                else if (m_last_type == mUPDATE) return new int[]{4};
                else if (m_last_type == mDELETE) return new int[]{5};
                else System.err.println("pattern error");
                break;
            case mDELETE:
                if (m_last_type == mADD) return new int[]{6};
                else if (m_last_type == mUPDATE) return new int[]{7};
                else if (m_last_type == mDELETE) return new int[]{8};
                else System.err.println("pattern error");
                break;
            case mFIND:
                return new int[]{9};
            case mUNKNOWN:
                System.err.println("unknown method type:" + mType);
                break;
        }
        return new int[]{};
    }
}
