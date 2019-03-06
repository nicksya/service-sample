package ch.efg.sample.impl;

import ch.efg.sample.api.IUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nikita Khomenko
 * @version 3/5/2019
 */
public class UserService<T extends IUser, ID extends String> implements ch.efg.sample.api.IUserService<T, ID> {
    private Map<ID, T> storage = new HashMap<ID, T>();

    @Override
    public List<T> findAll() {
        List<T> result = new ArrayList<T>();
        result.addAll(storage.values());
        return result;
    }

    @Override
    public List<T> findById(ID id) {
        List<T> result = new ArrayList<T>();
        result.add(storage.get(id));
        return result;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> var1) {
        List<S> result = new ArrayList<S>();
        var1.forEach(s -> result.add(save(s)));
        return result;
    }

    @Override
    public <S extends T> S save(S var1) {
        return (S) storage.put((ID) var1.getId(), (T) var1);
    }

    @Override
    public T delete(String var1) {
        return storage.remove(var1);
    }

    @Override
    public Map<String, List<T>> findAllGroupByGroupId() {
        Map<String, List<T>> result = new HashMap<>();
        for (T user : storage.values()) {
            String group = user.getGroupId();
            List usersByGroup = result.get(group);
            if (null == usersByGroup) {
                usersByGroup = new ArrayList();
                usersByGroup.add(user);
                result.put(group, usersByGroup);
            } else {
                usersByGroup.add(user);
            }
        }
        return result;
    }
}
