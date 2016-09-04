getPokeHtml <- function() {
    filename <- "pokemon-by-attack.html"
    
    if (file.exists(filename)) {
        readLines(filename)
    
    } else {
        address <- "http://www.pokemongodb.net/2016/07/pokemon-by-attack.html"
        con <- url(address)
        content <- readLines(con)
        close(con)
        writeLines(content, filename)
        
        content
    }
}


getPokeValues <- function() {
    library(XML)
    pokeHtml <- getPokeHtml()
    pokes <- htmlTreeParse(pokeHtml, useInternalNodes = TRUE)
    
    substr24 <- function(s) { as.integer(substr(s, 2, 4)) }
    
    rawNums <- xpathSApply(pokes, "//table//tr/td[1]", xmlValue)[2:152]
    
    nums <- sapply(rawNums, substr24, USE.NAMES = FALSE)
    names <- xpathSApply(pokes, "//table//tr/td[3]/a", xmlValue)
    stamina <- xpathSApply(pokes, "//table//tr/td[6]", xmlValue)[2:152]
    attack <- xpathSApply(pokes, "//table//tr/td[7]", xmlValue)[2:152]
    defense <- xpathSApply(pokes, "//table//tr/td[8]", xmlValue)[2:152]
    
    ord = order(nums)
    nums <- nums[ord]
    names <- names[ord]
    stamina <- stamina[ord]
    attack <- attack[ord]
    defense <- defense[ord]
    
    data.frame(Num = nums, Name = names, Stamina = stamina, Attack = attack, Defense = defense)
}

itemString <- function(val) {
    paste("        <item>", val, "</item>", sep = "")
}

getIntegerArrayLines <- function(name, items) {
    lines <-  paste('    <integer-array name="', name, '">', sep = "")
    lines <- c(lines, sapply(items, itemString))
    lines <- c(lines, '    </integer-array>')
    lines
}

generatePokeData <- function() {
    data <- getPokeValues()
    filename <- "poke_data.xml"
    
    lines <- "<resources>"
    lines <- c(lines, 
               getIntegerArrayLines("pokemon_stamina", d$Stamina),
               getIntegerArrayLines("pokemon_attack", d$Attack),
               getIntegerArrayLines("pokemon_defense", d$Defense)
               )
    
    lines <- c(lines, "</resources>")
    
    writeLines(lines, filename)
}
