package techtoolbox;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		for (int i = 0; i < Config.badWords.length; i++) {
            if (event.getMessage().getContentRaw().toLowerCase().contains(Config.badWords[i])) {
                event.getMessage().delete().complete();
                EmbedBuilder embed = new EmbedBuilder();
    			embed.setTitle(":no_entry: Woah, calm down there.");
    			embed.setDescription("Looks like you got a bit toasty there. Unfortunately, you broke rule 3 (no derogatory or discriminatory remarks), so I've removed your message. Remember, if such behavior continues, action will be taken.");
    			embed.setColor(0xf94242);
    			embed.setFooter("Computer Science Club", "https://raw.githubusercontent.com/nkomarn/SpigotImages/master/Discord%20Bot%20Logo.png");
                event.getMember().getUser().openPrivateChannel().queue((channel) -> {
        			channel.sendMessage(embed.build()).complete();
        			embed.clear();
        		});
                break;
            }
        }
		
		// Commands (only admins)
		if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
			if (args[0].equalsIgnoreCase(Config.PREFIX + "update")) {
				event.getChannel().deleteMessageById(event.getChannel().getLatestMessageId()).queue();
				
				EmbedBuilder embed = new EmbedBuilder();
				
				Main.updateMembers();
				
				// Create embed
				embed.setTitle(":white_check_mark: Updated user list!");
				embed.setColor(0x81ff62);
				embed.setFooter("Computer Science Club", "https://raw.githubusercontent.com/nkomarn/SpigotImages/master/Discord%20Bot%20Logo.png");
			
				event.getChannel().sendTyping().complete();
				event.getChannel().sendMessage(embed.build()).complete();
				embed.clear();
			}
			else if (args[0].equalsIgnoreCase(Config.PREFIX + "announce")) {				
				if (args.length > 1) {
					event.getMessage().delete().complete();
					EmbedBuilder embed = new EmbedBuilder();
					
					String message = "";
					for (int i = 1; args.length > i; i++) {
                        message = message + args[i] + " ";
					}
					
					// Create Embed
					embed.setDescription(message);
					embed.setAuthor("Announcement by " + event.getMember().getEffectiveName(), null, event.getMember().getUser().getAvatarUrl());
					embed.setColor(0xff5d59);
					embed.setFooter("Computer Science Club", "https://raw.githubusercontent.com/nkomarn/SpigotImages/master/Discord%20Bot%20Logo.png");
				
					event.getGuild().getTextChannelById("417830772146176002").sendTyping();
					event.getGuild().getTextChannelById("417830772146176002").sendMessage("@everyone").complete();
					event.getGuild().getTextChannelById("417830772146176002").sendMessage(embed.build()).complete();
					embed.clear();
				}
				else {
					event.getMessage().delete().complete();
					EmbedBuilder embed = new EmbedBuilder();
					
					// Create Embed
					embed.setTitle(":x: Look's like you didn't provide a message.");
					embed.setColor(0xf94242);
					embed.setDescription("Add a message after the command to announce the message.");
					embed.setFooter("Computer Science Club", "https://raw.githubusercontent.com/nkomarn/SpigotImages/master/Discord%20Bot%20Logo.png");
				
					event.getChannel().sendTyping();
					event.getChannel().sendMessage(embed.build()).complete();
					embed.clear();
				}
			}
			else if (args[0].equalsIgnoreCase(Config.PREFIX + "destroyevidence")) {
				if (args.length > 1) {
					event.getChannel().deleteMessageById(event.getChannel().getLatestMessageId()).complete();
					Main.deleteMessages(Integer.parseInt(args[1]), event.getChannel());
				}
				else {
					
				}
			}
			else if (args[0].equalsIgnoreCase("~takeitawayrick")) {
				Main.rickRoll(event.getGuild());
			}
		}	
	}
}
