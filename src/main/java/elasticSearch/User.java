package elasticSearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @ClassName User
 * @Description
 * @Author wpj
 * @Date 2021/6/15 21:26
 **/



@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String name;
    Integer age;
}
