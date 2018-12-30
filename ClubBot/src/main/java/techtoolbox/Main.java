package techtoolbox;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class Main {
	
	private static JDA jda;
	public static int sent = 0;
	public static int i = 0;
	public static Instant start = Instant.now();
	static List<Member> designMembers;
	static List<Member> developmentMembers;
	static List<Member> hardwareMembers;
	public static String[] rickRoll = {"We're no strangers to love", 
			"You know the rules and so do I",
			"A full commitment's what I'm thinking of",
			"You wouldn't get this from any other guy",
			"I just wanna tell you how I'm feeling",
			"Gotta make you understand",
			"Never gonna give you up",
			"Never gonna let you down",
			"Never gonna run around and desert you",
			"Never gonna make you cry",
			"Never gonna say goodbye",
			"Never gonna tell a lie and hurt you",
			"We've known each other for so long",
			"Your heart's been aching but you're too shy to say it",
			"Inside we both know what's been going on",
			"We know the game and we're gonna play it",
			"And if you ask me how I'm feeling",
			"Don't tell me you're too blind to see",
			"Never gonna give you up", 
			"Never gonna let you down",
			"Never gonna run around and desert you",
			"Never gonna make you cry",
			"Never gonna say goodbye", 
			"Never gonna tell a lie and hurt you",
			"Never gonna give you up", 
			"Never gonna let you down", 
			"Never gonna run around and desert you", 
			"Never gonna make you cry",
			"Never gonna say goodbye",
			"Never gonna tell a lie and hurt you", 
			"Never gonna give, never gonna give",
			"(Give you up)",
			"(Ooh) Never gonna give, never gonna give", 
			"(Give you up)",
			"We've known each other for so long",
			"Your heart's been aching but you're too shy to say it",
			"Inside we both know what's been going on",
			"We know the game and we're gonna play it", 
			"I just wanna tell you how I'm feeling",
			"Gotta make you understand",
			"Never gonna give you up",
			"Never gonna let you down",
			"Never gonna run around and desert you", 
			"Never gonna make you cry",
			"Never gonna say goodbye",
			"Never gonna tell a lie and hurt you",
			"Never gonna give you up",
			"Never gonna let you down", 
			"Never gonna run around and desert you",
			"Never gonna make you cry",
			"Never gonna say goodbye",
			"Never gonna tell a lie and hurt you",
			"Never gonna give you up",
			"Never gonna let you down",
			"Never gonna run around and desert you",
			"Never gonna make you cry",
			" "};
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		jda = new JDABuilder(AccountType.BOT).setToken("").buildAsync();
	
		// Set presence
		jda.getPresence().setGame(Game.listening("never gonna give you up..."));
		jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
		
		jda.addEventListener(new AddReaction());
		jda.addEventListener(new RemoveReaction());
		jda.addEventListener(new Commands());
	
		
	}
	
	// Creates embed in direct message when emote is selected
	public static void joinEmbed(String group, User user) {
		EmbedBuilder embed = new EmbedBuilder();
		
		// Create Embed
		embed.setTitle(":clipboard: We're all good to go!");
		embed.setColor(0xff6200);
		embed.setDescription("I've added you to the " + group + " group. I'll let everyone know of your arrival. Adventure awaits!");
		embed.setFooter("Computer Science Club", "https://raw.githubusercontent.com/nkomarn/SpigotImages/master/Discord%20Bot%20Logo.png");
		
		user.openPrivateChannel().queue((channel) -> {
			// Send embed in direct message
			channel.sendMessage(embed.build()).complete();
			
			// Clear embed for later use
			embed.clear();
		});
	}
	
	// Creates embed in direct message when emote is removed
	public static void leaveEmbed(String group, User user) {
		EmbedBuilder embed = new EmbedBuilder();
		
		// Create Embed
		embed.setTitle(":wave: Thanks for participating!");
		embed.setColor(0xff6200);
		embed.setDescription("Thanks for being part of " + group + "! It was a fun ride.");
		embed.setFooter("Computer Science Club", "https://raw.githubusercontent.com/nkomarn/SpigotImages/master/Discord%20Bot%20Logo.png");
		
		user.openPrivateChannel().queue((channel) -> {
			// Send embed in direct message
			channel.sendMessage(embed.build()).complete();
			
			// Clear embed for later use
			embed.clear();
		});
	}
	
	// Notifies group of a new member
	public static void newMemberEmbed(TextChannel channel, Member member) {
		channel.sendTyping().complete();
		
		EmbedBuilder embed = new EmbedBuilder();
		
		// Pick random message and send embed
		int random = ThreadLocalRandom.current().nextInt(0, Config.messages.length);
		
		// Create embed
		embed.setTitle(":wave: New member!");
		embed.setColor(0xff6200);
		embed.setDescription(Config.messages[random].replace("[person]", member.getEffectiveName()));
		embed.setFooter("Computer Science Club", "https://raw.githubusercontent.com/nkomarn/SpigotImages/master/Discord%20Bot%20Logo.png");
	
		// Send message in channel
		channel.sendMessage(embed.build()).complete();
		embed.clear();
	}
	
	// Update amounts of members under members category
	public static void updateMembers() {
		designMembers = jda.getGuildById(Config.guildID).getMembersWithRoles(jda.getGuildById(Config.guildID).getRoleById("417828339005390860"));
		jda.getGuildById(Config.guildID).getVoiceChannelById("485467455246827540").getManager().setName("Design: " + designMembers.size()).complete();
		
		developmentMembers = jda.getGuildById(Config.guildID).getMembersWithRoles(jda.getGuildById(Config.guildID).getRoleById("417828670887952394"));
		jda.getGuildById(Config.guildID).getVoiceChannelById("485467502609170451").getManager().setName("Development: " + developmentMembers.size()).complete();
		
		hardwareMembers = jda.getGuildById(Config.guildID).getMembersWithRoles(jda.getGuildById(Config.guildID).getRoleById("417828569616482314"));
		jda.getGuildById(Config.guildID).getVoiceChannelById("485467545449529356").getManager().setName("Hardware: " + hardwareMembers.size()).complete();
	
		designMembers.clear();
		developmentMembers.clear();
		hardwareMembers.clear();
	}
	
	// Deletes messages in channel
	public static void deleteMessages(int amount, MessageChannel channel) {
		for (int i = 0; i < amount; i++) {
			channel.deleteMessageById(channel.getLatestMessageId()).queue();
			System.out.println(channel.getLatestMessageId());
		}
	}
	
	// Rick roll
	public static void rickRoll(Guild guild) {
		// Rickroll task
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				if (i != 55) {
					i++;
					System.out.println("Lyrics sent: " + sent);
					guild.getTextChannelById("419249450372825113").sendMessage(Main.rickRoll[i]).queue();
					guild.getTextChannelById("421453782451224586").sendMessage(Main.rickRoll[i]).queue();
					guild.getTextChannelById("418540155574419477").sendMessage(Main.rickRoll[i]).queue();
					guild.getTextChannelById("419247557953454081").sendMessage(Main.rickRoll[i]).queue();
					guild.getTextChannelById("488165879612178432").sendMessage(Main.rickRoll[i]).queue();
					sent = sent + 5;
					guild.getVoiceChannelById("528756932862017557").getManager().setName("Lyrics sent: " + sent).complete();	
					guild.getVoiceChannelById("528757102936850432").getManager().setName("Elapsed: " + Duration.between(start, Instant.now()).toHours() + " hours").complete();
					}
				else {
					i = 0;
				}
			}
		}, 0, 3000);
	}
}
