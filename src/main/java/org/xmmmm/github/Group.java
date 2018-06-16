package org.xmmmm.github;

public class Group {
    private long flag;
    private String name;
    private long gid;
    private long code;

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Group{" +
                "flag=" + flag +
                ", name='" + name + '\'' +
                ", gid=" + gid +
                ", code=" + code +
                '}';
    }
}
