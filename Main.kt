import java.util.*
val scanner = Scanner(System.`in`)

enum class ParkingLot {
    NOTCREATED,
    CREATED;
}

class Park {
    var parkState = ParkingLot.NOTCREATED
    var parking = Array<String>(0) { "" }
    var parkSlotsNumber = 0
    var command = ""

    fun waitForInput() {
        command = scanner.next()
    }

    fun operate() {
        waitForInput()
        if (command != "exit") {
            when (command) {
                "create" -> create()
                "park" -> park()
                "leave" -> leave()
                "status" -> status()
                else -> operate()
            }
        }
    }

    fun create() {
        parkSlotsNumber = scanner.nextInt()
        parking = Array<String>(parkSlotsNumber) { "" }
        parkState = ParkingLot.CREATED
        println("Created a parking lot with $parkSlotsNumber spots.")
        operate()
    }

    fun park() {
        if (parkState == ParkingLot.NOTCREATED) {
            val mess = scanner.nextLine()
            println("Sorry, a parking lot has not been created.")
        }
        if (parkState != ParkingLot.NOTCREATED) {
            var counter = 0
            var carsCounter = 0
            val serialNumber = scanner.next().toString()
            val color = scanner.next().toLowerCase().capitalize()
            val id = "$serialNumber $color"
            loopToPark@ for (i in parking.indices) {
                if (parking[i].isEmpty()) {
                    parking[i] = id
                    counter = i + 1
                    println("$color car parked in spot $counter.")
                    break@loopToPark
                } else if (parking[i].isNotEmpty()) {
                    carsCounter++
                    if (carsCounter == parkSlotsNumber) println("Sorry, the parking lot is full.")
                }
            }
        }
        operate()
    }

    fun status() {
        if (parkState == ParkingLot.NOTCREATED) println("Sorry, a parking lot has not been created.")
        if (parkState != ParkingLot.NOTCREATED) {
            var emptimessCounter = 0
            for (i in parking.indices) {
                if (parking[i].isNotEmpty()) {
                    val carIsHere = parking[i]
                    val carSlot = i + 1
                    println("$carSlot $carIsHere")
                } else if (parking[i].isEmpty()) {
                    emptimessCounter++
                    if (emptimessCounter == parkSlotsNumber) println("Parking lot is empty.")
                }
            }
        }
        operate()
    }

    fun leave() {
        if (parkState == ParkingLot.NOTCREATED) println("Sorry, a parking lot has not been created.")
        if (parkState != ParkingLot.NOTCREATED) {
            val carSLot = scanner.nextInt()
            val indx = carSLot - 1
            if (parking[indx].isNotEmpty()) {
                println("Spot $carSLot is free.")
                parking[indx] = ""
            } else println("There is no car in spot $carSLot.")
        }
        operate()
    }

    fun wrongCommand() {
        println("Wrong command. Useful command are : create , park , leave , exit .")
        operate()
    }
}

fun main () {
    val myParking = Park()
    myParking.operate()
}
