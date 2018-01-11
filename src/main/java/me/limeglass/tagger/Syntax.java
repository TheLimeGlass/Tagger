package me.limeglass.tagger;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.util.SimpleEvent;
import me.limeglass.tagger.utils.Utils;
import me.limeglass.tagger.utils.annotations.AllChangers;
import me.limeglass.tagger.utils.annotations.AntiDependency;
import me.limeglass.tagger.utils.annotations.Changers;
import me.limeglass.tagger.utils.annotations.Disabled;

public class Syntax {

	private static HashMap<String, String[]> modified = new HashMap<String, String[]>();
	private static HashMap<String, String[]> completeSyntax = new HashMap<String, String[]>();

	public static String[] register(Class<?> syntaxClass, String... syntax) {
		if (syntaxClass.isAnnotationPresent(Disabled.class)) return null;
		String type = "Expressions";
		if (Condition.class.isAssignableFrom(syntaxClass)) type = "Conditions";
		else if (Effect.class.isAssignableFrom(syntaxClass)) type = "Effects";
		else if (SimpleEvent.class.isAssignableFrom(syntaxClass)) type = "Events";
		else if (PropertyExpression.class.isAssignableFrom(syntaxClass)) type = "PropertyExpressions";
		String node = "Syntax." + type + "." + syntaxClass.getSimpleName() + ".";
		if (!Tagger.getSyntaxData().isSet(node + "enabled")) {
			Tagger.getSyntaxData().set(node + "enabled", true);
			save();
		}
		if (syntaxClass.isAnnotationPresent(Changers.class) || syntaxClass.isAnnotationPresent(AllChangers.class)) {
			if (syntaxClass.isAnnotationPresent(AllChangers.class)) Tagger.getSyntaxData().set(node + "changers", "All changers");
			else {
				ChangeMode[] changers = syntaxClass.getAnnotation(Changers.class).value();
				Tagger.getSyntaxData().set(node + "changers", Arrays.toString(changers));
			}
			save();
		}
		if (syntaxClass.isAnnotationPresent(AntiDependency.class)) {
			String plugin = ((AntiDependency) syntaxClass.getAnnotation(AntiDependency.class)).value()[0];
			if (Bukkit.getPluginManager().getPlugin(plugin) != null && Bukkit.getPluginManager().getPlugin(plugin).isEnabled()) return null;
		}
		if (syntaxClass.isAnnotationPresent(Description.class)) {
			String[] descriptions = syntaxClass.getAnnotation(Description.class).value();
			Tagger.getSyntaxData().set(node + "description", descriptions[0]);
			save();
		}
		if (!Tagger.getSyntaxData().getBoolean(node + "enabled")) {
			if (Tagger.getInstance().getConfig().getBoolean("NotRegisteredSyntax", false)) Tagger.consoleMessage(node.toString() + " didn't register!");
			return null;
		}
		if (!Tagger.getSyntaxData().isSet(node + "syntax")) {
			Tagger.getSyntaxData().set(node + "syntax", syntax);
			save();
			return add(syntaxClass.getSimpleName(), syntax);
		}
		List<String> data = Tagger.getSyntaxData().getStringList(node + "syntax");
		if (!Utils.compareArrays(data.toArray(new String[data.size()]), syntax)) modified.put(syntaxClass.getSimpleName(), syntax);
		if (Tagger.getSyntaxData().isList(node + "syntax")) {
			List<String> syntaxes = Tagger.getSyntaxData().getStringList(node + "syntax");
			return add(syntaxClass.getSimpleName(), syntaxes.toArray(new String[syntaxes.size()]));
		}
		return add(syntaxClass.getSimpleName(), new String[]{Tagger.getSyntaxData().getString(node + "syntax")});
	}
	
	public static Boolean isModified(@SuppressWarnings("rawtypes") Class syntaxClass) {
		return modified.containsKey(syntaxClass.getSimpleName());
	}
	
	public static String[] get(String syntaxClass) {
		return completeSyntax.get(syntaxClass);
	}
	
	private static String[] add(String syntaxClass, String... syntax) {
		if (!completeSyntax.containsValue(syntax)) {
			completeSyntax.put(syntaxClass, syntax);
		}
		return syntax;
	}
	
	public static void save() {
		try {
			Tagger.getSyntaxData().save(Tagger.syntaxFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}