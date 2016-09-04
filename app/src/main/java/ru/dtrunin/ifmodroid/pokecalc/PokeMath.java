package ru.dtrunin.ifmodroid.pokecalc;

/**
 * Created by dmitry.trunin on 04.09.2016.
 */
public final class PokeMath {

    /**
     * http://pokemongo.gamepress.gg/power-up-costs
     */
    public static int getPowerUpStardustCost(int fromLevel) {
        checkLevelBounds(fromLevel);
        if (fromLevel <= 5) {
            return 200;
        }
        if (fromLevel <= 9) {
            return 400;
        }
        if (fromLevel <= 13) {
            return 600;
        }
        if (fromLevel <= 17) {
            return 800;
        }
        if (fromLevel <= 21) {
            return 1000;
        }
        if (fromLevel <= 25) {
            return 1300;
        }
        if (fromLevel <= 29) {
            return 1600;
        }
        if (fromLevel <= 33) {
            return 1900;
        }
        if (fromLevel <= 37) {
            return 2200;
        }
        if (fromLevel <= 41) {
            return 2500;
        }
        if (fromLevel <= 45) {
            return 3000;
        }
        if (fromLevel <= 49) {
            return 3500;
        }
        if (fromLevel <= 53) {
            return 4000;
        }
        if (fromLevel <= 57) {
            return 4500;
        }
        if (fromLevel <= 61) {
            return 5000;
        }
        if (fromLevel <= 65) {
            return 6000;
        }
        if (fromLevel <= 69) {
            return 7000;
        }
        if (fromLevel <= 73) {
            return 8000;
        }
        if (fromLevel <= 77) {
            return 9000;
        }
        return 10000;
    }

    public static boolean isAllowedPowerUpStardustCost(int powerUpStardustCost) {
        switch (powerUpStardustCost) {
            case 200:
            case 400:
            case 600:
            case 800:
            case 1000:
            case 1300:
            case 1600:
            case 1900:
            case 2200:
            case 2500:
            case 3000:
            case 3500:
            case 4000:
            case 4500:
            case 5000:
            case 6000:
            case 7000:
            case 8000:
            case 9000:
            case 10000:
                return true;
            default:
                return false;
        }
    }

    public static int getMinLevelForPowerUpStardustCost(int powerUpStardustCost) {
        switch (powerUpStardustCost) {
            case 200:   return 2;
            case 400:   return 6;
            case 600:   return 10;
            case 800:   return 14;
            case 1000:  return 18;
            case 1300:  return 22;
            case 1600:  return 26;
            case 1900:  return 30;
            case 2200:  return 34;
            case 2500:  return 38;
            case 3000:  return 42;
            case 3500:  return 46;
            case 4000:  return 50;
            case 4500:  return 54;
            case 5000:  return 58;
            case 6000:  return 62;
            case 7000:  return 66;
            case 8000:  return 70;
            case 9000:  return 74;
            case 10000: return 78;
            default:
                throw new IllegalArgumentException("Not allowed power up stardust cost value: "
                        + powerUpStardustCost);
        }
    }

    public static int getMaxLevelForPowerUpStardustCost(int powerUpStardustCost) {
        if (powerUpStardustCost == 10000) {
            return 79;
        } else {
            return getMinLevelForPowerUpStardustCost(powerUpStardustCost) + 3;
        }
    }

    public static int getCp(int level, int stamina, int attack, int defense) {
        final double cpm = getCpMultiplier(level);
        final double cp = attack * Math.sqrt(defense) * Math.sqrt(stamina) * cpm * cpm / 10.0;
        return (int) Math.round(Math.floor(cp));
    }

    public static int getHp(int level, int stamina) {
        final double cp = getCpMultiplier(level);
        final double hp = stamina * cp;
        return (int) Math.round(Math.floor(hp));
    }

    private static final double [] cp_2_80 = {
            0.094, 0.16639787, 0.21573247, 0.25572005, 0.29024988,
            0.3210876 , 0.34921268, 0.37523559, 0.39956728, 0.42250001,
            0.44310755, 0.46279839, 0.48168495, 0.49985844, 0.51739395,
            0.53435433, 0.55079269, 0.56675452, 0.58227891, 0.59740001,
            0.61215729, 0.62656713, 0.64065295, 0.65443563, 0.667934,
            0.68116492, 0.69414365, 0.70688421, 0.71939909, 0.7317,
            0.73776948, 0.74378943, 0.74976104, 0.75568551, 0.76156384,
            0.76739717, 0.7731865, 0.77893275, 0.78463697, 0.79030001
    };

    private static final double cp_correction = 0.009426125469;

    private static double getCpMultiplier(int level) {
        checkLevelBounds(level);
        int fracLevel = level / 2;
        return cp_2_80[fracLevel - 1] + (2 * fracLevel == level ? 0 : cp_correction);



//        if (level <= 20) {
//            return 0.01885225 * 0.5 * level - 0.01001625;
//        } else if (level <= 40) {
//            return 0.01783805 * 0.5 * (level - 20) + 0.17850625;
//        } else if (level <= 60) {
//            return 0.01784981 * 0.5 * (level - 40) + 0.35688675;
//        } else {
//            return 0.00891892 * 0.5 * (level - 60) + 0.53538485;
//        }
    }

    private static void checkLevelBounds(int level) {
        if (level < 2 || level > 80) {
            throw new IllegalArgumentException("Allowed values of level are "
                    + "from 2 to 79: " + level);
        }
    }

    private PokeMath() {}
}
