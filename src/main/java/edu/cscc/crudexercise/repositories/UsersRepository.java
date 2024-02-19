package edu.cscc.crudexercise.repositories;

import edu.cscc.crudexercise.models.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UsersRepository {

    private static Map<Integer, User> users;
    private static AtomicInteger idGenerator;

    public UsersRepository() {
        if (users == null) {
            idGenerator = new AtomicInteger(0);
            users = new HashMap<>();
        }
    }

    public User create(User user) {
        User newUser = new User(idGenerator.addAndGet(1), user.getName(), user.getEmail());
        users.put(newUser.getId(), newUser);

        return newUser;
    }

    public User get(Integer id) {
        return users.get(id);
    }

    public List<User> all() {
        return users.values().stream().toList();
    }

    public User update(Integer id, Map<?, ?> updates) {
        User user = users.get(id);
        if (updates.containsKey("name")) {
            user.setName((String)updates.get("name"));
        }
        if (updates.containsKey("email")) {
            user.setEmail((String)updates.get("email"));
        }

        return user;
    }

    public User replace(Integer id, User user) {
        users.put(id, user);

        return users.get(id);
    }

    public Boolean delete(Integer id) {
        return users.remove(id) != null;
    }
}
