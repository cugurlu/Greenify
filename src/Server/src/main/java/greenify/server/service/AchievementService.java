package greenify.server.service;

import greenify.server.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {
    @Autowired
    UserService userService;

    /**
     * This method updates all achievements of a user.
     * @param user the user for whom the achievements change
     */
    public void updateAchievements(User user) {
        achieveGettingStarted(user);
        achieveSocialButterfly(user);
        achieveGreenSaver(user);
        achieveAnimalFriend(user);
        achieveTomDumoulin(user);
        achieveLetItShine(user);
    }

    /**
     * This method makes sure the user gets an achievement
     * upon calculating their footprint for the first time.
     * @param user user for whom achiev1 changes
     */
    public void achieveGettingStarted(User user) {
        userService.setAchievement(user.getName(), "Starting off", true);
    }

    /**
     * This method changes achiev2 when this is the case.
     * @param user user for whom achiev2 changes
     */
    public void achieveSocialButterfly(User user) {
        if (user.getFriends().size() >= 3) {
            userService.setAchievement(user.getName(), "Social butterfly", true);
        } else {
            userService.setAchievement(user.getName(), "Social butterfly", false);
        }
    }

    /**
     * This method changes achiev3 when this is the case.
     * @param user user for whom achiev3 changes
     */
    public void achieveGreenSaver(User user) {
        if (20 > user.getFootPrint()) {
            userService.setAchievement(user.getName(), "Green saver", true);
        } else {
            userService.setAchievement(user.getName(), "Green saver", false);
        }
    }

    /**
     * This method changes achiev4 when this is the case.
     * @param user user for whom achiev4 changes
     */
    public void achieveAnimalFriend(User user) {
        int vegan = Integer.parseInt(user.getExtraInputs().get("vegan"));
        if (vegan > 10) {
            userService.setAchievement(user.getName(), "Animal friend", true);
        } else {
            userService.setAchievement(user.getName(), "Animal friend", false);
        }
    }

    /**
     * This method changes achiev5 when this is the case.
     * @param user user for whom achiev5 changes
     */
    public void achieveTomDumoulin(User user) {
        int bike = Integer.parseInt(user.getExtraInputs().get("bike"));
        if (bike > 15) {
            userService.setAchievement(user.getName(), "Tom Dumoulin", true);
        } else {
            userService.setAchievement(user.getName(), "Tom Dumoulin", false);
        }
    }

    /**
     * This method changes achiev6 when this is the case.
     * @param user user for whom achiev6 changes
     */
    public void achieveLetItShine(User user) {
        int solarPanels = Integer.parseInt(user.getExtraInputs().get("solar_panels"));
        if (solarPanels >= 2) {
            userService.setAchievement(user.getName(), "Let it shine", true);
        } else {
            userService.setAchievement(user.getName(), "Let it shine", false);
        }
    }

}
