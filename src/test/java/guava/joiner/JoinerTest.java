package guava.joiner;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableMap.of;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @ClassName JoinerTest
 * @Description
 * @Author agan
 * @Date 2021/2/28 14:27
 **/


public class JoinerTest {

    private  final List<String> stringList = Arrays.asList("张三","李四","王五");

    private final List<String> stringListWithNull = Arrays.asList("张三","李四","王五",null);

//    private final Map<String,String> stringMap = ImmutableMap.of("张三","李四","王五");
    private final String fileName = "C:\\Users\\agan\\Desktop\\test";
    @Test
    public void testJoinOn(){
        String result = Joiner.on("#").join(stringList);
        assertThat(result,equalTo("张三#李四#王五"));
    }

    @Test(expected = NullPointerException.class)
    public void testJoinOnWithNullValue(){
        String result = Joiner.on("#").join(stringListWithNull);
        assertThat(result,equalTo("张三#李四#王五#嘿嘿嘿"));
    }

    @Test
    public void testJoinOnWithNullValueButSkip(){
        String result = Joiner.on("#").skipNulls().join(stringListWithNull);
        assertThat(result,equalTo("张三#李四#王五"));
    }

    @Test
    public void testJoinOnWithNullValueButUseDefaultValue(){
        String result = Joiner.on("#").useForNull("默认值").join(stringListWithNull);
        assertThat(result,equalTo("张三#李四#王五#默认值"));
    }

    @Test
    public void testJoin_on_Append_To_StringBuilder(){
        final StringBuilder builder= new StringBuilder();
        StringBuilder builder1 = Joiner.on("#").useForNull("默认值").appendTo(builder, stringListWithNull);
        assertThat(builder1,sameInstance(builder));
        assertThat(builder.toString(),equalTo("张三#李四#王五#默认值"));
        assertThat(builder1.toString(),equalTo("张三#李四#王五#默认值"));
    }

    @Test
    public void testJoin_on_append_to_write(){
        try {
            FileWriter writer = new FileWriter(new File(fileName));
            Joiner.on("#").useForNull("默认值").appendTo(writer,stringListWithNull);
//            assertThat(Files.isFile().test(new File(fileName)),equalTo(true));
        } catch (IOException e) {
            fail("append to the write occur fetal error");
        }
    }

    @Test
    public void testJoinOnByStreamSkipNullValue(){
        String collect = stringListWithNull.stream().filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining("#"));
        assertThat(collect,equalTo("张三#李四#王五"));
    }

    @Test
    public void testJoinOnByStreamWithDefaultValue(){
         String result = stringListWithNull.stream()
//                .map(s -> s == null || s.isEmpty() ? "默认值" : s)
                 .map(this::defaultValue)
                 .collect(Collectors.joining("#"));
        assertThat(result,equalTo("张三#李四#王五#默认值"));
    }
    private String defaultValue(final String s){
       return    s == null || s.isEmpty() ? "默认值" : s;
    }
}
