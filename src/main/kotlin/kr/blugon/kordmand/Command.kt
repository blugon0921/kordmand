package kr.blugon.kordmand

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.Guild
import dev.kord.core.event.interaction.AutoCompleteInteractionCreateEvent
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.interaction.*

interface Command {
    val command: String
    val description: String
    val options: List<CommandOption>?

    fun onRun(bot: Kord, run: GuildChatInputCommandInteractionCreateEvent.()->Unit) {
        bot.on<GuildChatInputCommandInteractionCreateEvent> {
            if(interaction.command.rootName != command) return@on
            run(this)
        }
    }

    fun onAutoComplete(bot: Kord, run: AutoCompleteInteractionCreateEvent.()->Unit) {
        bot.on<AutoCompleteInteractionCreateEvent> {
            if(interaction.command.rootName != command) return@on
            run(this)
        }
    }

    companion object {
        suspend fun Kord.registerGuildCommand(command: Command, guild: Guild) = this.registerGuildCommand(command, guild.id)
        suspend fun Kord.registerGuildCommand(command: Command, guildId: Snowflake) = command.registerWithGuild(this, guildId)

        suspend fun Kord.registerGlobalCommand(command: Command) = command.registerWithGlobal(this)
    }

    suspend fun registerWithGuild(bot: Kord, guildId: Snowflake) {
        bot.createGuildChatInputCommand(guildId, command, description) {
            this.setOptions()
        }
    }

    suspend fun registerWithGlobal(bot: Kord) {
        bot.createGlobalChatInputCommand(command, description) {
            this.setOptions()
        }
    }

    private fun ChatInputCreateBuilder.setOptions() {
        this@Command.options?.forEach { option ->
            when(option) {
                //Mentionable
                is MentionableOption -> this.mentionable(option.name, option.description) {
                    this.required = option.required
                }

                //Channel
                is ChannelOption -> this.channel(option.name, option.description) {
                    this.required = option.required
                    this.channelTypes = option.channelTypes
                }

                //User
                is UserOption -> this.user(option.name, option.description) {
                    this.required = option.required
                }

                //Role
                is RoleOption -> this.user(option.name, option.description) {
                    this.required = option.required
                }

                //Attachment
                is AttachmentOption -> this.attachment(option.name, option.description) {
                    this.required = option.required
                }

                //Number
                is NumberOption -> this.number(option.name, option.description) {
                    this.required = option.required
                    this.minValue = option.minValue
                    this.maxValue = option.maxValue
                }

                //String
                is StringOption -> this.string(option.name, option.description) {
                    this.required = option.required
                    this.minLength = option.minLength
                    this.maxLength = option.maxLength
                    this.autocomplete = option.autoComplete
                    option.choices.forEach {
                        this.choice(it.name, it.value)
                    }
                }

                //Integer
                is IntegerOption -> this.integer(option.name, option.description) {
                    this.required = option.required
                    this.minValue = option.minValue
                    this.maxValue = option.maxValue
                    option.choices.forEach {
                        this.choice(it.name, it.value)
                    }
                }

                //Boolean
                is BooleanOption -> this.boolean(option.name, option.description) {
                    this.required = option.required
                }

                //SubCommand
                is SubCommandOption -> this.subCommand(option.name, option.description) {
                    this.required = option.required
                    option.options?.forEach { option2 ->
                        this.setOptions(option2)
                    }
                }

                //Group
                is GroupOption -> this.group(option.name, option.description) {
                    this.required = option.required
                    option.subCommands?.forEach { subCommandOption ->
                        this.subCommand(subCommandOption.name, subCommandOption.description) {
                            this.required = subCommandOption.required
                            subCommandOption.options?.forEach { option2 ->
                                this.setOptions(option2)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun SubCommandBuilder.setOptions(option: CommandOption) {
        when(option) {
            //Mentionable
            is MentionableOption -> this.mentionable(option.name, option.description) {
                this.required = option.required
            }

            //Channel
            is ChannelOption -> this.channel(option.name, option.description) {
                this.required = option.required
                this.channelTypes = option.channelTypes
            }

            //User
            is UserOption -> this.user(option.name, option.description) {
                this.required = option.required
            }

            //Role
            is RoleOption -> this.user(option.name, option.description) {
                this.required = option.required
            }

            //Attachment
            is AttachmentOption -> this.attachment(option.name, option.description) {
                this.required = option.required
            }

            //Number
            is NumberOption -> this.number(option.name, option.description) {
                this.required = option.required
                this.minValue = option.minValue
                this.maxValue = option.maxValue
            }

            //String
            is StringOption -> this.string(option.name, option.description) {
                this.required = option.required
                this.minLength = option.minLength
                this.maxLength = option.maxLength
                this.autocomplete = option.autoComplete
                option.choices.forEach {
                    this.choice(it.name, it.value)
                }
            }

            //Integer
            is IntegerOption -> this.integer(option.name, option.description) {
                this.required = option.required
                this.minValue = option.minValue
                this.maxValue = option.maxValue
                option.choices.forEach {
                    this.choice(it.name, it.value)
                }
            }

            //Boolean
            is BooleanOption -> this.boolean(option.name, option.description) {
                this.required = option.required
            }
        }
    }
}