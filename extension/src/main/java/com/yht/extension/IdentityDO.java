package com.yht.extension;

import lombok.Data;

import java.util.Objects;

@Data
public class IdentityDO implements Cloneable {

    private String level1;      //一级定义

    private String level2 = BusinessIdentityUtil.IDENTITY_DEFAULT_VALUE;    //二级定义

    private String level3 = BusinessIdentityUtil.IDENTITY_DEFAULT_VALUE;    //三级定义

    @Override
    public IdentityDO clone() {
        return JsonUtil.clonePojo(this);
    }

    public IdentityDO() {
    }

    public IdentityDO(String level1, String level2, String level3) {
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        IdentityDO that = (IdentityDO)o;
        return Objects.equals(level1, that.level1) &&
                Objects.equals(level2, that.level2) &&
                Objects.equals(level3, that.level3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level1, level2, level3);
    }

    @Override
    public String toString() {
        return BusinessIdentityUtil.generatePageSceneId(this);
    }

    public String toBizCodeString() {
        return BusinessIdentityUtil.generatePageSceneId(this);
    }

}
