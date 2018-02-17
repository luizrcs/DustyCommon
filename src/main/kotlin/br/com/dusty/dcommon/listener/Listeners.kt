package br.com.dusty.dcommon.listener

import br.com.dusty.dcommon.Main
import br.com.dusty.dcommon.listener.login.AsyncPlayerPreLoginListener
import br.com.dusty.dcommon.listener.login.PlayerJoinListener
import br.com.dusty.dcommon.listener.login.PlayerLoginListener
import br.com.dusty.dcommon.listener.login.PlayerLoginStartListener
import br.com.dusty.dcommon.listener.mechanics.*
import br.com.dusty.dcommon.listener.quit.PlayerQuitListener
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

object Listeners {

	val LISTENERS = hashSetOf<Listener>()

	fun register(listener: Listener, plugin: JavaPlugin) {
		LISTENERS.add(listener)

		Bukkit.getPluginManager().registerEvents(listener, plugin)
	}

	fun register(listener: Listener) {
		register(listener, Main.INSTANCE)
	}

	fun registerAll(listeners: List<Listener>, plugin: JavaPlugin) {
		listeners.forEach { register(it, plugin) }
	}

	fun registerAll(listeners: List<Listener>) {
		registerAll(listeners, Main.INSTANCE)
	}

	fun registerDefault() {
		registerAll(arrayListOf(
				//Login
				AsyncPlayerPreLoginListener,
				PlayerJoinListener,
				PlayerLoginListener,
				PlayerLoginStartListener,

				//Mechanics
				AsyncPlayerChatListener,
				EntityDamageByEntityListener,
				EntityDamageListener,
				InventoryClickListener,
				InventoryOpenListener,
				PlayerInteractEntityListener,
				PlayerMoveListener,
				PlayerPickupItemListener,
				SignChangeListener,
				WorldLoadListener,

				//Quit
				PlayerQuitListener))

		//Packet
//		PacketPlayOutPlayerInfoListener.registerListener()
//		PacketPlayOutNamedEntitySpawnListener.registerListener()
	}
}
