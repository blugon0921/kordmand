package kr.blugon.kordmand

import dev.kord.common.entity.ChannelType
import dev.kord.common.entity.Choice
import dev.kord.common.entity.optional.Optional

abstract class CommandOption {
    abstract val name: String
    abstract val description: String
    abstract val type: OptionType
    open var required: Boolean = false
}

data class MentionableOption( //Mentionable
    override var name: String,
    override var description: String,
    var apply: MentionableOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.MENTIONABLE
    init { apply(this) }
}

data class ChannelOption( //Channel
    override var name: String,
    override var description: String,
    var apply: ChannelOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.CHANNEL
    val channelTypes = ArrayList<ChannelType>()
    init { apply(this) }
}

data class UserOption( //User
    override var name: String,
    override var description: String,
    var apply: UserOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.USER
    init { apply(this) }
}

data class RoleOption( //Role
    override var name: String,
    override var description: String,
    var apply: RoleOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.ROLE
    init { apply(this) }
}

data class AttachmentOption( //Attachment
    override var name: String,
    override var description: String,
    var apply: AttachmentOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.ATTACHMENT
    init { apply(this) }
}

data class NumberOption( //Number
    override var name: String,
    override var description: String,
    var minValue: Double? = null,
    var maxValue: Double? = null,
    var apply: NumberOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.NUMBER
    val choices = ArrayList<Choice.NumberChoice>()
    init { apply(this) }
}

data class StringOption( //String
    override var name: String,
    override var description: String,
    var minLength: Int? = null,
    var maxLength: Int? = null,
    var autoComplete: Boolean = false,
    var apply: StringOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.STRING
    val choices = ArrayList<Choice.StringChoice>()
    init { apply(this) }

    fun choice(name: String, value: String) {
        choices.add(Choice.StringChoice(name, Optional.Missing(), value))
    }
}

data class IntegerOption( //Integer
    override var name: String,
    override var description: String,
    var minValue: Long? = null,
    var maxValue: Long? = null,
    var apply: IntegerOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.INTEGER
    val choices = ArrayList<Choice.IntegerChoice>()
    init { apply(this) }

    fun choice(name: String, value: Long) {
        choices.add(Choice.IntegerChoice(name, Optional.Missing(), value))
    }
}

data class BooleanOption( //Boolean
    override var name: String,
    override var description: String,
    var apply: BooleanOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.BOOLEAN
    init { apply(this) }
}

data class SubCommandOption( //Boolean
    override var name: String,
    override var description: String,
    var apply: SubCommandOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.SUB_COMMAND
    var options: MutableList<CommandOption>? = null
    init { apply(this) }
}

data class GroupOption( //Boolean
    override var name: String,
    override var description: String,
    var apply: GroupOption.() -> Unit = {},
): CommandOption() {
    override val type: OptionType = OptionType.GROUP
    var subCommands: MutableList<SubCommandOption>? = null
    init { apply(this) }
}

enum class OptionType {
    MENTIONABLE,
    CHANNEL,
    USER,
    ROLE,
    ATTACHMENT,
    NUMBER,
    STRING,
    INTEGER,
    BOOLEAN,
    SUB_COMMAND,
    GROUP,
}