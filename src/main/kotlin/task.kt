fun main() {
    var isContinueTrip = true

    while (isContinueTrip) {
        println("Шаг 1 - Создать направление")
        val direction = Direction()
        val trainDirection = direction.createDirection()
        println("Маршрут поезда - ${trainDirection}")
        println()

        println("Шаг 2 - Продать билеты")
        val cashDesk = CashDesk()
        val ticketsSold = cashDesk.sellTickets()
        println("Продано билетов - ${ticketsSold}")
        println()

        println("Шаг 3 - Сформировать поезд")
        val trainBuilder = TrainBuilder()
        val train = trainBuilder.buildTrain(ticketsSold, trainDirection)
        train.getTrainInfo()
        println()

        println("Шаг 4 - Отправить поезд")
        val trainDispatcher = TrainDispatcher()
        trainDispatcher.sendTrain(train)
        println()

        println("Выберите дальнейшее действие: ")
        println("EXIT - выход из программы")
        println("Любой другой символ - создать новое направление")
        val input = readln().uppercase()

        if (input == "EXIT") {
            isContinueTrip = false
        }
        println("#--------------------------------------------------------------------#")
    }
}

class Direction {
    val cities = listOf("Бийск", "Барнаул", "Москва", "Санкт-Петербург", "Новосибирск",
        "Екатеринбург", "Нижний Новгород", "Казань", "Челябинск", "Омск", "Кировск", "Сочи",
        "Ростов-на-Дону", "Уфа", "Красноярск", "Пермь", "Воронеж", "Владивосток", "Калининград")

    fun createDirection(): String {
        val from = cities.random()
        var to = cities.random()
        while (to == from) {
            to = cities.random()
        }
        return "$from - $to"
    }
}

class CashDesk(private val amountFrom : Int = 5, private val amountTo : Int = 201) {
    fun sellTickets(): Int {
        return (amountFrom..amountTo).random()
    }
}

class TrainBuilder(private val amountFrom : Int = 5, private val amountTo : Int = 25) {
    fun buildTrain(passengerCount: Int, direction: String): Train {
        val train = Train(direction)
        var passengersRemaining = passengerCount

        while (passengersRemaining > 0) {
            val capacityWagon = (amountFrom..amountTo).random()
            val passengersInWagon = minOf(capacityWagon, passengersRemaining)
            train.addWagon(Wagon(capacityWagon, passengersInWagon))
            passengersRemaining -= passengersInWagon
        }

        return train
    }
}

class Wagon(val capacity: Int, val passengers: Int)

class Train(val direction: String) {
    val wagons = mutableListOf<Wagon>()

    fun addWagon(wagon: Wagon) {
        wagons.add(wagon)
    }

    fun getTrainInfo() {
        wagons.forEachIndexed { index, wagon ->
            println("Вагон номер ${index + 1} вместимостью ${wagon.capacity} пассажиров")
        }
    }
}

class TrainDispatcher {
    fun sendTrain(train: Train) {
        println("Поезд ${train.direction}, состоящий из ${train.wagons.size} вагонов отправлен")
    }
}
