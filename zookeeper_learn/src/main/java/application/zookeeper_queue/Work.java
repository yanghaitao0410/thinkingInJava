package application.zookeeper_queue;

import java.io.Serializable;

/**
 * @author yht
 * @create 2018/11/29
 */
public class Work implements Serializable {
    private static final long serialVersionUID = 3762651195416871285L;

    private int workId;
    private String workName;

    public Work(int workId, String workName) {
        this.workId = workId;
        this.workName = workName;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    @Override
    public String toString() {
        return "Work{" +
                "workId=" + workId +
                ", workName='" + workName + '\'' +
                '}';
    }
}
