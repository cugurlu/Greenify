package greenify.server.data.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import greenify.common.ApplicationException;
import greenify.server.AllAchievements;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserTest {

    @Test
    public void setAndGetTest() {
        User testUser = new User();
        testUser.setId(1L);
        testUser.setName("greenify");
        testUser.setPassword("password");
        User user = new User(1L, "greenify", "password");
        assertEquals(1L, (long) user.getId());
        assertEquals(user.getName(), "greenify");
        assertEquals(user.getPassword(), "password");
        assertEquals(user, testUser);
    }

    @Test
    public void toStringTest() {
        User user = new User(1L, "greenify", "password");
        assertEquals("User(id=1, name=greenify, password=password)", user.toString());
    }

    @Test
    public void equalsTest() {
        User first = new User(2L, "greenify", "12345");
        User second = new User(2L, "greenify", "12345");
        assertEquals(first.getId(), second.getId());
        assertEquals(first.getName(), second.getName());
        assertEquals(first.getPassword(), second.getPassword());
        assertEquals(first, second);
    }

    @Test
    public void equalsDifferentId() {
        User first = new User(1L, "greenify", "password");
        User second = new User(2L, "greenify", "password");
        assertNotEquals(first, second);
    }

    @Test
    public void equalsDifferentName() {
        User first = new User(5L, "greenify", "password");
        User second = new User(5L, "hello", "password");
        assertNotEquals(first, second);
    }

    @Test
    public void equalsDifferentPassword() {
        User first = new User(1L, "greenify", "password");
        User second = new User(1L, "greenify", "hi");
        assertNotEquals(first, second);
    }

    @Test
    public void notEqualsTest() {
        User first = new User(1L, "greenify", "password");
        User second = new User(2L, "greenify", "password");
        assertNotEquals(first, second);
    }

    @Test
    public void instanceOfTest() {
        User first = new User();
        Object second = new Object();
        assertNotEquals(first, second);
    }

    @Test
    public void hashCodeTest() {
        User first = new User(1L, "greenify", "password");
        User second = new User(1L, "greenify", "password");
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void getFriendEmpty() {
        User first = new User(1L, "greenify", "password");
        User second = new User(1L, "merel", "password");
        assertEquals(first.getFriends(), second.getFriends());
        assertEquals(first.getFriends(), new ArrayList<User>());
    }

    @Test
    public void addFriendTest() {
        User first = new User(1L, "greenify", "password");
        User second = new User(1L, "merel", "password");
        assertEquals(first.getFriends(), second.getFriends());
        first.addFriend(second);
        ArrayList<User> test = new ArrayList<User>();
        test.add(second);
        assertEquals(first.getFriends(), test);
    }

    @Test
    public void addYourselfTest() {
        User test = new User(1L, "greenify", "password");
        assertEquals(test.getFriends(), new ArrayList<User>());
        assertThrows(ApplicationException.class, () -> {
            test.addFriend(test);
        });
    }

    @Test
    public void addTwiceTest() {
        User test = new User(1L, "greenify", "password");
        List<User> friendList = new ArrayList<>();
        friendList.add(test);
        User user = new User(1L, "green", "pass");
        user.setFriends(friendList);
        assertThrows(ApplicationException.class, () -> {
            user.addFriend(test);
        });
    }

    @Test
    public void removeFriendValidTest() {
        User test = new User(1L, "greenify", "password");
        List<User> friendList = new ArrayList<>();
        friendList.add(test);
        User user = new User(1L, "green", "pass");
        user.setFriends(friendList);
        assertEquals(user.getFriends(), friendList);
        user.removeFriend(test);
        assertEquals(user.getFriends(), new ArrayList<User>());
    }

    @Test
    public void removeFriendInvalidTest() {
        User user = new User(1L, "greenify", "password");
        User test = new User(2L, "user", "pass");
        assertThrows(ApplicationException.class, () -> {
            user.removeFriend(test);
        });
    }

    @Test
    public void setFriendTest() {
        List<User> friends = new ArrayList<User>();
        User first = new User(1L, "greenify", "password");
        User second = new User(1L, "merel", "password");
        friends.add(second);
        first.setFriends(friends);
        assertEquals(friends, first.getFriends());
    }

    @Test
    public void getAchievementsTest() {
        User user = new User(1L, "greenify", "password");
        assertEquals(user.getAchievements(), AllAchievements.getDefaults());
    }

    @Test
    public void setAchievementsTest() {
        User user = new User(1L, "greenify", "password");
        user.setAchievements(null);
        assertNull(user.getAchievements());
    }
}
