package entity;

import java.util.List;
import java.util.Map;

/**
 * 对应一个 操作 类型
 * methodName 操作名（String）
 * mType 操作的类型（Int） ：mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0
 * dataCodeMap 操作参数半数据码映射表(Hashmap)
 *             key: 半数据码（Long），半数据码是指最后一位数据ID全为0的数据码
 *             value: 数据对应的参数名（String）
 * locationCode location数据元素对应的值
 */
public class Method {
    final int mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0;
    String methodName;
    int mType = mUNKNOWN;
    Map<Long,String> dataCodeMap;
    long locationCode;
//    List<String> targets;

    /**
     * 构造方法
     * @param methodName 操作名（String）
     * @param mType 操作的类型（Int） ：mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0
     * @param dataCodeMap 操作参数半数据码映射表(Hashmap)
     * @param locationCode locationCode location数据元素对应的值
     */
    public Method(String methodName, int mType, Map<Long, String> dataCodeMap, long locationCode) {
        this.methodName = methodName;
        this.mType = mType;
        this.dataCodeMap = dataCodeMap;
//        this.targets = targets;
        this.locationCode = locationCode;
    }

    /**
     *
     * @return methodName 操作名（String）
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     *
     * @return mType 操作的类型（Int） ：mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0
     */
    public int getmType() {
        return mType;
    }

    /**
     *
     * @return dataCodeMap 操作参数半数据码映射表(Hashmap)
     */
    public Map<Long,String> getDataCodeMap() {
        return dataCodeMap;
    }

//    public List<String> getTargets() {
//        return targets;
//    }

    /**
     *
     * @return locationCode locationCode location数据元素对应的值
     */
    public long getLocator() {
        return locationCode;
    }

    /**
     * 打印Method信息
     * @return
     */
    @Override
    public String toString() {
        return "\nMethod{" +
                "methodName='" + methodName + '\'' +
                ", mType=" + mType +
                ", dataCodeMap=" + dataCodeMap +
                ", locationCode=" + locationCode +
                "}\n";
    }
}
