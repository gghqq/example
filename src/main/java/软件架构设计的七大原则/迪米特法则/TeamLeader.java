package 软件架构设计的七大原则.迪米特法则;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @ClassName TeamLeader
 * @Description
 * @Author agan
 * @Date 2021/4/15 22:09
 **/

public class TeamLeader {
    public void checkClouseNum(List<Clouse> clouseList){
        System.out.println("Boss,课程总数是: " + clouseList.size());
    }

    public void checkClouseNumber(){
        List<Clouse> clouses = Lists.newArrayListWithExpectedSize(10);
//       team课程,team统计
        for (int i = 0; i <10 ; i++) {
            clouses.add(new Clouse());
        }
        System.out.println("Boss,课程总数是: " + clouses.size());

    }
}
