package entity;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Case {
    List<Method> methods;
    String serviceName, pat;
    Map<Long, String> oracleMap;

    /**
     * construct
     * @param methods
     * @param serviceName
     * @param pat
     * @param oracle hashmap - key: param name - value: data datacode
     */
    public Case(List<Method> methods, String serviceName, String pat, Map<Long, String> oracle) {
        this.methods = methods;
        this.serviceName = serviceName;
        this.pat = pat;
        this.oracleMap = oracle;
    }

    public List<Method> getMethods() {
        return this.methods;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPat() {
        return pat;
    }

    public Map<Long, String> getOracle() {
        return oracleMap;
    }

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
