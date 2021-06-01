package mysql事务;

public interface UserService {
    int update(User user);
    User getU(int id);
}
