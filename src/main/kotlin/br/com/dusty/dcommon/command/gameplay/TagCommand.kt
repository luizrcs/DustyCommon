package br.com.dusty.dcommon.command.gameplay

import br.com.dusty.dcommon.command.PlayerCustomCommand
import br.com.dusty.dcommon.gamer.EnumRank
import br.com.dusty.dcommon.gamer.GamerRegistry
import br.com.dusty.dcommon.util.text.Text
import br.com.dusty.dcommon.util.text.basic
import br.com.dusty.dcommon.util.text.negative
import br.com.dusty.dcommon.util.text.positive
import org.bukkit.entity.Player

object TagCommand: PlayerCustomCommand(EnumRank.DEFAULT, "tag") {

	val USAGE = Text.NEGATIVE_PREFIX + "Uso: /tag ".basic() + "<tag>".negative()

	val TAG_DOESNT_EXIST = Text.NEGATIVE_PREFIX + "Não".positive() + " há uma ".basic() + "tag".negative() + " com o nome \"".basic() + "%s".negative() + "\"!".basic()
	val TAG_ALREADY_SET = Text.NEGATIVE_PREFIX + "Sua ".basic() + "tag".negative() + " já é ".basic() + "%s".negative() + "!".basic()
	val TAG_TOO_HIGH = Text.NEGATIVE_PREFIX + "Essa ".basic() + "tag".negative() + " é mais ".basic() + "alta".negative() + " do que você pode usar!".basic()
	val TAG_CHANGED = Text.POSITIVE_PREFIX + "Agora sua ".basic() + "tag".positive() + " é ".basic() + "%s".positive() + "!".basic()

	override fun execute(sender: Player, alias: String, args: Array<String>): Boolean {
		val gamer = GamerRegistry.gamer(sender)

		if (args.isEmpty()) sender.sendMessage(USAGE)
		else {
			val tag = EnumRank.values().firstOrNull { it.name.equals(args[0], true) }

			when {
				tag == null                  -> sender.sendMessage(TAG_DOESNT_EXIST.format(args[0]))
				tag == gamer.tag             -> sender.sendMessage(TAG_ALREADY_SET.format(tag.toString()))
				tag.isHigherThan(gamer.rank) -> sender.sendMessage(TAG_TOO_HIGH)
				else                         -> {
					gamer.tag = tag

					sender.sendMessage(TAG_CHANGED.format(tag.toString()))
				}
			}
		}

		return false
	}

	override fun tabComplete(sender: Player, alias: String, args: Array<String>) = EnumRank.values().filter {
		it.isLowerThanOrEquals(GamerRegistry.gamer(sender).rank) && it.name.startsWith(args[0], true)
	}.map { it.name.toLowerCase() }.toMutableList()
}