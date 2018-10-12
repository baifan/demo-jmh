package tech.weiyi.demo.metric;

public class MethodCall {

    public static final String SEPARATOR = "#";

    private int frameworkId;

    private String operationName;

    private volatile String frameworkIdr;

    /**
     * 公共构造函数
     */
    public MethodCall() {
        super();
    }

    public MethodCall(int frameworkId, String operationName) {
        this.frameworkId = frameworkId;
        this.operationName = operationName;
        this.frameworkIdr = frameworkId + SEPARATOR + operationName;
    }

    public int getFrameworkId() {
        return frameworkId;
    }

    public void setFrameworkId(int frameworkId) {
        this.frameworkId = frameworkId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != MethodCall.class) {
            return false;
        }
        if (this == o) {
            return true;
        }

        MethodCall that = (MethodCall) o;
        return operationName.equals(that.operationName) && frameworkId == that.frameworkId;
    }

    @Override
    public int hashCode() {
        return operationName.hashCode();
    }

    public String toIdr() {
        if (frameworkIdr == null) {
            frameworkIdr = frameworkId + SEPARATOR + operationName;
        }
        return frameworkIdr;
    }
}
