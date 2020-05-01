package machine

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val machine = CoffeeMachine()
    do {
        val command = scanner.next()
        val finished = machine.input(command)
    } while (!finished)
}

class CoffeeMachine {
    enum class State(val message: String) {
        CHOOSING_ACTION("Write action (buy, fill, take, remaining, exit): "),
        CHOOSING_COFFEE("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: "),
        ADDING_WATER("Write how many ml of water do you want to add: "),
        ADDING_MILK("Write how many ml of milk do you want to add: "),
        ADDING_BEANS("Write how many grams of coffee beans do you want to add: "),
        ADDING_CUPS("Write how many disposable cups of coffee do you want to add: ")
    }

    enum class CoffeeType(val waterNeeded: Int, val milkNeeded: Int, val beansNeeded: Int, val price: Int) {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6)
    }

    private var state: State = State.CHOOSING_ACTION
        set(value) {
            print(value.message)
            field = value
        }

    private var waterLevel = 400
    private var milkLevel = 540
    private var beansLevel = 120
    private var cupsLevel = 9
    private var moneyLevel = 550

    init {
        state = State.CHOOSING_ACTION
    }

    fun input(command: String): Boolean {
        return when (state) {
            State.CHOOSING_ACTION -> chooseAction(command)
            State.CHOOSING_COFFEE -> { chooseCoffee(command); false }
            State.ADDING_WATER -> { addWater(command); false }
            State.ADDING_MILK -> { addMilk(command); false }
            State.ADDING_BEANS -> { addBeans(command); false }
            State.ADDING_CUPS -> { addCups(command); false }
        }
    }

    private fun chooseAction(command: String): Boolean {
        return when (command) {
            "buy" -> {
                println()
                state = State.CHOOSING_COFFEE
                false
            }
            "fill" -> {
                println()
                state = State.ADDING_WATER
                false
            }
            "take" -> {
                println()
                take()
                state = State.CHOOSING_ACTION
                false
            }
            "remaining" -> {
                println()
                remaining()
                state = State.CHOOSING_ACTION
                false
            }
            "exit" -> true
            else -> {
                state = State.CHOOSING_ACTION
                false
            }
        }
    }

    private fun chooseCoffee(command: String) {
        when (command) {
            "1" -> {
                makeCoffee(CoffeeType.ESPRESSO)
                state = State.CHOOSING_ACTION
            }
            "2" -> {
                makeCoffee(CoffeeType.LATTE)
                state = State.CHOOSING_ACTION
            }
            "3" -> {
                makeCoffee(CoffeeType.CAPPUCCINO)
                state = State.CHOOSING_ACTION
            }
            "back" -> {
                state = State.CHOOSING_ACTION
            }
        }
    }

    private fun makeCoffee(coffeeType: CoffeeType) {
        when {
            waterLevel < coffeeType.waterNeeded ->
                println("Sorry, not enough water!")
            milkLevel < coffeeType.milkNeeded ->
                println("Sorry, not enough milk!")
            beansLevel < coffeeType.beansNeeded ->
                println("Sorry, not enough coffee beans!")
            cupsLevel < 1 ->
                println("Sorry, not enough disposable cups!")
            else -> {
                println("I have enough resources, making you a coffee!")
                waterLevel -= coffeeType.waterNeeded
                milkLevel -= coffeeType.milkNeeded
                beansLevel -= coffeeType.beansNeeded
                cupsLevel--
                moneyLevel += coffeeType.price
            }
        }
        println()
    }

    private fun remaining() {
        println("The coffee machine has:")
        println("$waterLevel of water")
        println("$milkLevel of milk")
        println("$beansLevel of coffee beans")
        println("$cupsLevel of disposable cups")
        println("\$$moneyLevel of money")
        println()
    }

    private fun take() {
        println("I gave you \$$moneyLevel")
        println()
        moneyLevel = 0
    }

    private fun addWater(command: String) {
        waterLevel += command.toInt()
        state = State.ADDING_MILK
    }

    private fun addMilk(command: String) {
        milkLevel += command.toInt()
        state = State.ADDING_BEANS
    }

    private fun addBeans(command: String) {
        beansLevel += command.toInt()
        state = State.ADDING_CUPS
    }

    private fun addCups(command: String) {
        cupsLevel += command.toInt()
        println()
        state = State.CHOOSING_ACTION
    }
}
