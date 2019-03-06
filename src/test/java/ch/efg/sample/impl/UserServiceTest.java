package ch.efg.sample.impl;

import ch.efg.sample.api.IUserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UserServiceTest {
    private IUserService service;

    private final String GROUP_0 = "Group0";
    private final String GROUP_1 = "Group1";
    private final String GROUP_2 = "Group2";
    private final String GROUP_Z = "GroupZ";

    private User user1 = new User("1", "User1", GROUP_0);
    private User user2 = new User("2", "User2", GROUP_0);
    private User user3 = new User("3", "User3", GROUP_2);
    private User userZ = new User("Z", "UserZ", GROUP_1);

    private List userArray = Arrays.asList(user1, user2, user3);

    @Before
    public void populateServiceWithData() {
        service = new UserService<User, String>();
        service.saveAll(userArray);
    }

    @Test
    public void testFindAll() {
        Assert.assertNotNull(service.findAll());
        Assert.assertEquals(service.findAll().size(), 3);
    }

    @Test
    public void testNoDupes() {
        service.save(user2);
        Assert.assertEquals(service.findAll().size(), 3);
    }

    @Test
    public void testSaveAndFindAndDeleteOne() {
        service.save(userZ);
        Assert.assertEquals(service.findById(userZ.getId()).get(0), userZ);
        Assert.assertEquals(service.findAll().size(), 4);
        service.delete(userZ.getId());
        Assert.assertEquals(service.findAll().size(), 3);
    }

    @Test
    public void testFindGroups() {
        Assert.assertEquals(service.findAllGroupByGroupId().values().size(), 2);
        Assert.assertNull(service.findAllGroupByGroupId().get(GROUP_Z));
    }
}
