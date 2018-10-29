package com.yht.nowcode.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 一些项目需要占用一个教室进行宣讲，教室不能同时容纳两个项目的宣讲。
 * 给你每一个项目的开始时间和结束时间，你来安排宣讲的日程，要求教室进行的宣讲的场次最多
 * 返回最多的场次。
 *
 * 实现贪心策略，然后实现一种保证对的方法（如何实现这个方法？），然后使用对数器进行验证。
 *  对的策略：按照项目结束时间早进行排序，选定一个项目后，淘汰举办时间在该项目结束时间前面的项目，
 *      然后继续寻找下一个结束时间短的项目。
 */
public class Seminar {
    //这里做了简化处理，时间都为整数
    class Project{
        int startTime;
        int endTime;
        public Project(int startTime, int endTime){
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
    class ProjectComparator implements Comparator<Project>{
        @Override
        public int compare(Project o1, Project o2) {
            return o1.endTime - o2.endTime;
        }
    }

    /**
     * @param projects 项目数组
     * @param curTime 当前时间
     * @return
     */
    public int maxTimes(Project[] projects, int curTime) {
        Arrays.sort(projects, new ProjectComparator());
        int result = 0;
        for(int i = 0; i < projects.length; i++) {
            if(curTime < projects[i].startTime){ //当前项目开始时间晚于当前时间才可以选中
                result++;
                curTime = projects[i].endTime;
            }
        }
        return result;
    }
}
