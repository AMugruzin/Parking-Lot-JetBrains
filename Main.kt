import java.util.*
val scanner = Scanner(System.`in`)
data class ParkingLotSpot(var plate: String, var color: String, var spot: Int, var occupied: Boolean)
enum class ParkingStatus {
    NOTCREATED, CREATED
}
class Parking {
    var parkState = ParkingStatus.NOTCREATED
    var parking = Array<ParkingLotSpot>(0) { ParkingLotSpot("", "", 0, false) }
    var parkSlotsNumber = 0
    var command = "placeholder"
    val createParkFirst = "Sorry, a parking lot has not been created."
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
                "reg_by_color" -> regByColor()
                "spot_by_color" -> spotByColor()
                "spot_by_reg" -> spotByReg()
                else -> operate()
            }
        }
    }
    fun create() {
        parkSlotsNumber = scanner.nextInt()
        parking = Array<ParkingLotSpot>(parkSlotsNumber) { ParkingLotSpot("", "", 0, false) }
        parkState = ParkingStatus.CREATED
        println("Created a parking lot with $parkSlotsNumber spots.")
        operate()
    }
    fun park() {
        if (parkState == ParkingStatus.NOTCREATED) {
            val mess = scanner.nextLine()
            println(createParkFirst)
        }
        if (parkState != ParkingStatus.NOTCREATED) {
            var counter = 0
            var carsCounter = 0
            val serialNumber = scanner.next().toString().toUpperCase()
            val color = scanner.next().toLowerCase().capitalize()
            val id = "$serialNumber $color"
            loopToPark@ for (i in parking.indices) {
                if (!parking[i].occupied) {
                    counter = i + 1
                    val carInSlot: ParkingLotSpot = ParkingLotSpot(serialNumber, color, counter, true)
                    parking[i] = carInSlot
                    println("$color car parked in spot $counter.")
                    break@loopToPark
                } else if (parking[i].occupied) {
                    carsCounter++
                    if (carsCounter == parkSlotsNumber) println("Sorry, the parking lot is full.")
                }
            }
        }
        operate()
    }
    fun status() {
        if (parkState != ParkingStatus.NOTCREATED) {
            var emptimessCounter = 0
            for (i in parking.indices) {
                if (parking[i].occupied) {
                    val carToPrint = "${parking[i].spot} ${parking[i].plate} ${parking[i].color}"
                    println(carToPrint)
                } else if (!parking[i].occupied) {
                    emptimessCounter++
                    if (emptimessCounter == parkSlotsNumber) println("Parking lot is empty.")
                }
            }
        } else println(createParkFirst)
        operate()
    }
    fun regByColor() {
       if (parkState == ParkingStatus.NOTCREATED) {
           val mess = scanner.nextLine()
           println(createParkFirst)
       }
       if (parkState != ParkingStatus.NOTCREATED) {
           val colorToSearch = scanner.next().toLowerCase().capitalize()
           var toShow = ""
           for (i in parking.indices) {
               if (parking[i].color.contains(colorToSearch)) {
                   toShow += "${parking[i].plate}, "
               }
           }
           if (toShow.isEmpty()) println("No cars with color ${colorToSearch.toUpperCase()} were found.")
           else println(toShow.trim().removeSuffix(","))
       }
    operate()
   }
    fun spotByColor() {
        if (parkState == ParkingStatus.NOTCREATED) {
            val mess = scanner.nextLine()
            println(createParkFirst)
        }
        if (parkState != ParkingStatus.NOTCREATED) {
            val colorToSearch = scanner.next().toLowerCase().capitalize()
            var toShow = ""
            for (i in parking.indices) {
                if (parking[i].color.contains(colorToSearch)) {
                    toShow += "${parking[i].spot}, "
                }
            }
            if (toShow.isEmpty()) println("No cars with color ${colorToSearch.toUpperCase()} were found.")
            else println(toShow.trim().removeSuffix(","))
        }
        operate()
    }
    fun spotByReg() {
        if (parkState == ParkingStatus.NOTCREATED) {
            val mess = scanner.nextLine()
            println(createParkFirst)
        }
        if (parkState != ParkingStatus.NOTCREATED) {
            val plateToSearch = scanner.next().toUpperCase()
            var toShow = ""
            for (i in parking.indices) {
                if (parking[i].plate.contains(plateToSearch)) {
                    toShow += "${parking[i].spot}, "
                }
            }
            if (toShow.isEmpty()) println("No cars with registration number $plateToSearch were found.")
            else println(toShow.trim().removeSuffix(","))
        }
        operate()
    }
    fun leave() {
        if (parkState != ParkingStatus.NOTCREATED) {
            val carSLot = scanner.nextInt()
            val indx = carSLot - 1
            if (parking[indx].occupied) {
                println("Spot $carSLot is free.")
                parking[indx] = ParkingLotSpot("", "", 0, false)
            } else println("There is no car in spot $carSLot.")
        } else println(createParkFirst)
        operate()
    }
}

fun main () {
    val myParking = Parking()
    myParking.operate()
}
