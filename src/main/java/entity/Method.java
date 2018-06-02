package entity;

import java.util.List;
import java.util.Map;

public class Method {
    final int mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0;
    String methodName;
    int mType = mUNKNOWN;
    Map<Long,String> dataCodeMap;
    long locationCode;
//    List<String> targets;

    public Method(String methodName, int mType, Map<Long, String> dataCodeMap, long locationCode) {
        this.methodName = methodName;
        this.mType = mType;
        this.dataCodeMap = dataCodeMap;
//        this.targets = targets;
        this.locationCode = locationCode;
    }

    public String getMethodName() {
        return methodName;
    }
    public int getmType() {
        return mType;
    }

    public Map<Long,String> getDataCodeMap() {
        return dataCodeMap;
    }

//    public List<String> getTargets() {
//        return targets;
//    }

    public long getLocator() {
        return locationCode;
    }

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
