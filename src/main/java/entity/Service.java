package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    final int mADD = 1, mUPDATE = 2, mDELETE = 3, mFIND = 4, mUNKNOWN = 0;
    private String service_name;
    private Map<Integer, List<Method>> methods;

    public Service(String service_name) {
        this.service_name = service_name;
        methods = new HashMap<Integer, List<Method>>();

        for (int i = 0; i < 5; i++) {
            methods.put(i, new ArrayList<Method>());
        }
    }

    public String getService_name() {
        return service_name;
    }

    public void addMethod(Method method) {
        methods.get(method.getmType()).add(method);
    }

    public List<Method> getMethods(Integer mKey) {
        return methods.get(mKey);
    }
}
