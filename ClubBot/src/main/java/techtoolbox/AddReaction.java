package techtoolbox;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class AddReaction extends ListenerAdapter {

	EmbedBuilder embed = new EmbedBuilder();
	
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		
		// Check if the message with reactions is the one in #welcome
		if (event.getMessageId().equalsIgnoreCase("488182547214499841")) {
			if (event.getReactionEmote().getName().equals("💾")) {
				event.getGuild().getController().addRolesToMember(event.getMember(), event.getJDA().getRolesByName("Hardware", true)).complete();
				Main.joinEmbed("Hardware", event.getUser());
				Main.newMemberEmbed(event.getGuild().getTextChannelById("488165879612178432"), event.getMember());
			}
			else if (event.getReactionEmote().getName().equals("🖌")) {
				event.getGuild().getController().addRolesToMember(event.getMember(), event.getJDA().getRolesByName("Design", true)).complete();
				Main.joinEmbed("Design", event.getUser());
				Main.newMemberEmbed(event.getGuild().getTextChannelById("418540155574419477"), event.getMember());
				Main.updateMembers();
			}
			else if (event.getReactionEmote().getName().equals("💻")) {
				event.getGuild().getController().addRolesToMember(event.getMember(), event.getJDA().getRolesByName("Development", true)).complete();
				Main.joinEmbed("Development", event.getUser());
				Main.newMemberEmbed(event.getGuild().getTextChannelById("419247557953454081"), event.getMember());
				Main.updateMembers();
			}
			else {
				// Remove invalid emote
				event.getReaction().removeReaction(event.getUser()).queue();
				
				// Create embed if there is any type of error
				embed.setTitle(":x: Woah, that group is invalid.");
				embed.setColor(0xf94242);
				embed.setDescription("Looks like you've used an invalid emote. Make sure to select a valid one so we know what group you are part of!");
				embed.setFooter("Computer Science Club", "https://raw.githubusercontent.com/nkomarn/SpigotImages/master/Discord%20Bot%20Logo.png");
			
				// Send DM
				event.getUser().openPrivateChannel().queue((channel) -> {
					channel.sendMessage(embed.build()).complete();
					embed.clear();
				});
			}
		}
	}
}
