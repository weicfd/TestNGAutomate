package entity;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 对应一个 操作序列 类型
 *
 *
 * methods Method类列表（ArrayList）
 * serviceName 服务名（String）
 * pat 模式（String） String内存放的是mType中对应的数字的组合，如124为Add-Update-Find操作序列
 * oracle 预期值半数据码映射表(Hashmap) key: 半数据码 datacode （Long） value: 参数名param name （String）
 */
public class Case {
    private List<Method> methods;
    private String serviceName, pat;
    private Map<Long, String> oracleMap;

    /**
     * construct 构造函数
     * @param methods 方法列表
     * @param serviceName 该用例的服务名称
     * @param pat 模式代号
     * @param oracle hashmap - key: 半数据码 - value: data datacode
     */
    public Case(List<Method> methods, String serviceName, String pat, Map<Long, String> oracle) {
        this.methods = methods;
        this.serviceName = serviceName;
        this.pat = pat;
        this.oracleMap = oracle;
    }

    /**
     * 获取方法列表
     * @return Method类列表（ArrayList）
     */
    public List<Method> getMethods() {
        return this.methods;
    }

    /**
     * 获取服务名
     * @return 服务名（String）
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     *
     * @return  模式（String） String内存放的是mType中对应的数字的组合，如124为Add-Update-Find操作序列
     */
    public String getPat() {
        return pat;
    }

    /**
     *
     * @return 预期值半数据码映射表(Hashmap) key: 半数据码 datacode （Long） value: 参数名param name （String）
     */
    public Map<Long, String> getOracle() {
        return oracleMap;
    }

    /**
     * 打印Case信息
     * @return
     */
    @Override
    public String toString() {
        return "Case:\n" +
                "methods=" + methods +
                ", serviceName='" + serviceName + '\'' +
                ", pat='" + pat + '\'' +
                ", oracleMap=" + oracleMap +
                '}' + '\n';
    }
}
