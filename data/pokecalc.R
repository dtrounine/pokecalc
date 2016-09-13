lf_coef <- function(level) {
    if (level <= 20) {
        0.009426125
    } else if (level <= 60) {
        0.0089219657
    } else {
        0.004459461
    }
}

lf_intercept <- function(level) {
    if (level <= 20) {
        -0.010016255
    } else if (level <= 60) {
        0.0000389325
    } else {
        0.267817222
    }
}

lf_intercept_str <- function(level) {
    lfi <- lf_intercept(level)
    if (lfi >= 0) {
        paste("+ ", lfi, sep="")
    } else {
        paste("- ", -lfi, sep="")
    }
}

level_factor <- function(level) {
    if (level <= 20) {
        0.009426125 * level - 0.010016255
    } else if (level <= 60) {
        0.0089219657 * level + 0.0000389325
    } else {
        0.004459461 * level + 0.267817222
    }
}

find_iv <- function(from_level, to_level, cp, hp, base_attack, base_defense, base_stamina) {
    count <- 0
    for (level in from_level:to_level)
    for (iv_attack in 0:15)
    for (iv_defense in 0:15)
    for (iv_stamina in 0:15) {
        count <- count + 1
        attack <- base_attack + iv_attack
        defense <- base_defense + iv_defense
        stamina <- base_stamina + iv_stamina
        f <- level_factor(level)
        cp1 <- attack * sqrt(defense) * sqrt(stamina) * f / 10.0
        hp1 <- stamina * sqrt(f)
        if (cp == floor(cp1) && hp == floor(hp1)) {
            print(paste(count, ") (level, iv_attack, iv_defense, iv_stamina) = (", 
                       level, ", ", iv_attack, ", ", iv_defense, ", ", iv_stamina, ")",
                       sep=""))
            print(paste("attack = base_attack + iv_attack = ", base_attack, " + ", iv_attack, " = ", base_attack + iv_attack, sep=""))
            print(paste("defense = base_defense + iv_defense = ", base_defense, " + ", iv_defense, " = ", base_defense + iv_defense, sep=""))
            print(paste("stamina = base_stamina + iv_stamina = ", base_stamina, " + ", iv_stamina, " = ", base_stamina + iv_stamina, sep=""))
            print(paste("level_factor(", level, ") = ", lf_coef(level), " * ", level, " ", lf_intercept_str(level), " = ", level_factor(level), sep=""))
            print(paste("CP = attack * SQRT(defense) * SQRT(stamina) * level_factor / 10", sep=""))
            print(paste("   = ", attack, " * SQRT(", defense, ") * SQRT(", stamina, ") * ", level_factor(level), " / 10" , sep=""))
            print(paste("   = ", cp1, sep=""))
            print(paste("   ~ ", floor(cp1), sep=""))
            print(paste("HP = stamina * SQRT(level_factor) = ", stamina, " * SQRT(", level_factor(level), ") = ", hp1, sep=""))
            print(paste("   ~ ", floor(hp1), sep=""))
            print("")
        }
    }
}
