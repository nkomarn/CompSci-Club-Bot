package techtoolbox;

import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class RemoveReaction extends ListenerAdapter {

	public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
		if (event.getMessageId().equalsIgnoreCase("488182547214499841")) {
			if (event.getReactionEmote().getName().equals("💾")) {
				event.getGuild().getController().removeRolesFromMember(event.getMember(), event.getJDA().getRolesByName("Hardware", true)).complete();
				Main.leaveEmbed("Hardware", event.getUser());
				Main.updateMembers();
			}
			else if (event.getReactionEmote().getName().equals("🖌")) {
				event.getGuild().getController().removeRolesFromMember(event.getMember(), event.getJDA().getRolesByName("Design", true)).complete();
				Main.leaveEmbed("Design", event.getUser());
				Main.updateMembers();
			}
			else if (event.getReactionEmote().getName().equals("💻")) {
				event.getGuild().getController().removeRolesFromMember(event.getMember(), event.getJDA().getRolesByName("Development", true)).complete();
				Main.leaveEmbed("Development", event.getUser());
				Main.updateMembers();
			}
		}
	}
}
