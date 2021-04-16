package 软件架构设计的七大原则.迪米特法则;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @ClassName Boss
 * @Description
 * @Author agan
 * @Date 2021/4/15 22:11
 **/

public class Boss {
    public void commandCheckNum(TeamLeader teamLeader){
        List<Clouse> clouses = Lists.newArrayListWithExpectedSize(10);
//        模拟Boss查看课程,team统计
        for (int i = 0; i <10 ; i++) {
           clouses.add(new Clouse());
        }
        teamLeader.checkClouseNum(clouses);
    }
//    优化
    public void commandCheck(TeamLeader teamLeader){
        teamLeader.checkClouseNumber();
    }
}
