package fsm;

import entity.Case;
import entity.Method;

import java.util.*;

public class DataFsm {
    int cursor, caseNo;
//    final int sStart = 1, sStandard = 2, sTest = 3, sEnd = 4;
    final int mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0;
    Map<Long, Integer> usedCodeList; // half-code, data_no
    List<Method> methods;
    int num;
    long m_p_star;
    Method m, m_f, m_last;
    Map<Long, String> targetCode;

    private DataFsm() {
        usedCodeList = new HashMap<>();
    }

    private static DataFsm dataFsm = new DataFsm();

    public static DataFsm getDataFsm() {
        return dataFsm;
    }

    public void init(List<Method> methods, int caseNo) {
        usedCodeList.clear();
        this.methods = methods;
        if (methods.size() < 2) System.err.println("methods len error");
        num = methods.size();
        m_f = methods.get(num - 1);
        cursor = 0;
        m = methods.get(cursor);
        m_p_star = m.getLocator();
        m_last = null;
        this.caseNo = caseNo;
    }

    public void next() {
        cursor++;
        m = methods.get(cursor);
        m_last = null;
        m_p_star = m.getLocator();
        // find the m'
        for (int i = cursor - 1; i >= 0; i--) {
            if (methods.get(i).getDataCodeMap().containsKey(m_p_star))
                m_last = methods.get(i);
        }
    }

    public long getData(long dataCode) {
        return dataCode + getIndex(dataCode);
    }

    public long getIndex(long dataCode) {
//        System.out.println("DataCode: " + dataCode );
        int res = -1;

        if (cursor < num - 2) {
            // standard state
            if (m_last == null) {
                // new entity
                res = usedCodeList.getOrDefault(dataCode, 0) + 1; // 从1开始标号
                usedCodeList.put(dataCode, res);
                return res;
            } else {
                switch (m.getmType()) {
                    case mADD:
                        res = usedCodeList.getOrDefault(dataCode, 0) + 1;
                        usedCodeList.put(dataCode, res);
                        return res;

                    case mUPDATE:
                        if (dataCode == m_p_star) return usedCodeList.get(dataCode);
                        else {
                            res = usedCodeList.getOrDefault(dataCode, 0) + 1;
                            usedCodeList.put(dataCode, res);
                            return res;
                        }
                    case mDELETE:
                        if (m_last.getmType() == mDELETE) {
                            res = usedCodeList.get(dataCode) - 1;
                            usedCodeList.put(dataCode, res);
                            return res;
                        } else {
                            return usedCodeList.get(dataCode);
                        }
                    case mFIND:
                        return usedCodeList.get(dataCode);
                    default:
                        return 0;

                }
            }
        } else if (cursor == num - 2) {
            // test state
            switch (caseNo) {
                case 1:
                    res = usedCodeList.getOrDefault(dataCode, 0) + 1;
                    usedCodeList.put(dataCode, res);
                    return res;
                case 2:
                    return usedCodeList.get(dataCode);
                case 3:
                case 4:
                case 5:
                    if (dataCode == m_p_star) return usedCodeList.get(dataCode);
                    else {
                        res = usedCodeList.getOrDefault(dataCode, 0) + 1;
                        usedCodeList.put(dataCode, res);
                        return res;
                    }
                case 6:
                case 7:
                case 8:
                    if (dataCode != m_p_star) System.err.println("delete method error");
                    return usedCodeList.get(dataCode);
                case 9:
                    return usedCodeList.get(dataCode);
                default:
                    break;
            }
        } else {
            // find state
            return usedCodeList.get(dataCode);
        }
        return res;
    }

    public long getOracle(Long dataCode) {
        long p_find = m_f.getLocator();
        switch (caseNo) {
            case 1:
            case 3:
            case 4:
            case 9:
                return dataCode + usedCodeList.get(dataCode);
            case 2:
            case 5:
            case 8:
                return 0;
            case 6:
            case 7:
                if (p_find == m_p_star) return 0;
                else return dataCode + usedCodeList.get(dataCode);
        }
        return dataCode + 0;
    }

//
//    public List<String> getTarget() {
//        List<String> target = new ArrayList<>();
//        for (long code :
//                m_f.getDataCodeMap().keySet()) {
//            if (code != m_f.getLocator()) target.add(m_f.getDataCodeMap().get(code));
//        }
//        return target;
//    }
}
