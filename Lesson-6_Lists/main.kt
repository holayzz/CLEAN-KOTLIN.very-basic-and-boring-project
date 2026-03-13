package lesson6

val inv = mutableMapOf(
    "solar work" to 0,
    "meteor kim pin tyao" to 0,
    "oxygen generator ticks" to 0
)

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
        val currcount = inv.getOrDefault("meteor kim pin tyao", 0)
        inv["meteor kim pin tyao"] = currcount + 1
        return currentResources.copy(hull = currentResources.hull - 20)
    }
}

class SolarWork : StationModule {
    override val name = "solarwork"

    override fun tick(currentResources: ShipResources): ShipResources {
        println("[$name]: +15 энергии")
        val currcount = inv.getOrDefault("solar work", 0)
        inv["solar work"] = currcount + 1
        return currentResources.copy(energy = currentResources.energy + 15)
    }
}

class OxygenGenerator : StationModule {
    override val name = "генератор оксигена"

    override fun tick(currentResources: ShipResources): ShipResources {
        val currcount = inv.getOrDefault("oxygen generator ticks", 0)
        inv["oxygen generator ticks"] = currcount + 1
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
    val modules = mutableListOf(SolarWork(), OxygenGenerator(), MeteorImpact())

    println("--- ЗАПУСК СТАНЦИИ ---")
    println("старт: $ship")
    for (i in 1..10) {
        if (ship.hull <= 0) {
            println("ggwp: прочность 0 или отрицательна")
            break
        }
        if (i in listOf(5, 10, 15, 20, 25, 30)) {
            println("upgrade: на чизбургерах сэкономили, + солнечная панель")
            modules.add(SolarWork())
        }
        modules.forEach {
                module -> ship = module.tick(ship)
        }
    }
    print("итоги: $ship, работали: $modules, подробнее: $inv")
}