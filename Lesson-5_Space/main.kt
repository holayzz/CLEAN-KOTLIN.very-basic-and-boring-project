package lesson5

data class ShipResources(
    val energy: Int,
    val oxygen: Int,
    val hull: Int,
    val scrap: Int
)

interface StationModule {
    val name: String
    fun tick(currentResources: ShipResources): ShipResources
}

class MeteorImpact : StationModule {
    override val name = "MeteorImpact"
    override fun tick(currentResources: ShipResources): ShipResources {
        println("пау метеоритом")
        return currentResources.copy(hull = currentResources.hull - 20)
    }
}

class SolarWork : StationModule {
    override val name = "solarwork"

    override fun tick(currentResources: ShipResources): ShipResources {
        println("[$name]: +15 энергии")
        return currentResources.copy(energy = currentResources.energy + 15)
    }
}

class OxygenGenerator : StationModule {
    override val name = "генератор оксигена"

    override fun tick(currentResources: ShipResources): ShipResources {
        return if (currentResources.energy >= 5) {
            println("[$name]: -5 энергии +10 оксигена")
            currentResources.copy(
                energy = currentResources.energy - 5,
                oxygen = currentResources.oxygen + 10
            )
        } else {
            println("[$name]: энергия кончилась( ")
            currentResources
        }
    }
}

fun main() {
    var ship = ShipResources(energy = 10, oxygen = 50, hull = 100, scrap = 10)
    val sun = SolarWork()
    val maow = OxygenGenerator()
    val client = MeteorImpact()

    println("--- ЗАПУСК СТАНЦИИ ---")
    println("старт: $ship")

    for (i in 1..230) {
        println("\nцикл #$i")
        ship = sun.tick(ship)
        ship = maow.tick(ship)
        ship = client.tick(ship)
        println("текущие ресурсы: $ship")
        if (ship.hull <= 0) {
            println("ggwp: учись управлять кораблем")
            break
        }
    }
}