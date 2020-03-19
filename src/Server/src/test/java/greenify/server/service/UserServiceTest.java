package greenify.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import greenify.common.ApplicationException;
import greenify.common.UserDto;
import greenify.server.AllAchievements;
import greenify.server.data.model.User;
import greenify.server.data.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    @TestConfiguration
    static class UserServiceConfiguration {
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CalculatorService calculatorService;

    @MockBean
    private AchievementService achievementService;

    /**
     * setUp method for test.
     */
    @Before
    public void setUp() {
        User alex = new User(1L, "alex", "password");
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        User lola = new User(2L, "lola", "password");
        when(userRepository.findByName(lola.getName()))
                .thenReturn(lola);
    }

    @Test
    public void validLoginTest() {
        String name = "alex";
        String password = "password";
        UserDto found = userService.loginUser(name, password);
        assertEquals(found.getName(), name);
    }

    @Test
    public void loginExceptionTest() {
        assertThrows(ApplicationException.class, () -> userService.loginUser("alex", "greenify"));
    }

    @Test
    public void userRegisterTest() {
        User user = new User(1L, "name", "password");
        UserDto registered = userService.registerUser(user.getName(), user.getPassword());
        assertEquals(registered.getName(), "name");
    }

    @Test
    public void registerExceptionTest() {
        assertThrows(ApplicationException.class, () ->
                userService.registerUser("alex", "password"));
    }

    @Test
    public void setInputTest() {
        User alex = new User(1L, "alex", "password");
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        userService.setInput("alex", "input_footprint_shopping_food_dairy_default", "6.5");
        assertEquals("6.5", alex.getFootPrintInputs()
                .get("input_footprint_shopping_food_dairy_default"));
    }

    @Test
    public void setExtraInputTest() {
        User alex = new User(1L, "alex", "password");
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        userService.setExtraInput("alex", "solar_panels", "5");
        assertEquals("5", alex.getExtraInputs()
                .get("solar_panels"));
    }

    @Test
    public void setInputNullTest() {
        assertThrows(ApplicationException.class, () -> userService.setInput(null, "hello", "5.5"));
    }

    @Test
    public void setExtraInputNullTest() {
        assertThrows(ApplicationException.class, () -> userService
                .setExtraInput(null, "hello", "6"));
    }

    @Test
    public void setInputApplicationTestItem() {
        assertThrows(ApplicationException.class, () -> userService
                .setInput("alex", "hello", "3.5"));
    }

    @Test
    public void setInputApplicationTestValue() {
        assertThrows(ApplicationException.class, () ->
                userService.setInput("alex", "input_footprint_transportation_num_vehicles", "5.5"));
    }

    @Test
    public void setInputExceptionTest() {
        assertThrows(ApplicationException.class, () ->
                userService.setInput("alex", "hi", "5.5"));
    }

    @Test
    public void setInputFootprintTest() {
        User alex = new User(1L, "alex", "password");
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        when(calculatorService.calculateFootprint(alex))
                .thenReturn(15f);
        userService.setInput("alex", "input_footprint_shopping_food_dairy_default", "6.5");
        Assert.assertTrue(alex.getFootPrintInputs()
                .get("input_footprint_shopping_food_dairy_default").equals("6.5"));
    }

    @Test
    public void getInputTest() {
        User alex = new User(1L, "alex", "password");
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        userService.setInput("alex",
                "input_footprint_shopping_food_dairy_default", "6.5");
        assertEquals("6.5", userService
                .getInput("alex", "input_footprint_shopping_food_dairy_default"));
    }

    @Test
    public void getInputMapTest() {
        Map<String, String> map = new HashMap<>();
        User alex = new User(1L, "alex", "password");
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        alex.setFootPrintInputs(map);
        assertEquals(map, userService
                .getInputMap("alex"));
    }

    @Test
    public void getExtraInputMapTest() {
        Map<String, String> map = new HashMap<>();
        User alex = new User(1L, "alex", "password");
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        alex.setExtraInputs(map);
        assertEquals(map, userService
                .getExtraInputMap("alex"));
    }

    @Test
    public void getInputExceptionTest() {
        assertThrows(ApplicationException.class, () -> userService.getInput("alex", "hello"));
    }

    @Test
    public void getFootprintTest() {
        User alex = new User(1L, "alex", "password");
        alex.setFootPrint(15F);
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        Assertions.assertEquals(15F, userService.getFootprint("alex"));
    }

    @Test
    public void getFirstFootprintTest() {
        User alex = new User(1L, "alex", "password");
        alex.setFirstFootprint(15F);
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        Assertions.assertEquals(15F, userService.getFirstFootprint("alex"));
    }

    @Test
    public void saveFootprintTest() {
        User alex = new User(1L, "alex", "password");
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        when(calculatorService.calculateFootprint(alex))
                .thenReturn(15f);
        userService.saveFootprint("alex");
        Assertions.assertEquals(15f, userService.saveFootprint("alex"));
    }

    @Test
    public void saveFirstFootprintTest() {
        User lola = new User(1L, "lola", "password");
        when(userRepository.findByName(lola.getName()))
                .thenReturn(lola);
        when(calculatorService.calculateFootprint(lola))
                .thenReturn(0f);
        userService.saveFirstFootprint("lola");
        Assertions.assertEquals(0f, userService.saveFirstFootprint("lola"));
    }

    @Test
    public void getFriendsTest() {
        User alex = new User(1L, "alex", "password");
        User lola = new User(2L, "lola", "pass");
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        when(userRepository.findByName(lola.getName()))
                .thenReturn(lola);
        alex.addFriend(lola);
        List<String> friendList = new ArrayList<>();
        friendList.add("lola");
        assertEquals(friendList, userService.getFriends("alex"));
    }

    @Test
    public void getAllUserTest() {
        User alex = new User(1L, "alex", "password");
        User lola = new User(2L, "lola", "pass");
        Iterable<User> users = new ArrayList<>();
        ((ArrayList<User>) users).add(alex);
        ((ArrayList<User>) users).add(lola);
        when(userRepository.findAll())
                .thenReturn(users);
        List<String> userList = new ArrayList<>();
        userList.add("alex");
        userList.add("lola");
        assertEquals(userList, userService.getAllUsers());
    }

    @Test
    public void invalidLoginTest() {
        assertThrows(ApplicationException.class, () -> userService.loginUser(null, null));
    }

    @Test
    public void addFriendTest() {
        User alex = userRepository.findByName("alex");
        User lola = userRepository.findByName("lola");
        assertEquals(lola.getFriends(), alex.getFriends());
        userService.addFriend("alex", "lola");
        ArrayList<User> test = new ArrayList<User>();
        test.add(lola);
        assertEquals(alex.getFriends(), test);
    }

    @Test
    public void removeFriendTest() {
        User alex = userRepository.findByName("alex");
        User lola = userRepository.findByName("lola");
        assertEquals(lola.getFriends(), alex.getFriends());
        userService.addFriend("alex", "lola");
        ArrayList<User> test = new ArrayList<User>();
        test.add(lola);
        assertEquals(alex.getFriends(), test);
        userService.removeFriend("alex", "lola");
        assertEquals(lola.getFriends(), alex.getFriends());
    }

    @Test
    public void removeFriendNullTest() {
        assertThrows(ApplicationException.class, () -> userService.removeFriend("alex", null));
    }

    @Test
    public void setAchievementNullUserTest() {
        assertThrows(ApplicationException.class, () -> userService
                .setAchievement("ceren", "Starting off", true));
    }

    @Test
    public void invalidAchievementTest() {
        assertThrows(ApplicationException.class, () -> userService
                .setAchievement("alex", "greenify", true));
    }

    @Test
    public void addFriendNullFriendTest() {
        assertThrows(ApplicationException.class, () -> userService.addFriend("alex", null));
    }

    @Test
    public void setAchievementTest() {
        User alex = new User(1L, "alex", "password");
        when(userRepository.findByName(alex.getName()))
                .thenReturn(alex);
        userService.setAchievement("alex",
                "Starting off", true);
        assertEquals(true, userService
                .getAchievement("alex", "Starting off"));
    }

    @Test
    public void getAchievementTest() {
        assertThrows(ApplicationException.class, () -> userService.getAchievement("alex", "hello"));
        assertEquals(false, userService.getAchievement("alex", "Starting off"));
    }

    @Test
    public void getAchievementsTest() {
        assertEquals(AllAchievements.getDefaults(), userService.getAchievements("alex"));
    }

    @Test
    public void deleteAccountException() {
        assertThrows(ApplicationException.class, () -> userService.deleteAccount("merel"));
    }

    @Test
    public void deleteAccount() {
        User alex = new User(1L, "alex", "password");
        doNothing().when(userRepository).delete(alex);
        userService.deleteAccount("alex");
        verify(userRepository, times(1)).delete(alex);
    }
}