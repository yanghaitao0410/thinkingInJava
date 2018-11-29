package application.master_selector.zkclient;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author yht
 * @create 2018/11/28
 */
public class UserCenter implements Serializable {

    private static final long serialVersionUID = 3609365926380513276L;
    private int mc_id;

    private String mc_name;

    public UserCenter() {
    }

    public UserCenter(int mc_id, String mc_name) {
        this.mc_id = mc_id;
        this.mc_name = mc_name;
    }

    public int getMc_id() {
        return mc_id;
    }

    public void setMc_id(int mc_id) {
        this.mc_id = mc_id;
    }


    public String getMc_name() {
        return mc_name;
    }

    public void setMc_name(String mc_name) {
        this.mc_name = mc_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCenter)) return false;
        UserCenter that = (UserCenter) o;
        return getMc_id() == that.getMc_id() &&
                Objects.equals(getMc_name(), that.getMc_name());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getMc_id(), getMc_name());
    }

    @Override
    public String toString() {
        return "UserCenter{" +
                "mc_id=" + mc_id +
                ", mc_name='" + mc_name + '\'' +
                '}';
    }
}
